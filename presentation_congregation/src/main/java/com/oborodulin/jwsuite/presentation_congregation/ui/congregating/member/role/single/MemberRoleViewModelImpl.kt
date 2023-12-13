package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

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
import com.oborodulin.home.common.util.toFullFormatOffsetDateTimeOrNull
import com.oborodulin.home.common.util.toOffsetDateTime
import com.oborodulin.home.common.util.toShortFormatString
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.SaveMemberRoleUseCase
import com.oborodulin.jwsuite.presentation.ui.model.mappers.MemberRoleToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRoleUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MemberRoleConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberRoleUiToMemberRoleMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toMembersListItem
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

private const val TAG = "Congregating.MemberRoleViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class MemberRoleViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: MemberUseCases,
    private val converter: MemberRoleConverter,
    private val memberRoleUiMapper: MemberRoleUiToMemberRoleMapper,
    private val memberRoleMapper: MemberRoleToMemberRolesListItemMapper
) : MemberRoleViewModel,
    DialogViewModel<MemberRoleUi, UiState<MemberRoleUi>, MemberRoleUiAction, UiSingleEvent, MemberRoleFields, InputWrapper>(
        state, MemberRoleFields.MEMBER_ROLE_ID.name, MemberRoleFields.MEMBER_ROLE_ROLE
    ) {
    override val congregation: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MemberRoleFields.MEMBER_ROLE_CONGREGATION.name, InputListItemWrapper())
    }
    override val member: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MemberRoleFields.MEMBER_ROLE_MEMBER.name, InputListItemWrapper())
    }

    override val role: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MemberRoleFields.MEMBER_ROLE_ROLE.name, InputListItemWrapper())
    }

    override val roleExpiredDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE.name, InputWrapper())
    }

    override val areInputsValid = combine(role, roleExpiredDate)
    { stateFlowsArray ->
        var errorIdResult = true
        for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
        errorIdResult
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<MemberRoleUi> = UiState.Loading

    override suspend fun handleAction(action: MemberRoleUiAction): Job {
        Timber.tag(TAG).d("handleAction(MemberRoleUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MemberRoleUiAction.Load -> when (action.memberRoleId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_role_new_subheader)
                    submitState(UiState.Success(MemberRoleUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_role_subheader)
                    loadMemberRole(action.memberRoleId)
                }
            }

            is MemberRoleUiAction.Save -> saveMemberRole()
        }
        return job
    }

    private fun loadMemberRole(memberRoleId: UUID): Job {
        Timber.tag(TAG).d("loadMemberRole(UUID) called: %s", memberRoleId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMemberRoleUseCase.execute(GetMemberRoleUseCase.Request(memberRoleId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveMemberRole(): Job {
        //val offsetFormatter = DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
        val congregationUi = CongregationUi()
        congregationUi.id = congregation.value.item?.itemId
        val memberUi = MemberUi(
            congregation = congregationUi,
            congregationId = congregation.value.item?.itemId
        )
        memberUi.id = member.value.item?.itemId
        val roleUi = RoleUi()
        roleUi.id = role.value.item?.itemId
        val memberRoleUi = MemberRoleUi(
            member = memberUi,
            role = roleUi,
            roleExpiredDate = roleExpiredDate.value.value.toFullFormatOffsetDateTimeOrNull()
        )
        memberRoleUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d("saveMemberRole() called: UI model %s", memberRoleUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveMemberRoleUseCase.execute(
                SaveMemberRoleUseCase.Request(memberRoleUiMapper.map(memberRoleUi))
            ).collect {
                Timber.tag(TAG).d("saveMemberRole() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(memberRoleMapper.map(it.data.memberRole))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<MemberRoleFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: MemberRoleUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberRoleUi) called: uiModel = %s", uiModel)
        uiModel.id?.let { initStateValue(MemberRoleFields.MEMBER_ROLE_ID, id, it.toString()) }
        initStateValue(
            MemberRoleFields.MEMBER_ROLE_CONGREGATION, congregation,
            CongregationsListItem(
                id = uiModel.member.congregation.id!!,
                locality = uiModel.member.congregation.locality
            )
        )
        initStateValue(
            MemberRoleFields.MEMBER_ROLE_MEMBER, member, uiModel.member.toMembersListItem()
        )
        initStateValue(
            MemberRoleFields.MEMBER_ROLE_ROLE, role,
            ListItemModel(uiModel.role.id, uiModel.role.roleName)
        )
        initStateValue(
            MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE, roleExpiredDate,
            uiModel.roleExpiredDate.toShortFormatString().orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MemberRoleInputEvent.Congregation -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_CONGREGATION, congregation, event.input, true
                    )

                    is MemberRoleInputEvent.Member -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_MEMBER, member, event.input, true
                    )

                    is MemberRoleInputEvent.Role -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_ROLE, role, event.input,
                        MemberRoleInputValidator.Role.isValid(event.input.headline)
                    )

                    is MemberRoleInputEvent.RoleExpiredDate -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE, roleExpiredDate, event.input,
                        MemberRoleInputValidator.RoleExpiredDate.isValid(event.input)
                    )
                }
            }.debounce(350)
            .collect { event ->
                when (event) {
                    is MemberRoleInputEvent.Congregation -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_CONGREGATION, congregation, null
                    )

                    is MemberRoleInputEvent.Member -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_MEMBER, member,
                        MemberRoleInputValidator.Member.errorIdOrNull(event.input.headline)
                    )

                    is MemberRoleInputEvent.Role -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_ROLE, role,
                        MemberRoleInputValidator.Role.errorIdOrNull(event.input.headline)
                    )

                    is MemberRoleInputEvent.RoleExpiredDate -> setStateValue(
                        MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE, roleExpiredDate,
                        MemberRoleInputValidator.RoleExpiredDate.errorIdOrNull(event.input)
                    )
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        MemberRoleInputValidator.Role.errorIdOrNull(role.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = MemberRoleFields.MEMBER_ROLE_ROLE.name, errorId = it)
            )
        }
        MemberRoleInputValidator.RoleExpiredDate.errorIdOrNull(roleExpiredDate.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (MemberRoleFields.valueOf(error.fieldName)) {
                MemberRoleFields.MEMBER_ROLE_ROLE -> role.value.copy(errorId = error.errorId)
                MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE -> roleExpiredDate.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : MemberRoleViewModel {
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
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val member = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val role = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val roleExpiredDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: MemberRoleUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: MemberRoleFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): MemberRoleUi {
            val memberRoleUi = MemberRoleUi(
                member = MemberViewModelImpl.previewUiModel(ctx),
                role = RoleUi(),
                roleExpiredDate = "2024-08-01T14:29:10.212+03:00".toOffsetDateTime()
            )
            memberRoleUi.id = UUID.randomUUID()
            return memberRoleUi
        }
    }
}