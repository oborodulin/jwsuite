package com.oborodulin.jwsuite.presentation.ui.appsetting

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
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MemberConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.AppSettingUiModelToMemberMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberToMembersListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

private const val TAG = "Presentation.AppSettingViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class AppSettingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: MemberUseCases,
    private val converter: MemberConverter,
    private val memberUiMapper: AppSettingUiModelToMemberMapper,
    private val memberMapper: MemberToMembersListItemMapper
) : AppSettingViewModel,
    DialogViewModel<AppSettingsUiModel, UiState<AppSettingsUiModel>, AppSettingUiAction, UiSingleEvent, AppSettingFields, InputWrapper>(
        state, AppSettingFields.MEMBER_ID.name, AppSettingFields.MEMBER_NUM
    ) {
    private val _memberTypes: MutableStateFlow<MutableMap<MemberType, String>> =
        MutableStateFlow(mutableMapOf())
    override val memberTypes = _memberTypes.asStateFlow()

    override val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_CONGREGATION.name, InputListItemWrapper())
    }

    override val group: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_GROUP.name, InputListItemWrapper())
    }

    override val territoryProcessingPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_NUM.name, InputWrapper())
    }
    override val territoryAtHandPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_NAME.name, InputWrapper())
    }
    override val territoryIdlePeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_SURNAME.name, InputWrapper())
    }
    override val territoryRoomsLimit: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_PATRONYMIC.name, InputWrapper())
    }
    override val territoryMaxRooms: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_PSEUDONYM.name, InputWrapper())
    }
    override val phoneNumber: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_PHONE_NUMBER.name, InputWrapper())
    }
    override val dateOfBirth: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_DATE_OF_BIRTH.name, InputWrapper())
    }
    override val dateOfBaptism: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_DATE_OF_BAPTISM.name, InputWrapper())
    }
    override val memberType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_TYPE.name, InputWrapper())
    }
    override val movementDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_MOVEMENT_DATE.name, InputWrapper())
    }
    override val loginExpiredDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.MEMBER_LOGIN_EXPIRED_DATE.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            //group, memberNum, memberName,
            territoryIdlePeriod,
            territoryRoomsLimit,
            territoryMaxRooms,
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
        for (type in MemberType.values()) _memberTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState(): UiState<AppSettingsUiModel> = UiState.Loading

    override suspend fun handleAction(action: AppSettingUiAction): Job {
        Timber.tag(TAG).d("handleAction(AppSettingUiModelAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is AppSettingUiAction.Load -> when (action.memberId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_new_subheader)
                    submitState(UiState.Success(AppSettingsUiModel()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.member_subheader)
                    loadMember(action.memberId)
                }
            }

            is AppSettingUiAction.Save -> saveMember()
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
        //val offsetFormatter = DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
        val congregationUi = CongregationUi()
        congregationUi.id = congregation.value.item?.itemId
        val groupUi =
            GroupUi(congregation = congregationUi, groupNum = group.value.item?.headline?.toInt())
        groupUi.id = group.value.item?.itemId
        val dateOfBirthOffsetDateTime = if (dateOfBirth.value.value.isNotEmpty())
            LocalDate.parse(
                dateOfBirth.value.value,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            ).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()
        else null
        Timber.tag(TAG).d(
            "saveMember(): dateOfBirth.value.value: %s; dateOfBirthOffsetDateTime = %s",
            dateOfBirth.value.value,
            dateOfBirthOffsetDateTime
        )
        val memberUi = AppSettingsUiModel(
            congregation = congregationUi,
            group = groupUi,
            memberNum = territoryProcessingPeriod.value.value,
            memberName = territoryAtHandPeriod.value.value,
            surname = territoryIdlePeriod.value.value,
            patronymic = territoryRoomsLimit.value.value,
            pseudonym = territoryMaxRooms.value.value,
            phoneNumber = phoneNumber.value.value,
            memberType = MemberType.valueOf(memberType.value.value),
            dateOfBirth = dateOfBirthOffsetDateTime,
            dateOfBaptism = if (dateOfBaptism.value.value.isNotEmpty())
                LocalDate.parse(
                    dateOfBaptism.value.value,
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                ).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()
            else null,
            loginExpiredDate = if (loginExpiredDate.value.value.isNotEmpty())
                LocalDate.parse(
                    loginExpiredDate.value.value,
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                ).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()
            else null,
            movementDate = LocalDate.parse(
                dateOfBaptism.value.value,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            ).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()
//                offsetFormatter.parse(
//                inactiveDate.value.value, OffsetDateTime::from
//            ) else null
        )
        memberUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d("saveMember() called: UI model %s", memberUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveMemberUseCase.execute(SaveMemberUseCase.Request(memberUiMapper.map(memberUi)))
                .collect {
                    Timber.tag(TAG).d("saveMember() collect: %s", it)
                    if (it is Result.Success) {
                        setSavedListItem(memberMapper.map(it.data.member))
                    }
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<AppSettingFields>().map { it.name }
    override fun getPseudonym(
        surname: String?, memberName: String?, groupNum: Int?, memberNum: String?
    ) =
        "${surname?.firstOrNull() ?: ""}${memberName?.firstOrNull() ?: ""}${groupNum?.toString() ?: "0"}${
            memberNum?.let { ".$it" }.orEmpty()
        }"

    override fun initFieldStatesByUiModel(uiModel: AppSettingsUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberModel) called: memberUi = %s", uiModel)
        uiModel.id?.let { initStateValue(AppSettingFields.MEMBER_ID, id, it.toString()) }
        initStateValue(
            AppSettingFields.MEMBER_CONGREGATION, congregation,
            uiModel.congregation.toCongregationsListItem()
        )
        initStateValue(
            AppSettingFields.MEMBER_GROUP, group,
            ListItemModel(uiModel.group?.id, uiModel.group?.groupNum?.toString().orEmpty())
        )
        initStateValue(AppSettingFields.MEMBER_NUM, territoryProcessingPeriod, uiModel.memberNum.orEmpty())
        initStateValue(AppSettingFields.MEMBER_NAME, territoryAtHandPeriod, uiModel.memberName.orEmpty())
        initStateValue(AppSettingFields.MEMBER_SURNAME, territoryIdlePeriod, uiModel.surname.orEmpty())
        initStateValue(AppSettingFields.MEMBER_PATRONYMIC, territoryRoomsLimit, uiModel.patronymic.orEmpty())
        initStateValue(
            AppSettingFields.MEMBER_PSEUDONYM, territoryMaxRooms,
            uiModel.pseudonym.ifEmpty {
                getPseudonym(
                    surname = uiModel.surname,
                    memberName = uiModel.memberName,
                    groupNum = uiModel.group?.groupNum,
                    memberNum = uiModel.memberNum
                )
            }
        )
        initStateValue(
            AppSettingFields.MEMBER_PHONE_NUMBER, phoneNumber, uiModel.phoneNumber.orEmpty()
        )
        initStateValue(
            AppSettingFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
            uiModel.dateOfBirth?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))// .ofPattern(Constants.APP_OFFSET_DATE_TIME))
                .orEmpty()
        )
        initStateValue(
            AppSettingFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
            uiModel.dateOfBaptism?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                .orEmpty()
        )
        initStateValue(AppSettingFields.MEMBER_TYPE, memberType, uiModel.memberType.name)
        initStateValue(
            AppSettingFields.MEMBER_MOVEMENT_DATE, movementDate,
            uiModel.movementDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                .orEmpty()
        )
        initStateValue(
            AppSettingFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
            uiModel.loginExpiredDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                .orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is AppSettingInputEvent.Congregation ->
                        setStateValue(
                            AppSettingFields.MEMBER_CONGREGATION, congregation, event.input, true
                        )

                    is AppSettingInputEvent.Group ->
                        when (AppSettingInputValidator.Group.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_GROUP, group, event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_GROUP, group, event.input
                            )
                        }

                    is AppSettingInputEvent.AppSettingNum ->
                        when (AppSettingInputValidator.AppSettingNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_NUM, territoryProcessingPeriod, event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_NUM, territoryProcessingPeriod, event.input
                            )
                        }

                    is AppSettingInputEvent.Surname ->
                        setStateValue(AppSettingFields.MEMBER_SURNAME, territoryIdlePeriod, event.input, true)

                    is AppSettingInputEvent.AppSettingName ->
                        setStateValue(AppSettingFields.MEMBER_NAME, territoryAtHandPeriod, event.input, true)

                    is AppSettingInputEvent.Patronymic ->
                        setStateValue(
                            AppSettingFields.MEMBER_PATRONYMIC,
                            territoryRoomsLimit,
                            event.input,
                            true
                        )

                    is AppSettingInputEvent.Pseudonym ->
                        when (AppSettingInputValidator.Pseudonym.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_PSEUDONYM, territoryMaxRooms, event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_PSEUDONYM, territoryMaxRooms, event.input
                            )
                        }

                    is AppSettingInputEvent.PhoneNumber ->
                        when (AppSettingInputValidator.PhoneNumber.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_PHONE_NUMBER, phoneNumber, event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_PHONE_NUMBER, phoneNumber, event.input
                            )
                        }

                    is AppSettingInputEvent.DateOfBirth ->
                        when (AppSettingInputValidator.DateOfBirth.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_DATE_OF_BIRTH,
                                dateOfBirth,
                                event.input,
                                true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_DATE_OF_BIRTH, dateOfBirth, event.input
                            )
                        }

                    is AppSettingInputEvent.DateOfBaptism ->
                        when (AppSettingInputValidator.DateOfBaptism.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism, event.input,
                                true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism, event.input
                            )
                        }

                    is AppSettingInputEvent.AppSettingType ->
                        when (AppSettingInputValidator.AppSettingType.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_TYPE, memberType, event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_TYPE, memberType, event.input
                            )
                        }

                    is AppSettingInputEvent.MovementDate ->
                        when (AppSettingInputValidator.MovementDate.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_MOVEMENT_DATE,
                                movementDate,
                                event.input,
                                true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_MOVEMENT_DATE, movementDate, event.input
                            )
                        }

                    is AppSettingInputEvent.LoginExpiredDate ->
                        when (AppSettingInputValidator.LoginExpiredDate.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
                                event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
                                event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is AppSettingInputEvent.Congregation ->
                        setStateValue(
                            AppSettingFields.MEMBER_CONGREGATION, congregation,
                            AppSettingInputValidator.Congregation.errorIdOrNull(event.input.headline)
                        )

                    is AppSettingInputEvent.Group ->
                        setStateValue(
                            AppSettingFields.MEMBER_GROUP, group,
                            AppSettingInputValidator.Group.errorIdOrNull(event.input.headline)
                        )

                    is AppSettingInputEvent.AppSettingNum ->
                        setStateValue(
                            AppSettingFields.MEMBER_NUM, territoryProcessingPeriod,
                            AppSettingInputValidator.AppSettingNum.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.Surname ->
                        setStateValue(AppSettingFields.MEMBER_SURNAME, territoryIdlePeriod, null)

                    is AppSettingInputEvent.AppSettingName ->
                        setStateValue(AppSettingFields.MEMBER_NAME, territoryAtHandPeriod, null)

                    is AppSettingInputEvent.Patronymic ->
                        setStateValue(AppSettingFields.MEMBER_PATRONYMIC, territoryRoomsLimit, null)

                    is AppSettingInputEvent.Pseudonym ->
                        setStateValue(
                            AppSettingFields.MEMBER_PSEUDONYM, territoryMaxRooms,
                            AppSettingInputValidator.Pseudonym.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.PhoneNumber ->
                        setStateValue(
                            AppSettingFields.MEMBER_PHONE_NUMBER, phoneNumber,
                            AppSettingInputValidator.PhoneNumber.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.DateOfBirth ->
                        setStateValue(
                            AppSettingFields.MEMBER_DATE_OF_BIRTH, dateOfBirth,
                            AppSettingInputValidator.DateOfBirth.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.DateOfBaptism ->
                        setStateValue(
                            AppSettingFields.MEMBER_DATE_OF_BAPTISM, dateOfBaptism,
                            AppSettingInputValidator.DateOfBaptism.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.AppSettingType ->
                        setStateValue(
                            AppSettingFields.MEMBER_TYPE, memberType,
                            AppSettingInputValidator.AppSettingType.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.MovementDate ->
                        setStateValue(
                            AppSettingFields.MEMBER_MOVEMENT_DATE, movementDate,
                            AppSettingInputValidator.MovementDate.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.LoginExpiredDate ->
                        setStateValue(
                            AppSettingFields.MEMBER_LOGIN_EXPIRED_DATE, loginExpiredDate,
                            AppSettingInputValidator.LoginExpiredDate.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        AppSettingInputValidator.Group.errorIdOrNull(group.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = AppSettingFields.MEMBER_GROUP.name,
                    errorId = it
                )
            )
        }
        AppSettingInputValidator.AppSettingNum.errorIdOrNull(territoryProcessingPeriod.value.value)?.let {
            inputErrors.add(InputError(fieldName = AppSettingFields.MEMBER_NUM.name, errorId = it))
        }
        AppSettingInputValidator.Pseudonym.errorIdOrNull(territoryMaxRooms.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = AppSettingFields.MEMBER_PSEUDONYM.name, errorId = it)
            )
        }
        AppSettingInputValidator.PhoneNumber.errorIdOrNull(phoneNumber.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = AppSettingFields.MEMBER_PHONE_NUMBER.name, errorId = it)
            )
        }
        AppSettingInputValidator.AppSettingType.errorIdOrNull(memberType.value.value)?.let {
            inputErrors.add(InputError(fieldName = AppSettingFields.MEMBER_TYPE.name, errorId = it))
        }
        AppSettingInputValidator.DateOfBirth.errorIdOrNull(dateOfBirth.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = AppSettingFields.MEMBER_DATE_OF_BIRTH.name, errorId = it)
            )
        }
        AppSettingInputValidator.DateOfBaptism.errorIdOrNull(dateOfBaptism.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = AppSettingFields.MEMBER_DATE_OF_BAPTISM.name, errorId = it)
            )
        }
        AppSettingInputValidator.MovementDate.errorIdOrNull(movementDate.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = AppSettingFields.MEMBER_MOVEMENT_DATE.name, errorId = it)
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (AppSettingFields.valueOf(error.fieldName)) {
                AppSettingFields.MEMBER_GROUP -> group.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_NUM -> territoryProcessingPeriod.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_PSEUDONYM -> territoryMaxRooms.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_PHONE_NUMBER -> phoneNumber.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_TYPE -> memberType.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_DATE_OF_BIRTH -> dateOfBirth.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_DATE_OF_BAPTISM -> dateOfBaptism.value.copy(errorId = error.errorId)
                AppSettingFields.MEMBER_MOVEMENT_DATE -> movementDate.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : AppSettingViewModel {
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

                override val memberTypes = MutableStateFlow(mutableMapOf<MemberType, String>())

                override val id = MutableStateFlow(InputWrapper())
                override val congregation =
                    MutableStateFlow(InputListItemWrapper<CongregationsListItem>())
                override val group = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val territoryProcessingPeriod = MutableStateFlow(InputWrapper())
                override val territoryAtHandPeriod = MutableStateFlow(InputWrapper())
                override val territoryIdlePeriod = MutableStateFlow(InputWrapper())
                override val territoryRoomsLimit = MutableStateFlow(InputWrapper())
                override val territoryMaxRooms = MutableStateFlow(InputWrapper())
                override val phoneNumber = MutableStateFlow(InputWrapper())
                override val dateOfBirth = MutableStateFlow(InputWrapper())
                override val dateOfBaptism = MutableStateFlow(InputWrapper())
                override val memberType = MutableStateFlow(InputWrapper())
                override val movementDate = MutableStateFlow(InputWrapper())
                override val loginExpiredDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun getPseudonym(
                    surname: String?, memberName: String?, groupNum: Int?, memberNum: String?
                ) = ""

                override fun submitAction(action: AppSettingUiAction): Job? = null
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: AppSettingFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): AppSettingsUiModel {
            val memberUi = AppSettingsUiModel(
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