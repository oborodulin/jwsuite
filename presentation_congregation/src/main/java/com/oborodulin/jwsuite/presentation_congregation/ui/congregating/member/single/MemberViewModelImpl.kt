package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.home.common.util.toFullFormatOffsetDateTime
import com.oborodulin.home.common.util.toFullFormatOffsetDateTimeOrNull
import com.oborodulin.home.common.util.toOffsetDateTime
import com.oborodulin.home.common.util.toShortFormatString
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.types.MemberType
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MemberConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.SaveMemberConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MemberToMembersListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MemberUiToMemberMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
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
import kotlinx.coroutines.flow.asStateFlow
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

private const val TAG = "Congregating.MemberViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class MemberViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: MemberUseCases,
    private val getConverter: MemberConverter,
    private val saveConverter: SaveMemberConverter,
    private val memberUiMapper: MemberUiToMemberMapper,
    private val memberMapper: MemberToMembersListItemMapper
) : MemberViewModel,
    DialogViewModel<MemberUi, UiState<MemberUi>, MemberUiAction, UiSingleEvent, MemberFields, InputWrapper>(
        state, MemberFields.MEMBER_ID.name, MemberFields.MEMBER_NUM
    ) {
    private val _memberTypes: MutableStateFlow<MutableMap<MemberType, String>> =
        MutableStateFlow(mutableMapOf())
    override val memberTypes = _memberTypes.asStateFlow()

    override val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>> by lazy {
        state.getStateFlow(MemberFields.MEMBER_CONGREGATION.name, InputListItemWrapper())
    }

    override val group: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
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
    override val dateOfBirth: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_DATE_OF_BIRTH.name, InputWrapper())
    }
    override val dateOfBaptism: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_DATE_OF_BAPTISM.name, InputWrapper())
    }
    override val memberType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_TYPE.name, InputWrapper())
    }
    override val movementDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_MOVEMENT_DATE.name, InputWrapper())
    }
    override val loginExpiredDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberFields.MEMBER_LOGIN_EXPIRED_DATE.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            //group, memberNum, memberName,
            surname,
            patronymic,
            pseudonym,
            phoneNumber,
            memberType,
            dateOfBirth,
            dateOfBaptism,
            loginExpiredDate,
            movementDate
        )
        { stateFlowsArray ->
            var errorIdResult = true
            for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
            errorIdResult
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initMemberTypes(com.oborodulin.jwsuite.domain.R.array.member_types)
    }

    private fun initMemberTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in MemberType.entries) _memberTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState(): UiState<MemberUi> = UiState.Loading

    override suspend fun handleAction(action: MemberUiAction): Job {
        Timber.tag(TAG).d("handleAction(MemberUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MemberUiAction.Load -> when (action.memberId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_new_subheader)
                    submitState(UiState.Success(MemberUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_subheader)
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
                    getConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveMember(): Job {
        //val offsetFormatter = DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
        val congregationUi = CongregationUi()
        congregationUi.id = congregation.value.item?.itemId
        val groupUi =
            GroupUi(congregation = congregationUi, groupNum = group.value.item?.headline?.toInt())
        groupUi.id = group.value.item?.itemId
        val dateOfBirthOffsetDateTime = dateOfBirth.value.value.toFullFormatOffsetDateTimeOrNull()
        Timber.tag(TAG).d(
            "saveMember(): dateOfBirth.value.value: %s; dateOfBirthOffsetDateTime = %s",
            dateOfBirth.value.value,
            dateOfBirthOffsetDateTime
        )
        val memberUi = MemberUi(
            congregation = congregationUi,
            group = groupUi,
            memberNum = memberNum.value.value.ifEmpty { null },
            memberName = memberName.value.value.ifEmpty { null },
            surname = surname.value.value.ifEmpty { null },
            patronymic = patronymic.value.value.ifEmpty { null },
            pseudonym = pseudonym.value.value,
            phoneNumber = phoneNumber.value.value,
            memberType = MemberType.valueOf(memberType.value.value),
            movementDate = movementDate.value.value.toFullFormatOffsetDateTime(),
            dateOfBirth = dateOfBirthOffsetDateTime,
            dateOfBaptism = dateOfBaptism.value.value.toFullFormatOffsetDateTimeOrNull(),
            loginExpiredDate = loginExpiredDate.value.value.toFullFormatOffsetDateTimeOrNull()
        )
        memberUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d("saveMember() called: UI model %s", memberUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveMemberUseCase.execute(SaveMemberUseCase.Request(memberUiMapper.map(memberUi)))
                .map {
                    saveConverter.convert(it)
                }
                .collect {
                    Timber.tag(TAG).d("saveMember() collect: %s", it)
                    if (it is UiState.Success) {
                        setSavedListItem(it.data.toMembersListItem())
                    }
                    submitState(it)
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<MemberFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: MemberUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberModel) called: memberUi = %s", uiModel)
        uiModel.id?.let { initStateValue(MemberFields.MEMBER_ID, id, it.toString()) }
        initStateValue(
            MemberFields.MEMBER_CONGREGATION, congregation,
            uiModel.congregation.toCongregationsListItem()
        )
        initStateValue(
            MemberFields.MEMBER_GROUP, group,
            ListItemModel(uiModel.group?.id, uiModel.group?.groupNum?.toString().orEmpty())
        )
        initStateValue(MemberFields.MEMBER_NUM, memberNum, uiModel.memberNum.orEmpty())
        initStateValue(MemberFields.MEMBER_NAME, memberName, uiModel.memberName.orEmpty())
        initStateValue(MemberFields.MEMBER_SURNAME, surname, uiModel.surname.orEmpty())
        initStateValue(MemberFields.MEMBER_PATRONYMIC, patronymic, uiModel.patronymic.orEmpty())
        initStateValue(
            MemberFields.MEMBER_PSEUDONYM, pseudonym,
            uiModel.pseudonym.ifEmpty {
                Member.getPseudonym(
                    surname = uiModel.surname,
                    memberName = uiModel.memberName,
                    groupNum = uiModel.group?.groupNum,
                    memberNum = uiModel.memberNum
                )
            }
        )
        initStateValue(
            MemberFields.MEMBER_PHONE_NUMBER, phoneNumber, uiModel.phoneNumber.orEmpty()
        )
        initStateValue(
            MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
            uiModel.dateOfBirth.toShortFormatString()
                .orEmpty() // .ofPattern(Constants.APP_OFFSET_DATE_TIME))
        )
        initStateValue(
            MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
            uiModel.dateOfBaptism.toShortFormatString().orEmpty()
        )
        initStateValue(MemberFields.MEMBER_TYPE, memberType, uiModel.memberType.name)
        initStateValue(
            MemberFields.MEMBER_MOVEMENT_DATE, movementDate,
            uiModel.movementDate.toShortFormatString().orEmpty()
        )
        initStateValue(
            MemberFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
            uiModel.loginExpiredDate.toShortFormatString().orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MemberInputEvent.Congregation -> setStateValue(
                        MemberFields.MEMBER_CONGREGATION, congregation, event.input, true
                    )

                    is MemberInputEvent.Group -> setStateValue(
                        MemberFields.MEMBER_GROUP, group, event.input,
                        MemberInputValidator.Group.isValid(event.input.headline)
                    )

                    is MemberInputEvent.MemberNum -> setStateValue(
                        MemberFields.MEMBER_NUM, memberNum, event.input,
                        MemberInputValidator.MemberNum.isValid(event.input)
                    )

                    is MemberInputEvent.Surname ->
                        setStateValue(MemberFields.MEMBER_SURNAME, surname, event.input, true)

                    is MemberInputEvent.MemberName ->
                        setStateValue(MemberFields.MEMBER_NAME, memberName, event.input, true)

                    is MemberInputEvent.Patronymic ->
                        setStateValue(MemberFields.MEMBER_PATRONYMIC, patronymic, event.input, true)

                    is MemberInputEvent.Pseudonym -> setStateValue(
                        MemberFields.MEMBER_PSEUDONYM, pseudonym, event.input,
                        MemberInputValidator.Pseudonym.isValid(event.input)
                    )

                    is MemberInputEvent.PhoneNumber -> setStateValue(
                        MemberFields.MEMBER_PHONE_NUMBER, phoneNumber, event.input,
                        MemberInputValidator.PhoneNumber.isValid(event.input)
                    )

                    is MemberInputEvent.DateOfBirth -> setStateValue(
                        MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth, event.input,
                        MemberInputValidator.DateOfBirth.isValid(event.input)
                    )

                    is MemberInputEvent.DateOfBaptism -> setStateValue(
                        MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism, event.input,
                        MemberInputValidator.DateOfBaptism.isValid(event.input)
                    )

                    is MemberInputEvent.MemberType -> setStateValue(
                        MemberFields.MEMBER_TYPE, memberType, event.input,
                        MemberInputValidator.MemberType.isValid(event.input)
                    )

                    is MemberInputEvent.MovementDate -> setStateValue(
                        MemberFields.MEMBER_MOVEMENT_DATE, movementDate, event.input,
                        MemberInputValidator.MovementDate.isValid(event.input)
                    )

                    is MemberInputEvent.LoginExpiredDate -> setStateValue(
                        MemberFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate, event.input,
                        MemberInputValidator.LoginExpiredDate.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is MemberInputEvent.Congregation -> setStateValue(
                        MemberFields.MEMBER_CONGREGATION, congregation,
                        MemberInputValidator.Congregation.errorIdOrNull(event.input.headline)
                    )

                    is MemberInputEvent.Group -> setStateValue(
                        MemberFields.MEMBER_GROUP, group,
                        MemberInputValidator.Group.errorIdOrNull(event.input.headline)
                    )

                    is MemberInputEvent.MemberNum -> setStateValue(
                        MemberFields.MEMBER_NUM, memberNum,
                        MemberInputValidator.MemberNum.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.Surname ->
                        setStateValue(MemberFields.MEMBER_SURNAME, surname, null)

                    is MemberInputEvent.MemberName ->
                        setStateValue(MemberFields.MEMBER_NAME, memberName, null)

                    is MemberInputEvent.Patronymic ->
                        setStateValue(MemberFields.MEMBER_PATRONYMIC, patronymic, null)

                    is MemberInputEvent.Pseudonym -> setStateValue(
                        MemberFields.MEMBER_PSEUDONYM, pseudonym,
                        MemberInputValidator.Pseudonym.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.PhoneNumber -> setStateValue(
                        MemberFields.MEMBER_PHONE_NUMBER, phoneNumber,
                        MemberInputValidator.PhoneNumber.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.DateOfBirth -> setStateValue(
                        MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
                        MemberInputValidator.DateOfBirth.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.DateOfBaptism -> setStateValue(
                        MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
                        MemberInputValidator.DateOfBaptism.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.MemberType -> setStateValue(
                        MemberFields.MEMBER_TYPE, memberType,
                        MemberInputValidator.MemberType.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.MovementDate -> setStateValue(
                        MemberFields.MEMBER_MOVEMENT_DATE, movementDate,
                        MemberInputValidator.MovementDate.errorIdOrNull(event.input)
                    )

                    is MemberInputEvent.LoginExpiredDate -> setStateValue(
                        MemberFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
                        MemberInputValidator.LoginExpiredDate.errorIdOrNull(event.input)
                    )
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        MemberInputValidator.Group.errorIdOrNull(group.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = MemberFields.MEMBER_GROUP.name, errorId = it))
        }
        MemberInputValidator.MemberNum.errorIdOrNull(memberNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = MemberFields.MEMBER_NUM.name, errorId = it))
        }
        MemberInputValidator.Pseudonym.errorIdOrNull(pseudonym.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberFields.MEMBER_PSEUDONYM.name, errorId = it)
            )
        }
        MemberInputValidator.PhoneNumber.errorIdOrNull(phoneNumber.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberFields.MEMBER_PHONE_NUMBER.name, errorId = it)
            )
        }
        MemberInputValidator.MemberType.errorIdOrNull(memberType.value.value)?.let {
            inputErrors.add(InputError(fieldName = MemberFields.MEMBER_TYPE.name, errorId = it))
        }
        MemberInputValidator.DateOfBirth.errorIdOrNull(dateOfBirth.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberFields.MEMBER_DATE_OF_BIRTH.name, errorId = it)
            )
        }
        MemberInputValidator.DateOfBaptism.errorIdOrNull(dateOfBaptism.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberFields.MEMBER_DATE_OF_BAPTISM.name, errorId = it)
            )
        }
        MemberInputValidator.MovementDate.errorIdOrNull(movementDate.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberFields.MEMBER_MOVEMENT_DATE.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (MemberFields.valueOf(error.fieldName)) {
                MemberFields.MEMBER_GROUP -> group.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_NUM -> memberNum.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_PSEUDONYM -> pseudonym.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_PHONE_NUMBER -> phoneNumber.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_TYPE -> memberType.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_DATE_OF_BIRTH -> dateOfBirth.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_DATE_OF_BAPTISM -> dateOfBaptism.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_MOVEMENT_DATE -> movementDate.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : MemberViewModel {
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

                override val memberTypes = MutableStateFlow(mutableMapOf<MemberType, String>())

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val congregation =
                    MutableStateFlow(InputListItemWrapper<CongregationsListItem>())
                override val group = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val memberNum = MutableStateFlow(InputWrapper())
                override val memberName = MutableStateFlow(InputWrapper())
                override val surname = MutableStateFlow(InputWrapper())
                override val patronymic = MutableStateFlow(InputWrapper())
                override val pseudonym = MutableStateFlow(InputWrapper())
                override val phoneNumber = MutableStateFlow(InputWrapper())
                override val dateOfBirth = MutableStateFlow(InputWrapper())
                override val dateOfBaptism = MutableStateFlow(InputWrapper())
                override val memberType = MutableStateFlow(InputWrapper())
                override val movementDate = MutableStateFlow(InputWrapper())
                override val loginExpiredDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: MemberUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: MemberFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): MemberUi {
            val memberUi = MemberUi(
                group = GroupViewModelImpl.previewUiModel(ctx),
                memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
                memberName = ctx.resources.getString(R.string.def_ivanov_member_name),
                surname = ctx.resources.getString(R.string.def_ivanov_member_surname),
                patronymic = ctx.resources.getString(R.string.def_ivanov_member_patronymic),
                pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym),
                phoneNumber = "+79493851487",
                memberType = MemberType.PREACHER,
                dateOfBirth = "1981-08-01T14:29:10.212+03:00".toOffsetDateTime(),
                dateOfBaptism = "1994-06-14T14:29:10.212+03:00".toOffsetDateTime()
            )
            memberUi.id = UUID.randomUUID()
            return memberUi
        }
    }
}