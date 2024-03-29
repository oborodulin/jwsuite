package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.extensions.toUUIDOrNull
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.types.RoadType
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.SaveStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toLocalityUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Geo.StreetViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class StreetViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: StreetUseCases,
    private val converter: StreetConverter,
    private val streetUiMapper: StreetUiToStreetMapper,
    private val streetMapper: StreetToStreetsListItemMapper
) : StreetViewModel,
    DialogViewModel<StreetUi, UiState<StreetUi>, StreetUiAction, UiSingleEvent, StreetFields, InputWrapper>(
        state, StreetFields.STREET_ID.name, StreetFields.STREET_LOCALITY
    ) {
    private val _roadTypes: MutableStateFlow<MutableMap<RoadType, String>> =
        MutableStateFlow(mutableMapOf())
    override val roadTypes = _roadTypes.asStateFlow()

    override val tlId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(StreetFields.STREET_TL_ID.name, InputWrapper())
    }
    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(StreetFields.STREET_LOCALITY.name, InputListItemWrapper())
    }

    /*
    override val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(StreetFields.STREET_LOCALITY_DISTRICT.name, InputListItemWrapper())
    }
    override val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(StreetFields.STREET_MICRODISTRICT.name, InputListItemWrapper())
    }*/
    override val roadType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(StreetFields.STREET_ROAD_TYPE.name, InputWrapper())
    }
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(StreetFields.STREET_IS_PRIVATE_SECTOR.name, InputWrapper())
    }
    override val estimatedHouses: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(StreetFields.STREET_EST_HOUSES.name, InputWrapper())
    }
    override val streetName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(StreetFields.STREET_NAME.name, InputWrapper())
    }

    override val areInputsValid = combine(locality, roadType, streetName)
    { locality, roadType, streetName -> locality.errorId == null && roadType.errorId == null && streetName.errorId == null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initStreetTypes(com.oborodulin.jwsuite.domain.R.array.road_types)
    }

    private fun initStreetTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in RoadType.entries) _roadTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: StreetUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(StreetUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is StreetUiAction.Load -> {
                when (action.streetId) {
                    null -> setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.street_new_subheader)
                    else -> setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.street_subheader)
                }
                loadStreet(action.streetId)
            }

            is StreetUiAction.Save -> saveStreet()
        }
        return job
    }

    private fun loadStreet(streetId: UUID? = null): Job {
        Timber.tag(TAG).d("loadStreet(UUID) called: streetId = %s", streetId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getStreetUseCase.execute(GetStreetUseCase.Request(streetId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveStreet(): Job {
        //val localityDistrictUi = LocalityDistrictUi()
        //localityDistrictUi.id = localityDistrict.value.item?.itemId
        //val microdistrictUi = MicrodistrictUi()
        //microdistrictUi.id = microdistrict.value.item?.itemId

        val streetUi = StreetUi(
            locality = locality.value.item.toLocalityUi(),
            //localityDistrict = localityDistrictUi,
            //microdistrict = microdistrictUi,
            roadType = RoadType.valueOf(roadType.value.value),
            isPrivateSector = isPrivateSector.value.value.toBoolean(),
            estimatedHouses = estimatedHouses.value.value.toIntOrNull(),
            streetName = streetName.value.value
        ).also {
            it.id = id()
            it.tlId = tlId.value.value.toUUIDOrNull()
        }
        Timber.tag(TAG).d("saveStreet() called: UI model %s", streetUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveStreetUseCase.execute(
                SaveStreetUseCase.Request(streetUiMapper.map(streetUi))
            ).collect {
                Timber.tag(TAG).d("saveStreet() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(streetMapper.map(it.data.street))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<StreetFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: StreetUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) {
            Timber.tag(TAG)
                .d("initFieldStatesByUiModel(StreetUi) called: uiModel = %s", uiModel)
        }
        uiModel.id?.let { initStateValue(StreetFields.STREET_ID, id, it.toString()) }
        uiModel.tlId?.let { initStateValue(StreetFields.STREET_TL_ID, tlId, it.toString()) }
        initStateValue(
            StreetFields.STREET_LOCALITY, locality, uiModel.locality.toListItemModel()
        )/*
        initStateValue(
            StreetFields.STREET_LOCALITY_DISTRICT, localityDistrict,
            ListItemModel(
                uiModel.localityDistrict?.id, uiModel.localityDistrict?.districtName.orEmpty()
            )
        )
        initStateValue(
            StreetFields.STREET_MICRODISTRICT, microdistrict,
            ListItemModel(
                uiModel.microdistrict?.id, uiModel.microdistrict?.microdistrictName.orEmpty()
            )
        )*/
        initStateValue(StreetFields.STREET_ROAD_TYPE, roadType, uiModel.roadType.name)
        initStateValue(
            StreetFields.STREET_IS_PRIVATE_SECTOR, isPrivateSector,
            uiModel.isPrivateSector.toString()
        )
        initStateValue(
            StreetFields.STREET_EST_HOUSES, estimatedHouses,
            uiModel.estimatedHouses?.toString().orEmpty()
        )
        initStateValue(StreetFields.STREET_NAME, streetName, uiModel.streetName)
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# observeInputEvents() called")
        }
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is StreetInputEvent.Locality -> setStateValue(
                        StreetFields.STREET_LOCALITY, locality, event.input,
                        StreetInputValidator.Locality.isValid(event.input.headline)
                    )
                    /*
                                        is StreetInputEvent.LocalityDistrict ->
                                            setStateValue(
                                                StreetFields.STREET_LOCALITY_DISTRICT, localityDistrict,
                                                event.input, true
                                            )

                                        is StreetInputEvent.Microdistrict ->
                                            setStateValue(
                                                StreetFields.STREET_MICRODISTRICT, microdistrict, event.input, true
                                            )
                    */
                    is StreetInputEvent.RoadType -> setStateValue(
                        StreetFields.STREET_ROAD_TYPE, roadType, event.input, true
                    )

                    is StreetInputEvent.IsPrivateSector -> setStateValue(
                        StreetFields.STREET_IS_PRIVATE_SECTOR, isPrivateSector,
                        event.input.toString()
                    )

                    is StreetInputEvent.EstimatedHouses -> setStateValue(
                        StreetFields.STREET_EST_HOUSES, estimatedHouses,
                        event.input.toString(), true
                    )

                    is StreetInputEvent.StreetName -> setStateValue(
                        StreetFields.STREET_NAME, streetName, event.input,
                        StreetInputValidator.StreetName.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is StreetInputEvent.Locality -> setStateValue(
                        StreetFields.STREET_LOCALITY, locality,
                        StreetInputValidator.Locality.errorIdOrNull(event.input.headline)
                    )
                    /*
                                        is StreetInputEvent.LocalityDistrict ->
                                            setStateValue(StreetFields.STREET_LOCALITY_DISTRICT, localityDistrict, null)

                                        is StreetInputEvent.Microdistrict ->
                                            setStateValue(StreetFields.STREET_MICRODISTRICT, microdistrict, null)
                    */
                    is StreetInputEvent.RoadType ->
                        setStateValue(StreetFields.STREET_ROAD_TYPE, roadType, null)

                    is StreetInputEvent.IsPrivateSector ->
                        setStateValue(StreetFields.STREET_IS_PRIVATE_SECTOR, isPrivateSector, null)

                    is StreetInputEvent.EstimatedHouses ->
                        setStateValue(StreetFields.STREET_EST_HOUSES, estimatedHouses, null)

                    is StreetInputEvent.StreetName -> setStateValue(
                        StreetFields.STREET_NAME, streetName,
                        StreetInputValidator.StreetName.errorIdOrNull(event.input)
                    )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        }
        val inputErrors: MutableList<InputError> = mutableListOf()
        StreetInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = StreetFields.STREET_LOCALITY.name, errorId = it)
            )
        }
        StreetInputValidator.StreetName.errorIdOrNull(streetName.value.value)?.let {
            inputErrors.add(InputError(fieldName = StreetFields.STREET_NAME.name, errorId = it))
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG)
                .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        }
        for (error in inputErrors) {
            state[error.fieldName] = when (StreetFields.valueOf(error.fieldName)) {
                StreetFields.STREET_LOCALITY -> locality.value.copy(errorId = error.errorId)
                StreetFields.STREET_NAME -> streetName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : StreetViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val roadTypes = MutableStateFlow(mutableMapOf<RoadType, String>())

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val tlId = MutableStateFlow(InputWrapper())
                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())

                //override val localityDistrict = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                //override val microdistrict = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val roadType = MutableStateFlow(InputWrapper())
                override val isPrivateSector = MutableStateFlow(InputWrapper())
                override val estimatedHouses = MutableStateFlow(InputWrapper())
                override val streetName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: StreetUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: StreetFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(isPartialInputsValid: Boolean, onSuccess: () -> Unit) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context) = StreetUi(
            locality = LocalityViewModelImpl.previewUiModel(ctx),
            roadType = RoadType.STREET,
            isPrivateSector = true,
            estimatedHouses = 56,
            streetName = ctx.resources.getString(R.string.def_baratynskogo_name)
        ).also { it.id = UUID.randomUUID() }
    }
}