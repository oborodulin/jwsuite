package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.InputError
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
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import com.oborodulin.jwsuite.domain.usecases.appsetting.AppSettingUseCases
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation.ui.model.converters.AppSettingListConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingsListMapper
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

private const val TAG = "Territoring.TerritorySettingViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritorySettingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: AppSettingUseCases,
    private val converter: AppSettingListConverter,
    private val appSettingsUiMapper: AppSettingsListItemToAppSettingsListMapper
) : TerritorySettingViewModel,
    DialogViewModel<List<AppSettingsListItem>, UiState<List<AppSettingsListItem>>, TerritorySettingUiAction, UiSingleEvent, TerritorySettingFields, InputWrapper>(
        state, //AppSettingFields.MEMBER_ID.name,
        initFocusedTextField = TerritorySettingFields.TERRITORY_PROCESSING_PERIOD
    ) {
    override val territoryProcessingPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritorySettingFields.TERRITORY_PROCESSING_PERIOD.name, InputWrapper())
    }
    override val territoryAtHandPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritorySettingFields.TERRITORY_AT_HAND_PERIOD.name, InputWrapper())
    }
    override val territoryIdlePeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritorySettingFields.TERRITORY_IDLE_PERIOD.name, InputWrapper())
    }
    override val territoryRoomsLimit: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritorySettingFields.TERRITORY_ROOMS_LIMIT.name, InputWrapper())
    }
    override val territoryMaxRooms: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritorySettingFields.TERRITORY_MAX_ROOMS.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            territoryProcessingPeriod,
            territoryAtHandPeriod,
            territoryIdlePeriod,
            territoryRoomsLimit,
            territoryMaxRooms,
        )
        { stateFlowsArray ->
            var errorIdResult = true
            for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
            errorIdResult
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritorySettingUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("handleAction(AppSettingUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is TerritorySettingUiAction.Load -> loadTerritorySettings()
            is TerritorySettingUiAction.Save -> saveTerritorySettings()
        }
        return job
    }

    private fun loadTerritorySettings(): Job {
        Timber.tag(TAG).d("loadTerritorySettings() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getAppSettingsUseCase.execute(
                GetAppSettingsUseCase.Request(
                    listOf(
                        AppSettingParam.TERRITORY_PROCESSING_PERIOD,
                        AppSettingParam.TERRITORY_AT_HAND_PERIOD,
                        AppSettingParam.TERRITORY_IDLE_PERIOD,
                        AppSettingParam.TERRITORY_ROOMS_LIMIT,
                        AppSettingParam.TERRITORY_MAX_ROOMS
                    )
                )
            ).map { converter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun saveTerritorySettings(): Job {
        val appSettings = listOf(
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_PROCESSING_PERIOD,
                paramValue = territoryProcessingPeriod.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_AT_HAND_PERIOD,
                paramValue = territoryAtHandPeriod.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_IDLE_PERIOD,
                paramValue = territoryIdlePeriod.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_ROOMS_LIMIT,
                paramValue = territoryRoomsLimit.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_MAX_ROOMS,
                paramValue = territoryMaxRooms.value.value
            )
        )
        Timber.tag(TAG).d("saveAppSettings() called: UI model %s", appSettings)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveAppSettingsUseCase.execute(
                SaveAppSettingsUseCase.Request(appSettingsUiMapper.map(appSettings))
            ).collect {
                Timber.tag(TAG).d("saveAppSettings() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritorySettingFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: List<AppSettingsListItem>): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) {
            Timber.tag(TAG)
                .d(
                    "initFieldStatesByUiModel(List<AppSettingsListItem>) called: uiModel = %s",
                    uiModel
                )
        }
        initStateValue(
            TerritorySettingFields.TERRITORY_PROCESSING_PERIOD, territoryProcessingPeriod,
            uiModel.first { it.paramName == AppSettingParam.TERRITORY_PROCESSING_PERIOD }.paramValue
        )
        initStateValue(
            TerritorySettingFields.TERRITORY_AT_HAND_PERIOD, territoryAtHandPeriod,
            uiModel.first { it.paramName == AppSettingParam.TERRITORY_AT_HAND_PERIOD }.paramValue
        )
        initStateValue(
            TerritorySettingFields.TERRITORY_IDLE_PERIOD, territoryIdlePeriod,
            uiModel.first { it.paramName == AppSettingParam.TERRITORY_IDLE_PERIOD }.paramValue
        )
        initStateValue(
            TerritorySettingFields.TERRITORY_ROOMS_LIMIT, territoryRoomsLimit,
            uiModel.first { it.paramName == AppSettingParam.TERRITORY_ROOMS_LIMIT }.paramValue
        )
        initStateValue(
            TerritorySettingFields.TERRITORY_MAX_ROOMS, territoryMaxRooms,
            uiModel.first { it.paramName == AppSettingParam.TERRITORY_MAX_ROOMS }.paramValue
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# observeInputEvents() called")
        }
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritorySettingInputEvent.TerritoryProcessingPeriod -> setStateValue(
                        TerritorySettingFields.TERRITORY_PROCESSING_PERIOD,
                        territoryProcessingPeriod,
                        event.input,
                        TerritorySettingInputValidator.TerritoryProcessingPeriod.isValid(event.input)
                    )

                    is TerritorySettingInputEvent.TerritoryAtHandPeriod -> setStateValue(
                        TerritorySettingFields.TERRITORY_AT_HAND_PERIOD, territoryAtHandPeriod,
                        event.input,
                        TerritorySettingInputValidator.TerritoryAtHandPeriod.isValid(event.input)
                    )

                    is TerritorySettingInputEvent.TerritoryIdlePeriod -> setStateValue(
                        TerritorySettingFields.TERRITORY_IDLE_PERIOD,
                        territoryIdlePeriod,
                        event.input,
                        TerritorySettingInputValidator.TerritoryIdlePeriod.isValid(event.input)
                    )

                    is TerritorySettingInputEvent.TerritoryRoomsLimit -> setStateValue(
                        TerritorySettingFields.TERRITORY_ROOMS_LIMIT,
                        territoryRoomsLimit,
                        event.input,
                        TerritorySettingInputValidator.TerritoryRoomsLimit.isValid(event.input)
                    )

                    is TerritorySettingInputEvent.TerritoryMaxRooms -> setStateValue(
                        TerritorySettingFields.TERRITORY_MAX_ROOMS, territoryMaxRooms, event.input,
                        TerritorySettingInputValidator.TerritoryMaxRooms.isValid(event.input)
                    )
                }
            }.debounce(350)
            .collect { event ->
                when (event) {
                    is TerritorySettingInputEvent.TerritoryProcessingPeriod ->
                        setStateValue(
                            TerritorySettingFields.TERRITORY_PROCESSING_PERIOD,
                            territoryProcessingPeriod,
                            TerritorySettingInputValidator.TerritoryProcessingPeriod.errorIdOrNull(
                                event.input
                            )
                        )

                    is TerritorySettingInputEvent.TerritoryAtHandPeriod ->
                        setStateValue(
                            TerritorySettingFields.TERRITORY_AT_HAND_PERIOD,
                            territoryAtHandPeriod,
                            TerritorySettingInputValidator.TerritoryAtHandPeriod.errorIdOrNull(event.input)
                        )

                    is TerritorySettingInputEvent.TerritoryIdlePeriod ->
                        setStateValue(
                            TerritorySettingFields.TERRITORY_IDLE_PERIOD, territoryIdlePeriod,
                            TerritorySettingInputValidator.TerritoryIdlePeriod.errorIdOrNull(event.input)
                        )

                    is TerritorySettingInputEvent.TerritoryRoomsLimit ->
                        setStateValue(
                            TerritorySettingFields.TERRITORY_ROOMS_LIMIT, territoryRoomsLimit,
                            TerritorySettingInputValidator.TerritoryRoomsLimit.errorIdOrNull(event.input)
                        )

                    is TerritorySettingInputEvent.TerritoryMaxRooms ->
                        setStateValue(
                            TerritorySettingFields.TERRITORY_MAX_ROOMS, territoryMaxRooms,
                            TerritorySettingInputValidator.TerritoryMaxRooms.errorIdOrNull(event.input)
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

        TerritorySettingInputValidator.TerritoryProcessingPeriod.errorIdOrNull(
            territoryProcessingPeriod.value.value
        )
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritorySettingFields.TERRITORY_PROCESSING_PERIOD.name,
                        errorId = it
                    )
                )
            }
        TerritorySettingInputValidator.TerritoryAtHandPeriod.errorIdOrNull(territoryAtHandPeriod.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritorySettingFields.TERRITORY_AT_HAND_PERIOD.name,
                        errorId = it
                    )
                )
            }
        TerritorySettingInputValidator.TerritoryIdlePeriod.errorIdOrNull(territoryIdlePeriod.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritorySettingFields.TERRITORY_IDLE_PERIOD.name, errorId = it
                    )
                )
            }
        TerritorySettingInputValidator.TerritoryRoomsLimit.errorIdOrNull(territoryRoomsLimit.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritorySettingFields.TERRITORY_ROOMS_LIMIT.name, errorId = it
                    )
                )
            }
        TerritorySettingInputValidator.TerritoryMaxRooms.errorIdOrNull(territoryMaxRooms.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritorySettingFields.TERRITORY_MAX_ROOMS.name,
                        errorId = it
                    )
                )
            }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG)
                .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        }
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritorySettingFields.valueOf(error.fieldName)) {
                TerritorySettingFields.TERRITORY_PROCESSING_PERIOD -> territoryProcessingPeriod.value.copy(
                    errorId = error.errorId
                )

                TerritorySettingFields.TERRITORY_AT_HAND_PERIOD -> territoryAtHandPeriod.value.copy(
                    errorId = error.errorId
                )

                TerritorySettingFields.TERRITORY_IDLE_PERIOD -> territoryIdlePeriod.value.copy(
                    errorId = error.errorId
                )

                TerritorySettingFields.TERRITORY_ROOMS_LIMIT -> territoryRoomsLimit.value.copy(
                    errorId = error.errorId
                )

                TerritorySettingFields.TERRITORY_MAX_ROOMS -> territoryMaxRooms.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritorySettingViewModel {
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
                override val territoryProcessingPeriod = MutableStateFlow(InputWrapper())
                override val territoryAtHandPeriod = MutableStateFlow(InputWrapper())
                override val territoryIdlePeriod = MutableStateFlow(InputWrapper())
                override val territoryRoomsLimit = MutableStateFlow(InputWrapper())
                override val territoryMaxRooms = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: TerritorySettingUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritorySettingFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = listOf(
            AppSettingsListItem(
                id = UUID.randomUUID(),
                paramName = AppSettingParam.TERRITORY_PROCESSING_PERIOD,
                paramValue = "12",
                paramFullName = ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.param_name_territory_processing_period)
            ),
            AppSettingsListItem(
                id = UUID.randomUUID(),
                paramName = AppSettingParam.TERRITORY_AT_HAND_PERIOD,
                paramValue = "4",
                paramFullName = ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.param_name_territory_at_hand_period)
            )
        )
    }
}