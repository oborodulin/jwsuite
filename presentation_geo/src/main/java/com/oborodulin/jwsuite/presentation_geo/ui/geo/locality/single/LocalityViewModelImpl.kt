package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single

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
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.types.LocalityType
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.LocalityUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.LocalityConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalitiesListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toRegionDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.toRegionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

private const val TAG = "Geo.LocalityViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class LocalityViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: LocalityUseCases,
    private val converter: LocalityConverter,
    private val localityUiMapper: LocalityUiToLocalityMapper,
    private val localityMapper: LocalityToLocalitiesListItemMapper
) : LocalityViewModel,
    DialogViewModel<LocalityUi, UiState<LocalityUi>, LocalityUiAction, UiSingleEvent, LocalityFields, InputWrapper>(
        state, LocalityFields.LOCALITY_ID.name, LocalityFields.LOCALITY_COUNTRY
    ) {
    private val _localityTypes: MutableStateFlow<MutableMap<LocalityType, String>> =
        MutableStateFlow(mutableMapOf())
    override val localityTypes = _localityTypes.asStateFlow()

    override val tlId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_TL_ID.name, InputWrapper())
    }
    override val country: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_COUNTRY.name, InputListItemWrapper())
    }
    override val region: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_REGION.name, InputListItemWrapper())
    }
    override val regionDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_REGION_DISTRICT.name, InputListItemWrapper())
    }
    override val localityCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_CODE.name, InputWrapper())
    }
    override val localityShortName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_SHORT_NAME.name, InputWrapper())
    }
    override val localityType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_TYPE.name, InputWrapper())
    }
    override val localityName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityFields.LOCALITY_NAME.name, InputWrapper())
    }

    override val areInputsValid =
        combine(country, region, localityCode, localityShortName, localityName)
        { country, region, localityCode, localityShortName, localityName ->
            country.errorId == null && region.errorId == null && localityCode.errorId == null && localityShortName.errorId == null && localityName.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initLocalityTypes(com.oborodulin.jwsuite.domain.R.array.locality_types)
    }

    private fun initLocalityTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in LocalityType.entries) _localityTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: LocalityUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("handleAction(LocalityUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is LocalityUiAction.Load -> {
                when (action.localityId) {
                    null -> setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.locality_new_subheader)
                    else -> setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.locality_subheader)
                }
                loadLocality(action.localityId)
            }

            is LocalityUiAction.Save -> saveLocality()
        }
        return job
    }

    private fun loadLocality(localityId: UUID? = null): Job {
        Timber.tag(TAG).d("loadLocality(UUID?) called: localityId = %s", localityId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getLocalityUseCase.execute(GetLocalityUseCase.Request(localityId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveLocality(): Job {
        val localityUi = LocalityUi(
            region = region.value.item.toRegionUi(),
            regionDistrict = regionDistrict.value.item.toRegionDistrictUi(),
            localityCode = localityCode.value.value.trim(),
            localityType = LocalityType.valueOf(localityType.value.value),
            localityShortName = localityShortName.value.value.trim(),
            localityName = localityName.value.value.trim()
        ).also {
            it.id = id()
            it.tlId = tlId.value.value.toUUIDOrNull()
        }
        Timber.tag(TAG).d("saveLocality() called: UI model %s", localityUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveLocalityUseCase.execute(
                SaveLocalityUseCase.Request(localityUiMapper.map(localityUi))
            ).collect {
                Timber.tag(TAG).d("saveLocality() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(localityMapper.map(it.data.locality))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<LocalityFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: LocalityUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG).d("initFieldStatesByUiModel(LocalityUi) called: uiModel = %s", uiModel)
        uiModel.id?.let { initStateValue(LocalityFields.LOCALITY_ID, id, it.toString()) }
        uiModel.tlId?.let { initStateValue(LocalityFields.LOCALITY_TL_ID, tlId, it.toString()) }
        initStateValue(
            LocalityFields.LOCALITY_COUNTRY, country, uiModel.region?.country.toListItemModel()
        )
        initStateValue(LocalityFields.LOCALITY_REGION, region, uiModel.region.toListItemModel())
        initStateValue(
            LocalityFields.LOCALITY_REGION_DISTRICT, regionDistrict,
            uiModel.regionDistrict.toListItemModel()
        )
        initStateValue(LocalityFields.LOCALITY_CODE, localityCode, uiModel.localityCode)
        initStateValue(
            LocalityFields.LOCALITY_SHORT_NAME, localityShortName, uiModel.localityShortName
        )
        initStateValue(LocalityFields.LOCALITY_TYPE, localityType, uiModel.localityType.name)
        initStateValue(LocalityFields.LOCALITY_NAME, localityName, uiModel.localityName)
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# observeInputEvents() called")
        }
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is LocalityInputEvent.Country -> setStateValue(
                        LocalityFields.LOCALITY_COUNTRY, country, event.input,
                        LocalityInputValidator.Country.isValid(event.input.headline)
                    )

                    is LocalityInputEvent.Region -> setStateValue(
                        LocalityFields.LOCALITY_REGION, region, event.input,
                        LocalityInputValidator.Region.isValid(event.input.headline)
                    )

                    is LocalityInputEvent.RegionDistrict -> setStateValue(
                        LocalityFields.LOCALITY_REGION_DISTRICT, regionDistrict, event.input,
                        true
                    )

                    is LocalityInputEvent.LocalityCode -> setStateValue(
                        LocalityFields.LOCALITY_CODE, localityCode, event.input,
                        LocalityInputValidator.LocalityCode.isValid(event.input)
                    )

                    is LocalityInputEvent.LocalityShortName -> setStateValue(
                        LocalityFields.LOCALITY_SHORT_NAME, localityShortName, event.input,
                        LocalityInputValidator.LocalityShortName.isValid(event.input)
                    )

                    is LocalityInputEvent.LocalityType -> setStateValue(
                        LocalityFields.LOCALITY_TYPE, localityType, event.input, true
                    )

                    is LocalityInputEvent.LocalityName -> setStateValue(
                        LocalityFields.LOCALITY_NAME, localityName, event.input,
                        LocalityInputValidator.LocalityName.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is LocalityInputEvent.Country -> setStateValue(
                        LocalityFields.LOCALITY_COUNTRY, country,
                        LocalityInputValidator.Country.errorIdOrNull(event.input.headline)
                    )

                    is LocalityInputEvent.Region -> setStateValue(
                        LocalityFields.LOCALITY_REGION, region,
                        LocalityInputValidator.Region.errorIdOrNull(event.input.headline)
                    )

                    is LocalityInputEvent.RegionDistrict -> setStateValue(
                        LocalityFields.LOCALITY_REGION_DISTRICT, regionDistrict, null
                    )

                    is LocalityInputEvent.LocalityCode -> setStateValue(
                        LocalityFields.LOCALITY_CODE, localityCode,
                        LocalityInputValidator.LocalityCode.errorIdOrNull(event.input)
                    )

                    is LocalityInputEvent.LocalityShortName -> setStateValue(
                        LocalityFields.LOCALITY_SHORT_NAME, localityShortName,
                        LocalityInputValidator.LocalityShortName.errorIdOrNull(event.input)
                    )

                    is LocalityInputEvent.LocalityType ->
                        setStateValue(LocalityFields.LOCALITY_TYPE, localityType, null)

                    is LocalityInputEvent.LocalityName -> setStateValue(
                        LocalityFields.LOCALITY_NAME, localityName,
                        LocalityInputValidator.LocalityName.errorIdOrNull(event.input)
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
        LocalityInputValidator.Country.errorIdOrNull(country.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = LocalityFields.LOCALITY_COUNTRY.name, errorId = it)
            )
        }
        LocalityInputValidator.Region.errorIdOrNull(region.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = LocalityFields.LOCALITY_REGION.name, errorId = it)
            )
        }
        LocalityInputValidator.LocalityCode.errorIdOrNull(localityCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LOCALITY_CODE.name, errorId = it))
        }
        LocalityInputValidator.LocalityShortName.errorIdOrNull(localityShortName.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = LocalityFields.LOCALITY_SHORT_NAME.name, errorId = it)
            )
        }
        LocalityInputValidator.LocalityName.errorIdOrNull(localityName.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LOCALITY_NAME.name, errorId = it))
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG)
                .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        }
        for (error in inputErrors) {
            state[error.fieldName] = when (LocalityFields.valueOf(error.fieldName)) {
                LocalityFields.LOCALITY_COUNTRY -> country.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_REGION -> region.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_CODE -> localityCode.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_SHORT_NAME -> localityShortName.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_NAME -> localityName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : LocalityViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val localityTypes = MutableStateFlow(mutableMapOf<LocalityType, String>())

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val tlId = MutableStateFlow(InputWrapper())
                override val country = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val region = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val regionDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val localityCode = MutableStateFlow(InputWrapper())
                override val localityShortName = MutableStateFlow(InputWrapper())
                override val localityType = MutableStateFlow(InputWrapper())
                override val localityName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: LocalityUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: LocalityFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context) = LocalityUi(
            region = RegionViewModelImpl.previewUiModel(ctx),
            //regionDistrict = ,
            localityCode = ctx.resources.getString(R.string.def_donetsk_code),
            localityShortName = ctx.resources.getString(R.string.def_donetsk_short_name),
            localityName = ctx.resources.getString(R.string.def_donetsk_name)
        ).also { it.id = UUID.randomUUID() }
    }
}