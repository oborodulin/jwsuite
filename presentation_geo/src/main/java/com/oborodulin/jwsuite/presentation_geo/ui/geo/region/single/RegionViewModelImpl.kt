package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.CoordinatesUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.SaveRegionConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionUiToRegionMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.toCountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
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

private const val TAG = "Geo.RegionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class RegionViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionUseCases,
    private val getConverter: RegionConverter,
    private val saveConverter: SaveRegionConverter,
    private val regionUiMapper: RegionUiToRegionMapper
) : RegionViewModel,
    DialogViewModel<RegionUi, UiState<RegionUi>, RegionUiAction, UiSingleEvent, RegionFields, InputWrapper>(
        state, RegionFields.REGION_ID.name, RegionFields.REGION_CODE
    ) {
    override val country: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RegionFields.REGION_COUNTRY.name, InputListItemWrapper())
    }
    override val regionCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionFields.REGION_CODE.name, InputWrapper())
    }
    override val regionName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionFields.REGION_NAME.name, InputWrapper())
    }
    override val regionGeocode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionFields.REGION_GEOCODE.name, InputWrapper())
    }
    override val regionOsmId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionFields.REGION_OSM_ID.name, InputWrapper())
    }
    override val latitude: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionFields.REGION_LATITUDE.name, InputWrapper())
    }
    override val longitude: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionFields.REGION_LONGITUDE.name, InputWrapper())
    }

    override val areInputsValid = combine(regionCode, regionName) { regionCode, regionName ->
        regionCode.errorId == null && regionName.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RegionUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(RegionUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionUiAction.Load -> when (action.regionId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.region_new_subheader)
                    submitState(UiState.Success(RegionUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.region_subheader)
                    loadRegion(action.regionId)
                }
            }

            is RegionUiAction.Save -> saveRegion()
        }
        return job
    }

    private fun loadRegion(regionId: UUID): Job {
        Timber.tag(TAG).d("loadRegion(UUID) called: regionId = %s", regionId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRegionUseCase.execute(GetRegionUseCase.Request(regionId))
                .map {
                    getConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveRegion(): Job {
        val regionUi = RegionUi(
            country = country.value.item.toCountryUi(),
            regionCode = regionCode.value.value,
            regionGeocode = regionGeocode.value.value.ifEmpty { null },
            regionOsmId = regionOsmId.value.value.toLongOrNull(),
            coordinates = CoordinatesUi(
                latitude.value.value.toBigDecimalOrNull(),
                longitude.value.value.toBigDecimalOrNull()
            ),
            regionName = regionName.value.value
        ).also { it.id = id.value.value.toUUIDOrNull() }
        Timber.tag(TAG).d("saveRegion() called: UI model %s", regionUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveRegionUseCase.execute(SaveRegionUseCase.Request(regionUiMapper.map(regionUi)))
                .map { saveConverter.convert(it) }
                .collect {
                    Timber.tag(TAG).d("saveRegion() collect: %s", it)
                    if (it is UiState.Success) {
                        setSavedListItem(it.data.toListItemModel())
                    }
                    submitState(it)
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<RegionFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: RegionUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(RegionUi) called: uiModel = %s", uiModel)
        uiModel.id?.let { initStateValue(RegionFields.REGION_ID, id, it.toString()) }
        initStateValue(RegionFields.REGION_COUNTRY, country, uiModel.country.toListItemModel())
        initStateValue(RegionFields.REGION_CODE, regionCode, uiModel.regionCode)
        initStateValue(RegionFields.REGION_NAME, regionName, uiModel.regionName)
        initStateValue(
            RegionFields.REGION_GEOCODE, regionGeocode, uiModel.regionGeocode.orEmpty()
        )
        initStateValue(
            RegionFields.REGION_OSM_ID, regionOsmId, uiModel.regionOsmId?.toString().orEmpty()
        )
        initStateValue(
            RegionFields.REGION_LATITUDE, latitude,
            uiModel.coordinates.latitude?.toString().orEmpty()
        )
        initStateValue(
            RegionFields.REGION_LONGITUDE, longitude,
            uiModel.coordinates.longitude?.toString().orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegionInputEvent.Country -> setStateValue(
                        RegionFields.REGION_COUNTRY, country, event.input,
                        RegionInputValidator.Country.isValid(event.input.headline)
                    )

                    is RegionInputEvent.RegionCode -> setStateValue(
                        RegionFields.REGION_CODE, regionCode, event.input,
                        RegionInputValidator.RegionCode.isValid(event.input)
                    )

                    is RegionInputEvent.RegionName -> setStateValue(
                        RegionFields.REGION_NAME, regionName, event.input,
                        RegionInputValidator.RegionName.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegionInputEvent.Country -> setStateValue(
                        RegionFields.REGION_COUNTRY, country,
                        RegionInputValidator.Country.errorIdOrNull(event.input.headline)
                    )

                    is RegionInputEvent.RegionCode -> setStateValue(
                        RegionFields.REGION_CODE, regionCode,
                        RegionInputValidator.RegionCode.errorIdOrNull(event.input)
                    )

                    is RegionInputEvent.RegionName -> setStateValue(
                        RegionFields.REGION_NAME, regionName,
                        RegionInputValidator.RegionName.errorIdOrNull(event.input)
                    )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegionInputValidator.Country.errorIdOrNull(country.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.REGION_COUNTRY.name, errorId = it))
        }
        RegionInputValidator.RegionCode.errorIdOrNull(regionCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.REGION_CODE.name, errorId = it))
        }
        RegionInputValidator.RegionName.errorIdOrNull(regionName.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.REGION_NAME.name, errorId = it))
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (RegionFields.valueOf(error.fieldName)) {
                RegionFields.REGION_COUNTRY -> country.value.copy(errorId = error.errorId)
                RegionFields.REGION_CODE -> regionCode.value.copy(errorId = error.errorId)
                RegionFields.REGION_NAME -> regionName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : RegionViewModel {
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

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val country = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val regionCode = MutableStateFlow(InputWrapper())
                override val regionName = MutableStateFlow(InputWrapper())
                override val regionGeocode = MutableStateFlow(InputWrapper())
                override val regionOsmId = MutableStateFlow(InputWrapper())
                override val latitude = MutableStateFlow(InputWrapper())
                override val longitude = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: RegionUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RegionFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = RegionUi(
            regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code),
            regionName = ctx.resources.getString(R.string.def_reg_donetsk_name)
        ).also { it.id = UUID.randomUUID() }
    }
}