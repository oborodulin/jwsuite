package com.oborodulin.jwsuite.presentation_dashboard.ui.database

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DatabaseUseCases
import com.oborodulin.jwsuite.domain.usecases.db.ReceiveDataUseCase
import com.oborodulin.jwsuite.domain.usecases.db.SendDataUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseCsvExpConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseCsvImpConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseReceiveConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseSendConverter
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

private const val TAG = "Presentation_Dashboard.DatabaseViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class DatabaseViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val databaseUseCases: DatabaseUseCases,
    private val csvImpConverter: DatabaseCsvImpConverter,
    private val csvExpConverter: DatabaseCsvExpConverter,
    private val sendConverter: DatabaseSendConverter,
    private val receiveConverter: DatabaseReceiveConverter
) : DatabaseViewModel,
    DialogViewModel<DatabaseUiModel, UiState<DatabaseUiModel>, DatabaseUiAction, UiSingleEvent, DatabaseFields, InputWrapper>(
        state, //AppSettingFields.MEMBER_ID.name, AppSettingFields.TERRITORY_PROCESSING_PERIOD
    ) {
    override val receiverMember: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(DatabaseFields.RECEIVER_MEMBER.name, InputListItemWrapper())
    }

    override val areInputsValid = receiverMember.map { it.errorId == null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: DatabaseUiAction): Job {
        if (LogLevel.LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(DatabaseUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is DatabaseUiAction.Init -> submitState(UiState.Success(DatabaseUiModel()))
            is DatabaseUiAction.Backup -> backup()
            is DatabaseUiAction.Restore -> restore()
            is DatabaseUiAction.Send -> send(action.memberRoleType)
            is DatabaseUiAction.Receive -> receive()
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

    private fun send(memberRoleType: MemberRoleType? = null): Job {
        Timber.tag(TAG).d("send(...) called: memberRoleType = %s", memberRoleType)
        val receiverMemberId = receiverMember.value.item?.itemId
        Timber.tag(TAG).d("send: receiverMemberId = %s", receiverMemberId)
        val job = viewModelScope.launch(errorHandler) {
            databaseUseCases.sendDataUseCase.execute(
                SendDataUseCase.Request(
                    memberId = receiverMemberId,
                    memberRoleType = memberRoleType
                )
            ).map { sendConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun receive(): Job {
        Timber.tag(TAG).d("receive() called")
        val job = viewModelScope.launch(errorHandler) {
            databaseUseCases.receiveDataUseCase.execute(ReceiveDataUseCase.Request)
                .map { receiveConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    override fun stateInputFields() = enumValues<DatabaseFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: DatabaseUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LogLevel.LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(DatabaseUiModel) called: uiModel = %s", uiModel)
        /*initStateValue(
            DatabaseFields.DATABASE_BACKUP_PERIOD, databaseBackupPeriod,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_PROCESSING_PERIOD }.paramValue
        )*/
        return null
    }

    override suspend fun observeInputEvents() {
        if (LogLevel.LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is DatabaseInputEvent.ReceiverMember -> setStateValue(
                        DatabaseFields.RECEIVER_MEMBER, receiverMember,
                        event.input,
                        DatabaseInputValidator.ReceiverMember.isValid(event.input.headline)
                    )
                }
            }.debounce(350)
            .collect { event ->
                when (event) {
                    is DatabaseInputEvent.ReceiverMember -> setStateValue(
                        DatabaseFields.RECEIVER_MEMBER, receiverMember,
                        DatabaseInputValidator.ReceiverMember.errorIdOrNull(event.input.headline)
                    )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LogLevel.LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()

        DatabaseInputValidator.ReceiverMember.errorIdOrNull(receiverMember.value.item?.headline)
            ?.let {
                inputErrors.add(
                    InputError(fieldName = DatabaseFields.RECEIVER_MEMBER.name, errorId = it)
                )
            }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LogLevel.LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (DatabaseFields.valueOf(error.fieldName)) {
                DatabaseFields.RECEIVER_MEMBER -> receiverMember.value.copy(
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
                    MutableStateFlow(R.string.preview_blank_title)
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
                override val receiverMember =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())

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

        fun previewUiModel(ctx: Context) = DatabaseUiModel(
            entityDesc = "",
            isDone = true
        ).also { it.id = UUID.randomUUID() }
    }
}