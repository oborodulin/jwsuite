package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import android.content.Context
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
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GroupUseCases
import com.oborodulin.jwsuite.domain.usecases.group.SaveGroupUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.GroupConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupToGroupsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupUiToGroupMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Congregating.GroupViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class GroupViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: GroupUseCases,
    private val converter: GroupConverter,
    private val groupUiMapper: GroupUiToGroupMapper,
    private val groupMapper: GroupToGroupsListItemMapper
) : GroupViewModel,
    DialogSingleViewModel<GroupUi, UiState<GroupUi>, GroupUiAction, UiSingleEvent, GroupFields, InputWrapper>(
        state, GroupFields.GROUP_ID.name, GroupFields.GROUP_NUM
    ) {
    override val congregation: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
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
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.group_new_subheader)
                    submitState(UiState.Success(GroupUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.group_subheader)
                    loadGroup(action.groupId)
                }
            }

            is GroupUiAction.Save -> saveGroup()
        }
        return job
    }

    private fun loadGroup(groupId: UUID): Job {
        Timber.tag(TAG).d("loadGroup(UUID) called: %s", groupId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getGroupUseCase.execute(GetGroupUseCase.Request(groupId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
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
        groupUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d("saveGroup() called: UI model %s", groupUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveGroupUseCase.execute(SaveGroupUseCase.Request(groupUiMapper.map(groupUi)))
                .collect {
                    Timber.tag(TAG).d("saveGroup() collect: %s", it)
                    if (it is Result.Success) {
                        setSavedListItem(groupMapper.map(it.data.group))
                    }
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<GroupFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val groupUi = uiModel as GroupUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(GroupModel) called: groupUi = %s", groupUi)
        groupUi.id?.let {
            initStateValue(GroupFields.GROUP_ID, id, it.toString())
        }
        initStateValue(
            GroupFields.GROUP_CONGREGATION, congregation,
            ListItemModel(groupUi.congregation.id, groupUi.congregation.congregationName)
        )
        initStateValue(GroupFields.GROUP_NUM, groupNum, groupUi.groupNum.toString())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is GroupInputEvent.GroupNum ->
                        when (GroupInputValidator.GroupNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                GroupFields.GROUP_NUM, groupNum, event.input, true
                            )

                            else -> setStateValue(
                                GroupFields.GROUP_NUM, groupNum, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is GroupInputEvent.GroupNum ->
                        setStateValue(
                            GroupFields.GROUP_NUM, groupNum,
                            GroupInputValidator.GroupNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        GroupInputValidator.GroupNum.errorIdOrNull(groupNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = GroupFields.GROUP_NUM.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
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
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val congregation = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val groupNum = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: GroupUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: GroupFields, isFocused: Boolean
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