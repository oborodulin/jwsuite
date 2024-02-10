package com.oborodulin.jwsuite.presentation.ui.database

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
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DatabaseUseCases
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation.ui.model.DatabaseUiModel
import com.oborodulin.jwsuite.presentation.ui.model.converters.DatabaseUiModelCsvExpConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.DatabaseUiModelCsvImpConverter
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Presentation.AppSettingViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class DatabaseViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val databaseUseCases: DatabaseUseCases,
    private val appSettingUseCases: AppSettingUseCases,
    private val csvImpConverter: DatabaseUiModelCsvImpConverter,
    private val csvExpConverter: DatabaseUiModelCsvExpConverter,
    private val appSettingsUiMapper: AppSettingsListItemToAppSettingsListMapper
) : DatabaseViewModel,
    DialogViewModel<DatabaseUiModel, UiState<DatabaseUiModel>, DatabaseUiAction, UiSingleEvent, DatabaseFields, InputWrapper>(
        state, //AppSettingFields.MEMBER_ID.name, AppSettingFields.TERRITORY_PROCESSING_PERIOD
    ) {
    override val databaseBackupPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(DatabaseFields.DATABASE_BACKUP_PERIOD.name, InputWrapper())
    }

    override val areInputsValid = databaseBackupPeriod.map { it.errorId == null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: DatabaseUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(AppSettingUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is DatabaseUiAction.Backup -> backup()
            is DatabaseUiAction.Restore -> restore()
            is DatabaseUiAction.Save -> saveDatabaseSettings()
        }
        return job
    }

    private fun backup(): Job {
        Timber.tag(TAG).d("backup() called")
        val job = viewModelScope.launch(errorHandler) {
            databaseUseCases.csvExportUseCase.execute(CsvExportUseCase.Request)
                .map { csvExpConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun restore(): Job {
        Timber.tag(TAG).d("restore() called")
        val job = viewModelScope.launch(errorHandler) {
            databaseUseCases.csvImportUseCase.execute(CsvImportUseCase.Request)
                .map { csvImpConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun saveDatabaseSettings(): Job {
        val appSettings = listOf(
            AppSettingsListItem(
                paramName = AppSettingParam.DATABASE_BACKUP_PERIOD,
                paramValue = databaseBackupPeriod.value.value
            )
        )
        Timber.tag(TAG).d("saveDatabaseSettings() called: UI model %s", appSettings)
        val job = viewModelScope.launch(errorHandler) {
            appSettingUseCases.saveAppSettingsUseCase.execute(
                SaveAppSettingsUseCase.Request(appSettingsUiMapper.map(appSettings))
            ).collect {
                Timber.tag(TAG).d("saveDatabaseSettings() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<DatabaseFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: DatabaseUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(DatabaseUiModel) called: uiModel = %s", uiModel)
        /*initStateValue(
            DatabaseFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_PROCESSING_PERIOD }.paramValue
        )*/
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is DatabaseInputEvent.TerritoryProcessingPeriod -> setStateValue(
                        DatabaseFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
                        event.input,
                        DatabaseInputValidator.DatabaseBackupPeriod.isValid(event.input)
                    )
                }
            }.debounce(350)
            .collect { event ->
                when (event) {
                    is DatabaseInputEvent.TerritoryProcessingPeriod ->
                        setStateValue(
                            DatabaseFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
                            DatabaseInputValidator.DatabaseBackupPeriod.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()

        DatabaseInputValidator.DatabaseBackupPeriod.errorIdOrNull(databaseBackupPeriod.value.value)
            ?.let {
                inputErrors.add(
                    InputError(fieldName = DatabaseFields.DATABASE_BACKUP_PERIOD.name, errorId = it)
                )
            }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (DatabaseFields.valueOf(error.fieldName)) {
                DatabaseFields.DATABASE_BACKUP_PERIOD -> databaseBackupPeriod.value.copy(
                    errorId = error.errorId
                )
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : DatabaseViewModel {
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

                override fun submitAction(action: DatabaseUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: DatabaseFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): DatabaseUiModel {
            val databaseUiModel = DatabaseUiModel(
                entityDesc = "",
                isDone = true
            )
            databaseUiModel.id = UUID.randomUUID()
            return databaseUiModel
        }
    }
}