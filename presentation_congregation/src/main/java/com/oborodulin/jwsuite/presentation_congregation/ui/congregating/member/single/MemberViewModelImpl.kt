package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import android.content.Context
import androidx.annotation.ArrayRes
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
import com.oborodulin.home.common.util.Constants
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MemberConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberToMembersListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberUiToMemberMapper
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
    private val resHelper: ResourcesHelper,
    private val useCases: MemberUseCases,
    private val converter: MemberConverter,
    private val memberUiMapper: MemberUiToMemberMapper,
    private val memberMapper: MemberToMembersListItemMapper
) : MemberViewModel,
    DialogSingleViewModel<MemberUi, UiState<MemberUi>, MemberUiAction, UiSingleEvent, MemberFields, InputWrapper>(
        state, MemberFields.MEMBER_ID.name, MemberFields.MEMBER_NUM
    ) {
    private val _memberTypes: MutableStateFlow<MutableMap<MemberType, String>> =
        MutableStateFlow(mutableMapOf())
    override val memberTypes = _memberTypes.asStateFlow()

    override val congregation: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
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

    init {
        initMemberTypes(com.oborodulin.jwsuite.domain.R.array.member_types)
    }

    private fun initMemberTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in MemberType.values()) _memberTypes.value[type] = resArray[type.ordinal]
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
        congregationUi.id = congregation.value.item?.itemId
        val groupUi = GroupUi(congregation = congregationUi)
        groupUi.id = group.value.item?.itemId
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
        memberUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d("saveMember() called: UI model %s", memberUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveMemberUseCase.execute(SaveMemberUseCase.Request(memberUiMapper.map(memberUi)))
                .collect {
                    Timber.tag(TAG).d("saveMember() collect: %s", it)
                    if (it is Result.Success) setSavedListItem(memberMapper.map(it.data.member))
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<MemberFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: MemberUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberModel) called: memberUi = %s", uiModel)
        uiModel.id?.let {
            initStateValue(MemberFields.MEMBER_ID, id, it.toString())
        }
        initStateValue(
            MemberFields.MEMBER_CONGREGATION, congregation,
            ListItemModel(
                uiModel.group.congregation.id, uiModel.group.congregation.congregationName
            )
        )
        initStateValue(
            MemberFields.MEMBER_GROUP, group,
            ListItemModel(uiModel.group.id, uiModel.group.groupNum.toString())
        )
        initStateValue(MemberFields.MEMBER_NUM, memberNum, uiModel.memberNum)
        initStateValue(MemberFields.MEMBER_NAME, memberName, uiModel.memberName.orEmpty())
        initStateValue(MemberFields.MEMBER_SURNAME, surname, uiModel.surname.orEmpty())
        initStateValue(MemberFields.MEMBER_PATRONYMIC, patronymic, uiModel.patronymic.orEmpty())
        initStateValue(MemberFields.MEMBER_PSEUDONYM, pseudonym, uiModel.pseudonym)
        initStateValue(
            MemberFields.MEMBER_PHONE_NUMBER, phoneNumber, uiModel.phoneNumber.orEmpty()
        )
        initStateValue(MemberFields.MEMBER_TYPE, memberType, uiModel.memberType.name)
        initStateValue(
            MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
            uiModel.dateOfBirth?.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                .orEmpty()
        )
        initStateValue(
            MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
            uiModel.dateOfBaptism?.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                .orEmpty()
        )
        initStateValue(
            MemberFields.MEMBER_INACTIVE_DATE, inactiveDate,
            uiModel.inactiveDate?.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                .orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MemberInputEvent.Congregation ->
                        setStateValue(
                            MemberFields.MEMBER_CONGREGATION, congregation, event.input, true
                        )

                    is MemberInputEvent.Group ->
                        when (MemberInputValidator.Group.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_GROUP, group, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_GROUP, group, event.input
                            )
                        }

                    is MemberInputEvent.MemberNum ->
                        when (MemberInputValidator.MemberNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_NUM, memberNum, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_NUM, memberNum, event.input
                            )
                        }

                    is MemberInputEvent.Pseudonym ->
                        when (MemberInputValidator.Pseudonym.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_PSEUDONYM, pseudonym, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_PSEUDONYM, pseudonym, event.input
                            )
                        }

                    is MemberInputEvent.PhoneNumber ->
                        when (MemberInputValidator.PhoneNumber.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_PHONE_NUMBER, phoneNumber, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_PHONE_NUMBER, phoneNumber, event.input
                            )
                        }

                    is MemberInputEvent.MemberType ->
                        when (MemberInputValidator.MemberType.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_TYPE, memberType, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_TYPE, memberType, event.input
                            )
                        }

                    is MemberInputEvent.DateOfBirth ->
                        when (MemberInputValidator.DateOfBirth.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth, event.input
                            )
                        }

                    is MemberInputEvent.DateOfBaptism ->
                        when (MemberInputValidator.DateOfBaptism.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism, event.input,
                                true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism, event.input
                            )
                        }

                    is MemberInputEvent.InactiveDate ->
                        when (MemberInputValidator.InactiveDate.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MemberFields.MEMBER_INACTIVE_DATE, inactiveDate, event.input, true
                            )

                            else -> setStateValue(
                                MemberFields.MEMBER_INACTIVE_DATE, inactiveDate, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is MemberInputEvent.Group ->
                        setStateValue(
                            MemberFields.MEMBER_GROUP, group,
                            MemberInputValidator.Group.errorIdOrNull(event.input.headline)
                        )

                    is MemberInputEvent.MemberNum ->
                        setStateValue(
                            MemberFields.MEMBER_NUM, memberNum,
                            MemberInputValidator.MemberNum.errorIdOrNull(event.input)
                        )

                    is MemberInputEvent.Pseudonym ->
                        setStateValue(
                            MemberFields.MEMBER_PSEUDONYM, pseudonym,
                            MemberInputValidator.Pseudonym.errorIdOrNull(event.input)
                        )

                    is MemberInputEvent.PhoneNumber ->
                        setStateValue(
                            MemberFields.MEMBER_PHONE_NUMBER, phoneNumber,
                            MemberInputValidator.PhoneNumber.errorIdOrNull(event.input)
                        )

                    is MemberInputEvent.MemberType ->
                        setStateValue(
                            MemberFields.MEMBER_TYPE, memberType,
                            MemberInputValidator.MemberType.errorIdOrNull(event.input)
                        )

                    is MemberInputEvent.DateOfBirth ->
                        setStateValue(
                            MemberFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
                            MemberInputValidator.DateOfBirth.errorIdOrNull(event.input)
                        )

                    is MemberInputEvent.DateOfBaptism ->
                        setStateValue(
                            MemberFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
                            MemberInputValidator.DateOfBaptism.errorIdOrNull(event.input)
                        )

                    is MemberInputEvent.InactiveDate ->
                        setStateValue(
                            MemberFields.MEMBER_INACTIVE_DATE, inactiveDate,
                            MemberInputValidator.InactiveDate.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
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
        MemberInputValidator.InactiveDate.errorIdOrNull(inactiveDate.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberFields.MEMBER_INACTIVE_DATE.name, errorId = it)
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                MemberFields.MEMBER_GROUP.name -> group.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_NUM.name -> memberNum.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_PSEUDONYM.name -> pseudonym.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_PHONE_NUMBER.name -> phoneNumber.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_TYPE.name -> memberType.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_DATE_OF_BIRTH.name -> dateOfBirth.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_DATE_OF_BAPTISM.name -> dateOfBaptism.value.copy(errorId = error.errorId)
                MemberFields.MEMBER_INACTIVE_DATE.name -> inactiveDate.value.copy(errorId = error.errorId)
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

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val memberTypes = MutableStateFlow(mutableMapOf<MemberType, String>())

                override val congregation = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val group = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val memberNum = MutableStateFlow(InputWrapper())
                override val memberName = MutableStateFlow(InputWrapper())
                override val surname = MutableStateFlow(InputWrapper())
                override val patronymic = MutableStateFlow(InputWrapper())
                override val pseudonym = MutableStateFlow(InputWrapper())
                override val phoneNumber = MutableStateFlow(InputWrapper())
                override val memberType = MutableStateFlow(InputWrapper())
                override val dateOfBirth = MutableStateFlow(InputWrapper())
                override val dateOfBaptism = MutableStateFlow(InputWrapper())
                override val inactiveDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
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
            val memberUi = MemberUi(
                group = GroupViewModelImpl.previewUiModel(ctx),
                memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
                memberName = ctx.resources.getString(R.string.def_ivanov_member_name),
                surname = ctx.resources.getString(R.string.def_ivanov_member_surname),
                patronymic = ctx.resources.getString(R.string.def_ivanov_member_patronymic),
                pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym),
                phoneNumber = "+79493851487",
                memberType = MemberType.PREACHER,
                dateOfBirth = Utils.toOffsetDateTime("1981-08-01T14:29:10.212+03:00"),
                dateOfBaptism = Utils.toOffsetDateTime("1994-06-14T14:29:10.212+03:00")
            )
            memberUi.id = UUID.randomUUID()
            return memberUi
        }
    }
}