package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.single

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogSingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.LocalityUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.domain.util.LocalityType
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters.LocalityConverter
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityToLocalitiesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.LocalityViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryStreetViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: LocalityUseCases,
    private val converter: LocalityConverter,
    private val localityUiMapper: LocalityUiToLocalityMapper,
    private val localityMapper: LocalityToLocalitiesListItemMapper
) : TerritoryStreetViewModel,
    DialogSingleViewModel<LocalityUi, UiState<LocalityUi>, TerritoryStreetUiAction, UiSingleEvent, TerritoryStreetFields, InputWrapper>(
        state,
        TerritoryStreetFields.LOCALITY_REGION
    ) {
    private val _localityTypes: MutableStateFlow<MutableMap<LocalityType, String>> =
        MutableStateFlow(mutableMapOf())
    override val localityTypes = _localityTypes.asStateFlow()

    private val localityId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_ID.name, InputWrapper())
    }
    override val region: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_REGION.name, InputListItemWrapper())
    }
    override val regionDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_REGION_DISTRICT.name, InputListItemWrapper())
    }
    override val localityCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_CODE.name, InputWrapper())
    }
    override val localityShortName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_SHORT_NAME.name, InputWrapper())
    }
    override val localityType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_TYPE.name, InputWrapper())
    }
    override val localityName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.LOCALITY_NAME.name, InputWrapper())
    }

    override val areInputsValid =
        combine(region, localityCode, localityShortName, localityName)
        { region, localityCode, localityShortName, localityName ->
            region.errorId == null && localityCode.errorId == null && localityShortName.errorId == null && localityName.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initLocalityTypes(com.oborodulin.jwsuite.domain.R.array.locality_types)
    }

    private fun initLocalityTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in LocalityType.values()) _localityTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState(): UiState<LocalityUi> = UiState.Loading

    override suspend fun handleAction(action: TerritoryStreetUiAction): Job {
        Timber.tag(TAG).d("handleAction(LocalityUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryStreetUiAction.Load -> when (action.localityId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.locality_new_subheader)
                    submitState(UiState.Success(LocalityUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.locality_subheader)
                    loadLocality(action.localityId)
                }
            }

            is TerritoryStreetUiAction.Save -> saveLocality()
        }
        return job
    }

    private fun loadLocality(localityId: UUID): Job {
        Timber.tag(TAG).d("loadLocality(UUID) called: %s", localityId)
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
        val regionUi = RegionUi()
        regionUi.id = region.value.item?.itemId
        val regionDistrictUi = RegionDistrictUi()
        regionDistrictUi.id = regionDistrict.value.item?.itemId

        val localityUi = LocalityUi(
            region = regionUi,
            regionDistrict = regionDistrictUi,
            localityCode = localityCode.value.value,
            localityType = LocalityType.valueOf(localityType.value.value),
            localityShortName = localityShortName.value.value,
            localityName = localityName.value.value
        )
        localityUi.id = if (localityId.value.value.isNotEmpty()) {
            UUID.fromString(localityId.value.value)
        } else null
        Timber.tag(TAG).d(
            "saveLocality() called: UI model %s; regionUi.id = %s; regionDistrictUi.id = %s",
            localityUi,
            regionUi.id,
            regionDistrictUi.id
        )
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

    override fun stateInputFields() = enumValues<TerritoryStreetFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val localityUi =
            uiModel as LocalityUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(LocalityModel) called: localityUi = %s", localityUi)
        localityUi.id?.let {
            initStateValue(TerritoryStreetFields.LOCALITY_ID, localityId, it.toString())
        }
        initStateValue(
            TerritoryStreetFields.LOCALITY_REGION, region,
            ListItemModel(localityUi.region.id, localityUi.region.regionName)
        )
        initStateValue(
            TerritoryStreetFields.LOCALITY_REGION_DISTRICT, regionDistrict,
            ListItemModel(
                localityUi.regionDistrict?.id, localityUi.regionDistrict?.districtName.orEmpty()
            )
        )
        initStateValue(TerritoryStreetFields.LOCALITY_CODE, localityCode, localityUi.localityCode)
        initStateValue(
            TerritoryStreetFields.LOCALITY_SHORT_NAME, localityShortName, localityUi.localityShortName
        )
        initStateValue(TerritoryStreetFields.LOCALITY_TYPE, localityType, localityUi.localityType.name)
        initStateValue(TerritoryStreetFields.LOCALITY_NAME, localityName, localityUi.localityName)
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryStreetInputEvent.Region ->
                        when (TerritoryStreetInputValidator.Region.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoryStreetFields.LOCALITY_REGION, region, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryStreetFields.LOCALITY_REGION, region, event.input
                            )
                        }

                    is TerritoryStreetInputEvent.RegionDistrict ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_REGION_DISTRICT, regionDistrict,
                            event.input, true
                        )

                    is TerritoryStreetInputEvent.TerritoryStreetCode ->
                        when (TerritoryStreetInputValidator.TerritoryStreetCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                TerritoryStreetFields.LOCALITY_CODE, localityCode, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryStreetFields.LOCALITY_CODE, localityCode, event.input
                            )
                        }

                    is TerritoryStreetInputEvent.TerritoryStreetShortName ->
                        when (TerritoryStreetInputValidator.TerritoryStreetShortName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                TerritoryStreetFields.LOCALITY_SHORT_NAME, localityShortName, event.input,
                                true
                            )

                            else -> setStateValue(
                                TerritoryStreetFields.LOCALITY_SHORT_NAME, localityShortName, event.input
                            )
                        }

                    is TerritoryStreetInputEvent.TerritoryStreetType ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_TYPE, localityType, event.input, true
                        )

                    is TerritoryStreetInputEvent.TerritoryStreetName ->
                        when (TerritoryStreetInputValidator.TerritoryStreetName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                TerritoryStreetFields.LOCALITY_NAME, localityName, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryStreetFields.LOCALITY_NAME, localityName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryStreetInputEvent.Region ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_REGION, region,
                            TerritoryStreetInputValidator.Region.errorIdOrNull(event.input.headline)
                        )

                    is TerritoryStreetInputEvent.RegionDistrict ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_REGION_DISTRICT, regionDistrict, null
                        )

                    is TerritoryStreetInputEvent.TerritoryStreetCode ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_CODE, localityCode,
                            TerritoryStreetInputValidator.TerritoryStreetCode.errorIdOrNull(event.input)
                        )

                    is TerritoryStreetInputEvent.TerritoryStreetShortName ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_SHORT_NAME, localityShortName,
                            TerritoryStreetInputValidator.TerritoryStreetShortName.errorIdOrNull(event.input)
                        )

                    is TerritoryStreetInputEvent.TerritoryStreetType ->
                        setStateValue(TerritoryStreetFields.LOCALITY_TYPE, localityType, null)

                    is TerritoryStreetInputEvent.TerritoryStreetName ->
                        setStateValue(
                            TerritoryStreetFields.LOCALITY_NAME, localityName,
                            TerritoryStreetInputValidator.TerritoryStreetName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoryStreetInputValidator.Region.errorIdOrNull(region.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoryStreetFields.LOCALITY_REGION.name, errorId = it)
            )
        }
        TerritoryStreetInputValidator.TerritoryStreetCode.errorIdOrNull(localityCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = TerritoryStreetFields.LOCALITY_CODE.name, errorId = it))
        }
        TerritoryStreetInputValidator.TerritoryStreetShortName.errorIdOrNull(localityShortName.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoryStreetFields.LOCALITY_SHORT_NAME.name, errorId = it)
            )
        }
        TerritoryStreetInputValidator.TerritoryStreetName.errorIdOrNull(localityName.value.value)?.let {
            inputErrors.add(InputError(fieldName = TerritoryStreetFields.LOCALITY_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                TerritoryStreetFields.LOCALITY_REGION.name -> region.value.copy(errorId = error.errorId)
                TerritoryStreetFields.LOCALITY_CODE.name -> localityCode.value.copy(errorId = error.errorId)
                TerritoryStreetFields.LOCALITY_SHORT_NAME.name -> localityShortName.value.copy(errorId = error.errorId)
                TerritoryStreetFields.LOCALITY_NAME.name -> localityName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryStreetViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val localityTypes = MutableStateFlow(mutableMapOf<LocalityType, String>())

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val region = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val regionDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val localityCode = MutableStateFlow(InputWrapper())
                override val localityShortName = MutableStateFlow(InputWrapper())
                override val localityType = MutableStateFlow(InputWrapper())
                override val localityName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: TerritoryStreetUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryStreetFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): LocalityUi {
            val localityUi = LocalityUi(
                region = RegionViewModelImpl.previewUiModel(ctx),
                //regionDistrict = ,
                localityCode = ctx.resources.getString(R.string.def_donetsk_code),
                localityShortName = ctx.resources.getString(R.string.def_donetsk_short_name),
                localityName = ctx.resources.getString(R.string.def_donetsk_name)
            )
            localityUi.id = UUID.randomUUID()
            return localityUi
        }
    }
}