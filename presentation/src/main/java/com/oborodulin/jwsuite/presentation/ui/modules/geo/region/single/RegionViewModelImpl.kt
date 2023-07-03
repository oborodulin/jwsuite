package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionUiToRegionMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Geo.ui.RegionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class RegionViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionUseCases,
    private val converter: RegionConverter,
    private val mapper: RegionUiToRegionMapper
) : RegionViewModel,
    SingleViewModel<RegionUi, UiState<RegionUi>, RegionUiAction, UiSingleEvent, RegionFields, InputWrapper>(
        state,
        RegionFields.REGION_CODE
    ) {
    private val regionId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.REGION_ID.name,
            InputWrapper()
        )
    }
    override val regionCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.REGION_CODE.name,
            InputWrapper()
        )
    }
    override val regionName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.REGION_NAME.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            regionCode,
            regionName
        ) { regionCode, regionName ->
            regionCode.errorId == null && regionName.errorId == null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<RegionUi> = UiState.Loading

    override suspend fun handleAction(action: RegionUiAction): Job {
        Timber.tag(TAG).d("handleAction(RegionUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionUiAction.Create -> {
                submitState(UiState.Success(RegionUi()))
            }

            is RegionUiAction.Load -> {
                loadRegion(action.regionId)
            }

            is RegionUiAction.Save -> {
                saveRegion()
            }
        }
        return job
    }

    private fun loadRegion(regionId: UUID): Job {
        Timber.tag(TAG).d("loadRegion(UUID) called: %s", regionId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRegionUseCase.execute(GetRegionUseCase.Request(regionId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveRegion(): Job {
        val regionUi = RegionUi(
            regionCode = regionCode.value.value,
            regionName = regionName.value.value
        )
        regionUi.id = if (regionId.value.value.isNotEmpty()) {
            UUID.fromString(regionId.value.value)
        } else null
        Timber.tag(TAG).d("saveRegion() called: UI model %s", regionUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveRegionUseCase.execute(SaveRegionUseCase.Request(mapper.map(regionUi)))
                .collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<RegionFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val regionUi = uiModel as RegionUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(RegionModel) called: regionUi = %s", regionUi)
        regionUi.id?.let {
            initStateValue(RegionFields.REGION_ID, regionId, it.toString())
        }
        initStateValue(RegionFields.REGION_CODE, regionCode, regionUi.regionCode)
        initStateValue(RegionFields.REGION_NAME, regionName, regionUi.regionName)
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegionInputEvent.RegionCode ->
                        when (RegionInputValidator.RegionCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.REGION_CODE, regionCode, event.input, true
                            )

                            else -> setStateValue(
                                RegionFields.REGION_CODE, regionCode, event.input
                            )
                        }

                    is RegionInputEvent.RegionName ->
                        when (RegionInputValidator.RegionName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.REGION_NAME, regionName, event.input, true
                            )

                            else -> setStateValue(
                                RegionFields.REGION_NAME, regionName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegionInputEvent.RegionCode ->
                        setStateValue(
                            RegionFields.REGION_CODE, regionCode,
                            RegionInputValidator.RegionCode.errorIdOrNull(event.input)
                        )

                    is RegionInputEvent.RegionName ->
                        setStateValue(
                            RegionFields.REGION_NAME, regionName,
                            RegionInputValidator.RegionName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegionInputValidator.RegionCode.errorIdOrNull(regionCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.REGION_CODE.name, errorId = it))
        }
        RegionInputValidator.RegionName.errorIdOrNull(regionName.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.REGION_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                RegionFields.REGION_CODE.name -> regionCode.value.copy(errorId = error.errorId)
                RegionFields.REGION_NAME.name -> regionName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : RegionViewModel {
                override var dialogTitleResId: Int? = null
                override val uiStateFlow = MutableStateFlow(UiState.Success(RegionUi()))
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val regionCode = MutableStateFlow(InputWrapper())
                override val regionName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: RegionUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RegionFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): RegionUi {
            val regionUi = RegionUi(
                regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code),
                regionName = ctx.resources.getString(R.string.def_reg_donetsk_name)
            )
            regionUi.id = UUID.randomUUID()
            return regionUi
        }
    }
}