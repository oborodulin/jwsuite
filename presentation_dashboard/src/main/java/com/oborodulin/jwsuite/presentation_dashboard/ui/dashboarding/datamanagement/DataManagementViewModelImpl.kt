package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

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
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import com.oborodulin.jwsuite.domain.usecases.appsetting.AppSettingUseCases
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDataManagementSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingsListMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleTransferObjectsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.TransferObjectsListItem
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DataManagementSettingsUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DataManagementSettingUiModelConverter
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Dashboarding.DataManagementViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class DataManagementViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: AppSettingUseCases,
    private val converter: DataManagementSettingUiModelConverter,
    private val appSettingsUiMapper: AppSettingsListItemToAppSettingsListMapper
) : DataManagementViewModel,
    DialogViewModel<DataManagementSettingsUiModel, UiState<DataManagementSettingsUiModel>, DataManagementUiAction, UiSingleEvent, DataManagementFields, InputWrapper>(
        state, //AppSettingFields.MEMBER_ID.name, AppSettingFields.TERRITORY_PROCESSING_PERIOD
    ) {
    override val databaseBackupPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(DataManagementFields.DATABASE_BACKUP_PERIOD.name, InputWrapper())
    }

    override val areInputsValid = databaseBackupPeriod.map { it.errorId == null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: DataManagementUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(DataManagementUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is DataManagementUiAction.Load -> loadDataManagementSettings()
            is DataManagementUiAction.Save -> saveDataManagementSettings()
        }
        return job
    }

    private fun loadDataManagementSettings(): Job {
        Timber.tag(TAG).d("loadDataManagementSettings() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getDataManagementSettingsUseCase.execute(GetDataManagementSettingsUseCase.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun saveDataManagementSettings(): Job {
        val appSettings = listOf(
            AppSettingsListItem(
                paramName = AppSettingParam.DATABASE_BACKUP_PERIOD,
                paramValue = databaseBackupPeriod.value.value
            )
        )
        Timber.tag(TAG).d("saveDataManagementSettings() called: UI model %s", appSettings)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveAppSettingsUseCase.execute(
                SaveAppSettingsUseCase.Request(appSettingsUiMapper.map(appSettings))
            ).collect {
                Timber.tag(TAG).d("saveDataManagementSettings() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<DataManagementFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: DataManagementSettingsUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) {
            Timber.tag(TAG)
                .d(
                    "initFieldStatesByUiModel(DataManagementSettingsUiModel) called: uiModel = %s",
                    uiModel
                )
        }
        initStateValue(
            DataManagementFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
            uiModel.settings.first { it.paramName == AppSettingParam.DATABASE_BACKUP_PERIOD }.paramValue
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
                    is DataManagementInputEvent.DatabaseBackupPeriod -> setStateValue(
                        DataManagementFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
                        event.input,
                        DataManagementInputValidator.DatabaseBackupPeriod.isValid(event.input)
                    )
                }
            }.debounce(350)
            .collect { event ->
                when (event) {
                    is DataManagementInputEvent.DatabaseBackupPeriod ->
                        setStateValue(
                            DataManagementFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
                            DataManagementInputValidator.DatabaseBackupPeriod.errorIdOrNull(event.input)
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

        DataManagementInputValidator.DatabaseBackupPeriod.errorIdOrNull(databaseBackupPeriod.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = DataManagementFields.DATABASE_BACKUP_PERIOD.name, errorId = it
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
            state[error.fieldName] = when (DataManagementFields.valueOf(error.fieldName)) {
                DataManagementFields.DATABASE_BACKUP_PERIOD -> databaseBackupPeriod.value.copy(
                    errorId = error.errorId
                )
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : DataManagementViewModel {
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
                override val databaseBackupPeriod = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: DataManagementUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: DataManagementFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = DataManagementSettingsUiModel(
            settings = emptyList(),
            transferObjects = transferObjectsList(ctx)
        ).also { it.id = UUID.randomUUID() }

        private fun transferObjectsList(ctx: Context) = listOf(
            RoleTransferObjectsListItem(
                id = UUID.randomUUID(),
                isPersonalData = false,
                transferObject = TransferObjectsListItem(
                    id = UUID.randomUUID(),
                    transferObjectType = TransferObjectType.ALL,
                    transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_all)
                )
            ), RoleTransferObjectsListItem(
                id = UUID.randomUUID(),
                isPersonalData = false,
                transferObject = TransferObjectsListItem(
                    id = UUID.randomUUID(),
                    transferObjectType = TransferObjectType.TERRITORIES,
                    transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_territories)
                )
            ), RoleTransferObjectsListItem(
                id = UUID.randomUUID(),
                isPersonalData = true,
                transferObject = TransferObjectsListItem(
                    id = UUID.randomUUID(),
                    transferObjectType = TransferObjectType.BILLS,
                    transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_bills)
                )
            )
        )
    }
}