package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetNextGroupNumUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GroupUseCases
import com.oborodulin.jwsuite.domain.usecases.group.SaveGroupUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.GroupConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.SaveGroupConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group.GroupUiToGroupMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toListItemModel
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

private const val TAG = "Congregating.GroupViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class GroupViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: GroupUseCases,
    private val getConverter: GroupConverter,
    private val saveConverter: SaveGroupConverter,
    private val groupUiMapper: GroupUiToGroupMapper
    //private val groupMapper: GroupToGroupsListItemMapper
) : GroupViewModel,
    DialogViewModel<GroupUi, UiState<GroupUi>, GroupUiAction, UiSingleEvent, GroupFields, InputWrapper>(
        state, GroupFields.GROUP_ID.name, GroupFields.GROUP_NUM
    ) {
    override val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>> by lazy {
        state.getStateFlow(GroupFields.GROUP_CONGREGATION.name, InputListItemWrapper())
    }

    override val groupNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(GroupFields.GROUP_NUM.name, InputWrapper())
    }

    override val areInputsValid =
        combine(congregation, groupNum)
        { congregation, groupNum -> congregation.errorId == null && groupNum.errorId == null }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<GroupUi> = UiState.Loading

    override suspend fun handleAction(action: GroupUiAction): Job {
        Timber.tag(TAG).d("handleAction(GroupUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is GroupUiAction.Load -> when (action.groupId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.group_new_subheader)
                    submitState(UiState.Success(GroupUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.group_subheader)
                    loadGroup(action.groupId)
                }
            }

            is GroupUiAction.GetNextGroupNum -> getNextGroupNum(action.congregationId)

            is GroupUiAction.Save -> saveGroup()
        }
        return job
    }

    private fun loadGroup(groupId: UUID): Job {
        Timber.tag(TAG).d("loadGroup(UUID) called: %s", groupId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getGroupUseCase.execute(GetGroupUseCase.Request(groupId))
                .map {
                    getConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun getNextGroupNum(congregationId: UUID): Job {
        Timber.tag(TAG).d("getNextGroupNum(UUID) called: %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getNextGroupNumUseCase.execute(GetNextGroupNumUseCase.Request(congregationId))
                .collect {
                    if (it is Result.Success) {
                        onTextFieldEntered(GroupInputEvent.GroupNum(it.data.groupNum.toString()))
                    }
                }
        }
        return job
    }

    private fun saveGroup(): Job {
        val congregationUi = CongregationUi()
        congregationUi.id = congregation.value.item?.itemId
        val groupUi = GroupUi(
            congregation = congregationUi,
            groupNum = groupNum.value.value.toInt(),
        )
        groupUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d("saveGroup() called: UI model %s", groupUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveGroupUseCase.execute(SaveGroupUseCase.Request(groupUiMapper.map(groupUi)))
                .map { saveConverter.convert(it) }
                .collect {
                    Timber.tag(TAG).d("saveGroup() collect: %s", it)
                    if (it is UiState.Success) {
                        setSavedListItem(it.data.toListItemModel())
                    }
                    submitState(it)
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<GroupFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: GroupUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(GroupModel) called: groupUi = %s", uiModel)
        uiModel.id?.let { initStateValue(GroupFields.GROUP_ID, id, it.toString()) }
        initStateValue(
            GroupFields.GROUP_CONGREGATION, congregation,
            uiModel.congregation.toCongregationsListItem()
        )
        initStateValue(GroupFields.GROUP_NUM, groupNum, uiModel.groupNum?.toString().orEmpty())
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is GroupInputEvent.Congregation -> setStateValue(
                        GroupFields.GROUP_CONGREGATION, congregation, event.input, true
                    )

                    is GroupInputEvent.GroupNum -> setStateValue(
                        GroupFields.GROUP_NUM, groupNum, event.input,
                        GroupInputValidator.GroupNum.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is GroupInputEvent.GroupNum -> setStateValue(
                        GroupFields.GROUP_NUM, groupNum,
                        GroupInputValidator.GroupNum.errorIdOrNull(event.input)
                    )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        GroupInputValidator.GroupNum.errorIdOrNull(groupNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = GroupFields.GROUP_NUM.name, errorId = it))
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (GroupFields.valueOf(error.fieldName)) {
                GroupFields.GROUP_NUM -> groupNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : GroupViewModel {
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
                override val congregation =
                    MutableStateFlow(InputListItemWrapper<CongregationsListItem>())
                override val groupNum = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                //override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: GroupUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: GroupFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean, onSuccess: () -> Unit
                ) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): GroupUi {
            val groupUi = GroupUi(
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                groupNum = ctx.resources.getInteger(R.integer.def_group1)
            )
            groupUi.id = UUID.randomUUID()
            return groupUi
        }
    }
}