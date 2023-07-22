package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

import android.content.Context
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
import com.oborodulin.home.common.util.Constants
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.MemberConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberToMemberUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberUiToMemberMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.toMembersListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

private const val TAG = "Congregating.MemberViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class MemberViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: MemberUseCases,
    private val converter: MemberConverter,
    private val memberUiMapper: MemberUiToMemberMapper,
    private val memberMapper: MemberToMemberUiMapper
) : MemberViewModel,
    DialogSingleViewModel<MemberUi, UiState<MemberUi>, MemberUiAction, UiSingleEvent, MemberFields, InputWrapper>(
        state,
        MemberFields.MEMBER_NUM
    ) {
    private val memberId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_ID.name, InputWrapper())
    }

    override val congregation: StateFlow<InputListItemWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_CONGREGATION.name, InputListItemWrapper())
    }

    override val group: StateFlow<InputListItemWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_GROUP.name, InputListItemWrapper())
    }

    override val memberNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_NUM.name, InputWrapper())
    }
    override val memberName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_NAME.name, InputWrapper())
    }
    override val surname: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_SURNAME.name, InputWrapper())
    }
    override val patronymic: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_PATRONYMIC.name, InputWrapper())
    }
    override val pseudonym: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_PSEUDONYM.name, InputWrapper())
    }
    override val phoneNumber: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_PHONE_NUMBER.name, InputWrapper())
    }
    override val memberType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_TYPE.name, InputWrapper())
    }
    override val dateOfBirth: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_DATE_OF_BIRTH.name, InputWrapper())
    }
    override val dateOfBaptism: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_DATE_OF_BAPTISM.name, InputWrapper())
    }
    override val inactiveDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_INACTIVE_DATE.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            group,
            memberNum,
            memberName,
            surname,
            patronymic,
            pseudonym,
            phoneNumber,
            memberType,
            dateOfBirth,
            dateOfBaptism,
            inactiveDate
        )
        { stateFlowsArray ->
            var errorIdResult = true
            for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
            errorIdResult
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<MemberUi> = UiState.Loading

    override suspend fun handleAction(action: MemberUiAction): Job {
        Timber.tag(TAG).d("handleAction(MemberUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MemberUiAction.Load -> when (action.memberId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.member_subheader)
                    submitState(UiState.Success(MemberUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.member_new_subheader)
                    loadMember(action.memberId)
                }
            }

            is MemberUiAction.Save -> saveMember()
        }
        return job
    }

    private fun loadMember(memberId: UUID): Job {
        Timber.tag(TAG).d("loadMember(UUID) called: %s", memberId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMemberUseCase.execute(GetMemberUseCase.Request(memberId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveMember(): Job {
        val offsetFormatter = DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
        val congregationUi = CongregationUi()
        congregationUi.id = congregation.value.item.itemId
        val groupUi = GroupUi(congregation = congregationUi)
        groupUi.id = group.value.item.itemId
        val memberUi = MemberUi(
            group = groupUi,
            memberNum = memberNum.value.value,
            memberName = memberName.value.value,
            surname = surname.value.value,
            patronymic = patronymic.value.value,
            pseudonym = pseudonym.value.value,
            phoneNumber = phoneNumber.value.value,
            memberType = MemberType.valueOf(memberType.value.value),
            dateOfBirth = if (dateOfBirth.value.value.isNotEmpty()) offsetFormatter.parse(
                dateOfBirth.value.value, OffsetDateTime::from
            ) else null,
            dateOfBaptism = if (dateOfBaptism.value.value.isNotEmpty()) offsetFormatter.parse(
                dateOfBaptism.value.value, OffsetDateTime::from
            ) else null,
            inactiveDate = if (inactiveDate.value.value.isNotEmpty()) offsetFormatter.parse(
                inactiveDate.value.value, OffsetDateTime::from
            ) else null
        )
        memberUi.id = if (memberId.value.value.isNotEmpty()) {
            UUID.fromString(memberId.value.value)
        } else null
        Timber.tag(TAG).d("saveMember() called: UI model %s", memberUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveMemberUseCase.execute(SaveMemberUseCase.Request(memberUiMapper.map(memberUi)))
                .collect {
                    Timber.tag(TAG).d("saveMember() collect: %s", it)
                    if (it is Result.Success) setSavedListItem(
                        memberMapper.map(it.data.member).toMembersListItem()
                    )
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<MemberFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val memberUi = uiModel as MemberUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberModel) called: memberUi = %s", memberUi)
        memberUi.id?.let {
            initStateValue(MemberFields.MEMBER_ID, memberId, it.toString())
        }
        initStateValue(
            MemberFields.MEMBER_CONGREGATION, congregation,
            ListItemModel(
                memberUi.group.congregation.id, memberUi.group.congregation.congregationName
            )
        )
        initStateValue(
            MemberFields.MEMBER_GROUP, group,
            ListItemModel(memberUi.group.id, memberUi.group.groupNum.toString())
        )
        initStateValue(MemberFields.MEMBER_NUM, memberNum, memberUi.memberNum)
        initStateValue(MemberFields.MEMBER_NAME, memberName, memberUi.memberName ?: "")
        initStateValue(MemberFields.MEMBER_SURNAME, surname, memberUi.surname ?: "")
        initStateValue(MemberFields.MEMBER_PATRONYMIC, patronymic, memberUi.patronymic ?: "")
        initStateValue(MemberFields.MEMBER_PSEUDONYM, pseudonym, memberUi.pseudonym)
        initStateValue(MemberFields.MEMBER_PHONE_NUMBER, phoneNumber, memberUi.phoneNumber ?: "")
        initStateValue(MemberFields.MEMBER_TYPE, memberType, memberUi.memberType.name)
        initStateValue(
            MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
            memberUi.dateOfBirth?.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                ?: ""
        )
        initStateValue(
            MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
            memberUi.dateOfBaptism?.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                ?: ""
        )
        initStateValue(
            MemberFields.MEMBER_INACTIVE_DATE, inactiveDate,
            memberUi.inactiveDate?.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                ?: ""
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MemberInputEvent.MemberNum ->
                        when (MemberInputValidator.MemberNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_NUM, memberNum, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_NUM, memberNum, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is MemberInputEvent.MemberNum ->
                        setStateValue(
                            MemberFields.MEMBER_NUM, memberNum,
                            MemberInputValidator.MemberNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        MemberInputValidator.MemberNum.errorIdOrNull(memberNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = MemberFields.MEMBER_NUM.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                MemberFields.MEMBER_NUM.name -> memberNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : MemberViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val congregation = MutableStateFlow(InputListItemWrapper())
                override val memberNum = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: MemberUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: MemberFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): MemberUi {
            val groupUi = MemberUi(
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                groupNum = ctx.resources.getInteger(R.integer.def_group1)
            )
            groupUi.id = UUID.randomUUID()
            return groupUi
        }
    }
}