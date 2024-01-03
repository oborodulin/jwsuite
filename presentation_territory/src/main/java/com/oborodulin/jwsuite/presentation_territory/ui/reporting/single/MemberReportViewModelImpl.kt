package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

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
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
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
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
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

private const val TAG = "Reporting.MemberViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class MemberReportViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: MemberUseCases,
    private val getConverter: MemberConverter,
    private val saveConverter: SaveMemberConverter,
    private val memberUiMapper: MemberUiToMemberMapper,
    private val memberMapper: MemberToMembersListItemMapper
) : MemberReportViewModel,
    DialogViewModel<MemberUi, UiState<MemberUi>, MemberReportUiAction, UiSingleEvent, MemberReportFields, InputWrapper>(
        state, MemberReportFields.MEMBER_REPORT_ID.name, MemberReportFields.MEMBER_REPORT_GENDER
    ) {
    private val _memberTypes: MutableStateFlow<MutableMap<TerritoryReportMark, String>> =
        MutableStateFlow(mutableMapOf())
    override val territoryMarks = _memberTypes.asStateFlow()

    override val house: StateFlow<InputListItemWrapper<CongregationsListItem>> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_HOUSE.name, InputListItemWrapper())
    }

    override val room: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_ROOM.name, InputListItemWrapper())
    }

    override val gender: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_GENDER.name, InputWrapper())
    }
    override val age: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_AGE.name, InputWrapper())
    }
    override val desc: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_DESC.name, InputWrapper())
    }
    override val patronymic: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_PATRONYMIC.name, InputWrapper())
    }
    override val pseudonym: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_PSEUDONYM.name, InputWrapper())
    }
    override val phoneNumber: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_PHONE_NUMBER.name, InputWrapper())
    }
    override val dateOfBirth: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_DATE_OF_BIRTH.name, InputWrapper())
    }
    override val dateOfBaptism: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_DATE_OF_BAPTISM.name, InputWrapper())
    }
    override val reportMark: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_MARK.name, InputWrapper())
    }
    override val movementDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_MOVEMENT_DATE.name, InputWrapper())
    }
    override val loginExpiredDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_LOGIN_EXPIRED_DATE.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            //group, memberNum, memberName,
            desc,
            patronymic,
            pseudonym,
            phoneNumber,
            reportMark,
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
        initTerritoryMemberMarks(com.oborodulin.jwsuite.domain.R.array.member_types)
    }

    private fun initTerritoryMemberMarks(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in TerritoryReportMark.entries) _memberTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MemberReportUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(MemberUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MemberReportUiAction.Load -> when (action.memberId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_new_subheader)
                    submitState(UiState.Success(MemberUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_subheader)
                    loadMember(action.memberId)
                }
            }

            is MemberReportUiAction.Save -> saveMember()
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
        congregationUi.id = house.value.item?.itemId
        val groupUi =
            GroupUi(congregation = congregationUi, groupNum = room.value.item?.headline?.toInt())
        groupUi.id = room.value.item?.itemId
        val dateOfBirthOffsetDateTime = dateOfBirth.value.value.toFullFormatOffsetDateTimeOrNull()
        Timber.tag(TAG).d(
            "saveMember(): dateOfBirth.value.value: %s; dateOfBirthOffsetDateTime = %s",
            dateOfBirth.value.value,
            dateOfBirthOffsetDateTime
        )
        val memberUi = MemberUi(
            congregation = congregationUi,
            group = groupUi,
            memberNum = gender.value.value.ifEmpty { null },
            memberName = age.value.value.ifEmpty { null },
            surname = desc.value.value.ifEmpty { null },
            patronymic = patronymic.value.value.ifEmpty { null },
            pseudonym = pseudonym.value.value,
            phoneNumber = phoneNumber.value.value,
            memberType = TerritoryReportMark.valueOf(reportMark.value.value),
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

    override fun stateInputFields() = enumValues<MemberReportFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: MemberUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberModel) called: memberUi = %s", uiModel)
        uiModel.id?.let { initStateValue(MemberReportFields.MEMBER_REPORT_ID, id, it.toString()) }
        initStateValue(
            MemberReportFields.MEMBER_REPORT_HOUSE, house,
            uiModel.congregation.toCongregationsListItem()
        )
        initStateValue(
            MemberReportFields.MEMBER_REPORT_ROOM, room,
            ListItemModel(uiModel.group?.id, uiModel.group?.groupNum?.toString().orEmpty())
        )
        initStateValue(MemberReportFields.MEMBER_REPORT_GENDER, gender, uiModel.memberNum.orEmpty())
        initStateValue(MemberReportFields.MEMBER_REPORT_AGE, age, uiModel.memberName.orEmpty())
        initStateValue(MemberReportFields.MEMBER_REPORT_DESC, desc, uiModel.surname.orEmpty())
        initStateValue(MemberReportFields.MEMBER_PATRONYMIC, patronymic, uiModel.patronymic.orEmpty())
        initStateValue(
            MemberReportFields.MEMBER_PSEUDONYM, pseudonym,
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
            MemberReportFields.MEMBER_PHONE_NUMBER, phoneNumber, uiModel.phoneNumber.orEmpty()
        )
        initStateValue(
            MemberReportFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
            uiModel.dateOfBirth.toShortFormatString()
                .orEmpty() // .ofPattern(Constants.APP_OFFSET_DATE_TIME))
        )
        initStateValue(
            MemberReportFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
            uiModel.dateOfBaptism.toShortFormatString().orEmpty()
        )
        initStateValue(MemberReportFields.MEMBER_REPORT_MARK, reportMark, uiModel.memberType.name)
        initStateValue(
            MemberReportFields.MEMBER_MOVEMENT_DATE, movementDate,
            uiModel.movementDate.toShortFormatString().orEmpty()
        )
        initStateValue(
            MemberReportFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
            uiModel.loginExpiredDate.toShortFormatString().orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MemberReportInputEvent.House -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_HOUSE, house, event.input, true
                    )

                    is MemberReportInputEvent.Room -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_ROOM, room, event.input,
                        MemberReportInputValidator.Room.isValid(event.input.headline)
                    )

                    is MemberReportInputEvent.Gender -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_GENDER, gender, event.input,
                        MemberReportInputValidator.Gender.isValid(event.input)
                    )

                    is MemberReportInputEvent.Desc ->
                        setStateValue(MemberReportFields.MEMBER_REPORT_DESC, desc, event.input, true)

                    is MemberReportInputEvent.Age ->
                        setStateValue(MemberReportFields.MEMBER_REPORT_AGE, age, event.input, true)

                    is MemberReportInputEvent.Patronymic ->
                        setStateValue(MemberReportFields.MEMBER_PATRONYMIC, patronymic, event.input, true)

                    is MemberReportInputEvent.Pseudonym -> setStateValue(
                        MemberReportFields.MEMBER_PSEUDONYM, pseudonym, event.input,
                        MemberReportInputValidator.Pseudonym.isValid(event.input)
                    )

                    is MemberReportInputEvent.PhoneNumber -> setStateValue(
                        MemberReportFields.MEMBER_PHONE_NUMBER, phoneNumber, event.input,
                        MemberReportInputValidator.PhoneNumber.isValid(event.input)
                    )

                    is MemberReportInputEvent.DateOfBirth -> setStateValue(
                        MemberReportFields.MEMBER_DATE_OF_BIRTH, dateOfBirth, event.input,
                        MemberReportInputValidator.DateOfBirth.isValid(event.input)
                    )

                    is MemberReportInputEvent.DateOfBaptism -> setStateValue(
                        MemberReportFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism, event.input,
                        MemberReportInputValidator.DateOfBaptism.isValid(event.input)
                    )

                    is MemberReportInputEvent.ReportMark -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_MARK, reportMark, event.input,
                        MemberReportInputValidator.ReportMark.isValid(event.input)
                    )

                    is MemberReportInputEvent.MovementDate -> setStateValue(
                        MemberReportFields.MEMBER_MOVEMENT_DATE, movementDate, event.input,
                        MemberReportInputValidator.MovementDate.isValid(event.input)
                    )

                    is MemberReportInputEvent.LoginExpiredDate -> setStateValue(
                        MemberReportFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate, event.input,
                        MemberReportInputValidator.LoginExpiredDate.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is MemberReportInputEvent.House -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_HOUSE, house,
                        MemberReportInputValidator.House.errorIdOrNull(event.input.headline)
                    )

                    is MemberReportInputEvent.Room -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_ROOM, room,
                        MemberReportInputValidator.Room.errorIdOrNull(event.input.headline)
                    )

                    is MemberReportInputEvent.Gender -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_GENDER, gender,
                        MemberReportInputValidator.Gender.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.Desc ->
                        setStateValue(MemberReportFields.MEMBER_REPORT_DESC, desc, null)

                    is MemberReportInputEvent.Age ->
                        setStateValue(MemberReportFields.MEMBER_REPORT_AGE, age, null)

                    is MemberReportInputEvent.Patronymic ->
                        setStateValue(MemberReportFields.MEMBER_PATRONYMIC, patronymic, null)

                    is MemberReportInputEvent.Pseudonym -> setStateValue(
                        MemberReportFields.MEMBER_PSEUDONYM, pseudonym,
                        MemberReportInputValidator.Pseudonym.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.PhoneNumber -> setStateValue(
                        MemberReportFields.MEMBER_PHONE_NUMBER, phoneNumber,
                        MemberReportInputValidator.PhoneNumber.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.DateOfBirth -> setStateValue(
                        MemberReportFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
                        MemberReportInputValidator.DateOfBirth.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.DateOfBaptism -> setStateValue(
                        MemberReportFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
                        MemberReportInputValidator.DateOfBaptism.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.ReportMark -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_MARK, reportMark,
                        MemberReportInputValidator.ReportMark.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.MovementDate -> setStateValue(
                        MemberReportFields.MEMBER_MOVEMENT_DATE, movementDate,
                        MemberReportInputValidator.MovementDate.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.LoginExpiredDate -> setStateValue(
                        MemberReportFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
                        MemberReportInputValidator.LoginExpiredDate.errorIdOrNull(event.input)
                    )
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        MemberReportInputValidator.Room.errorIdOrNull(room.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = MemberReportFields.MEMBER_REPORT_ROOM.name, errorId = it))
        }
        MemberReportInputValidator.Gender.errorIdOrNull(gender.value.value)?.let {
            inputErrors.add(InputError(fieldName = MemberReportFields.MEMBER_REPORT_GENDER.name, errorId = it))
        }
        MemberReportInputValidator.Pseudonym.errorIdOrNull(pseudonym.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_PSEUDONYM.name, errorId = it)
            )
        }
        MemberReportInputValidator.PhoneNumber.errorIdOrNull(phoneNumber.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_PHONE_NUMBER.name, errorId = it)
            )
        }
        MemberReportInputValidator.ReportMark.errorIdOrNull(reportMark.value.value)?.let {
            inputErrors.add(InputError(fieldName = MemberReportFields.MEMBER_REPORT_MARK.name, errorId = it))
        }
        MemberReportInputValidator.DateOfBirth.errorIdOrNull(dateOfBirth.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_DATE_OF_BIRTH.name, errorId = it)
            )
        }
        MemberReportInputValidator.DateOfBaptism.errorIdOrNull(dateOfBaptism.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_DATE_OF_BAPTISM.name, errorId = it)
            )
        }
        MemberReportInputValidator.MovementDate.errorIdOrNull(movementDate.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_MOVEMENT_DATE.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (MemberReportFields.valueOf(error.fieldName)) {
                MemberReportFields.MEMBER_REPORT_ROOM -> room.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_REPORT_GENDER -> gender.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_PSEUDONYM -> pseudonym.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_PHONE_NUMBER -> phoneNumber.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_REPORT_MARK -> reportMark.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_DATE_OF_BIRTH -> dateOfBirth.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_DATE_OF_BAPTISM -> dateOfBaptism.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_MOVEMENT_DATE -> movementDate.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : MemberReportViewModel {
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

                override val territoryMarks = MutableStateFlow(mutableMapOf<TerritoryReportMark, String>())

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val house =
                    MutableStateFlow(InputListItemWrapper<CongregationsListItem>())
                override val room = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val gender = MutableStateFlow(InputWrapper())
                override val age = MutableStateFlow(InputWrapper())
                override val desc = MutableStateFlow(InputWrapper())
                override val patronymic = MutableStateFlow(InputWrapper())
                override val pseudonym = MutableStateFlow(InputWrapper())
                override val phoneNumber = MutableStateFlow(InputWrapper())
                override val dateOfBirth = MutableStateFlow(InputWrapper())
                override val dateOfBaptism = MutableStateFlow(InputWrapper())
                override val reportMark = MutableStateFlow(InputWrapper())
                override val movementDate = MutableStateFlow(InputWrapper())
                override val loginExpiredDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: MemberReportUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: MemberReportFields, isFocused: Boolean
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
                memberType = TerritoryReportMark.PREACHER,
                dateOfBirth = "1981-08-01T14:29:10.212+03:00".toOffsetDateTime(),
                dateOfBaptism = "1994-06-14T14:29:10.212+03:00".toOffsetDateTime()
            )
            memberUi.id = UUID.randomUUID()
            return memberUi
        }
    }
}