package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogSingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.RegionDistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.SaveRegionDistrictUseCase
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionDistrictConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictUiToRegionDistrictMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Geo.ui.RegionDistrictViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class RegionDistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionDistrictUseCases,
    private val converter: RegionDistrictConverter,
    private val mapper: RegionDistrictUiToRegionDistrictMapper
) : RegionDistrictViewModel,
    DialogSingleViewModel<RegionDistrictUi, UiState<RegionDistrictUi>, RegionDistrictUiAction, UiSingleEvent, RegionDistrictFields, InputWrapper>(
        state,
        RegionDistrictFields.DISTRICT_SHORT_NAME
    ) {
    private val regionDistrictId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.REGION_DISTRICT_ID.name,
            InputWrapper()
        )
    }
    override val region: StateFlow<InputListItemWrapper> by lazy {
        state.getStateFlow(RegionDistrictFields.REGION_DISTRICT_REGION.name, InputListItemWrapper())
    }
    override val districtShortName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionDistrictFields.DISTRICT_SHORT_NAME.name, InputWrapper())
    }
    override val districtName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RegionDistrictFields.DISTRICT_NAME.name, InputWrapper())
    }

    override val areInputsValid = combine(region, districtShortName, districtName)
    { region, districtShortName, districtName ->
        region.errorId == null && districtShortName.errorId == null && districtName.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<RegionDistrictUi> = UiState.Loading

    override suspend fun handleAction(action: RegionDistrictUiAction): Job {
        Timber.tag(TAG).d("handleAction(RegionDistrictUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionDistrictUiAction.Load -> when (action.regionDistrictId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.locality_new_subheader)
                    submitState(UiState.Success(RegionDistrictUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.locality_subheader)
                    loadRegionDistrict(action.regionDistrictId)
                }
            }

            is RegionDistrictUiAction.Save -> saveRegionDistrict()
        }
        return job
    }

    private fun loadRegionDistrict(regionDistrictId: UUID): Job {
        Timber.tag(TAG).d("loadRegionDistrict(UUID) called: %s", regionDistrictId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRegionDistrictUseCase.execute(
                GetRegionDistrictUseCase.Request(regionDistrictId)
            )
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveRegionDistrict(): Job {
        val regionUi = RegionUi()
        regionUi.id = region.value.item.itemId
        val regionDistrictUi = RegionDistrictUi(
            region = regionUi,
            districtShortName = districtShortName.value.value,
            districtName = districtName.value.value
        )
        regionDistrictUi.id = if (regionDistrictId.value.value.isNotEmpty()) {
            UUID.fromString(regionDistrictId.value.value)
        } else null
        Timber.tag(TAG).d("saveRegionDistrict() called: UI model %s", regionDistrictUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveRegionDistrictUseCase.execute(
                SaveRegionDistrictUseCase.Request(mapper.map(regionDistrictUi))
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<RegionDistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val regionDistrictUi = uiModel as RegionDistrictUi
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(RegionDistrictModel) called: regionDistrictUi = %s",
                regionDistrictUi
            )
        regionDistrictUi.id?.let {
            initStateValue(RegionDistrictFields.REGION_DISTRICT_ID, regionDistrictId, it.toString())
        }
        initStateValue(
            RegionDistrictFields.REGION_DISTRICT_REGION, region,
            ListItemModel(regionDistrictUi.region.id, regionDistrictUi.region.regionName)
        )
        initStateValue(
            RegionDistrictFields.DISTRICT_SHORT_NAME, districtShortName,
            regionDistrictUi.districtShortName
        )
        initStateValue(
            RegionDistrictFields.DISTRICT_NAME, districtName, regionDistrictUi.districtName
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegionDistrictInputEvent.Region ->
                        when (RegionDistrictInputValidator.Region.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                RegionDistrictFields.REGION_DISTRICT_REGION, region, event.input,
                                true
                            )

                            else -> setStateValue(
                                RegionDistrictFields.REGION_DISTRICT_REGION, region, event.input
                            )
                        }

                    is RegionDistrictInputEvent.DistrictShortName ->
                        when (RegionDistrictInputValidator.DistrictShortName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.DISTRICT_SHORT_NAME, districtShortName,
                                event.input, true
                            )

                            else -> setStateValue(
                                RegionDistrictFields.DISTRICT_SHORT_NAME, districtShortName,
                                event.input
                            )
                        }

                    is RegionDistrictInputEvent.DistrictName ->
                        when (RegionDistrictInputValidator.DistrictName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.DISTRICT_NAME, districtName, event.input, true
                            )

                            else -> setStateValue(
                                RegionDistrictFields.DISTRICT_NAME, districtName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegionDistrictInputEvent.Region ->
                        setStateValue(
                            RegionDistrictFields.REGION_DISTRICT_REGION, region,
                            RegionDistrictInputValidator.Region.errorIdOrNull(event.input.headline)
                        )

                    is RegionDistrictInputEvent.DistrictShortName ->
                        setStateValue(
                            RegionDistrictFields.DISTRICT_SHORT_NAME, districtShortName,
                            RegionDistrictInputValidator.DistrictShortName.errorIdOrNull(event.input)
                        )

                    is RegionDistrictInputEvent.DistrictName ->
                        setStateValue(
                            RegionDistrictFields.DISTRICT_NAME, districtName,
                            RegionDistrictInputValidator.DistrictName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegionDistrictInputValidator.Region.errorIdOrNull(region.value.item.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = RegionDistrictFields.REGION_DISTRICT_REGION.name,
                    errorId = it
                )
            )
        }
        RegionDistrictInputValidator.DistrictShortName.errorIdOrNull(districtShortName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = RegionDistrictFields.DISTRICT_SHORT_NAME.name,
                        errorId = it
                    )
                )
            }
        RegionDistrictInputValidator.DistrictName.errorIdOrNull(districtName.value.value)?.let {
            inputErrors.add(
                InputError(
                    fieldName = RegionDistrictFields.DISTRICT_NAME.name,
                    errorId = it
                )
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                RegionDistrictFields.REGION_DISTRICT_REGION.name -> region.value.copy(errorId = error.errorId)
                RegionDistrictFields.DISTRICT_SHORT_NAME.name -> districtShortName.value.copy(
                    errorId = error.errorId
                )

                RegionDistrictFields.DISTRICT_NAME.name -> districtName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : RegionDistrictViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val region = MutableStateFlow(InputListItemWrapper())
                override val districtShortName = MutableStateFlow(InputWrapper())
                override val districtName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: RegionDistrictUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RegionDistrictFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): RegionDistrictUi {
            val regionDistrictUi = RegionDistrictUi(
                region = RegionViewModelImpl.previewUiModel(ctx),
                districtShortName = ctx.resources.getString(R.string.def_reg_donetsky_short_name),
                districtName = ctx.resources.getString(R.string.def_reg_donetsky_name)
            )
            regionDistrictUi.id = UUID.randomUUID()
            return regionDistrictUi
        }
    }
}