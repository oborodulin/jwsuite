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
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.TerritoryReportUseCases
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toMembersListItem
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryMemberReportConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.toHousesListItem
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

private const val TAG = "Reporting.MemberReportViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class MemberReportViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: TerritoryReportUseCases,
    private val converter: TerritoryMemberReportConverter
) : MemberReportViewModel,
    DialogViewModel<TerritoryMemberReportUi, UiState<TerritoryMemberReportUi>, MemberReportUiAction, UiSingleEvent, MemberReportFields, InputWrapper>(
        state, MemberReportFields.MEMBER_REPORT_ID.name//, MemberReportFields.MEMBER_REPORT_GENDER
    ) {
    private val _reportMarks: MutableStateFlow<MutableMap<TerritoryReportMark, String>> =
        MutableStateFlow(mutableMapOf())
    override val reportMarks = _reportMarks.asStateFlow()

    override val territoryStreet: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            MemberReportFields.MEMBER_REPORT_TERRITORY_STREET.name, InputListItemWrapper()
        )
    }
    override val house: StateFlow<InputListItemWrapper<HousesListItem>> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_HOUSE.name, InputListItemWrapper())
    }
    override val room: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_ROOM.name, InputListItemWrapper())
    }
    override val reportMark: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_MARK.name, InputWrapper())
    }
    override val language: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_LANGUAGE.name, InputListItemWrapper())
    }
    override val gender: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_GENDER.name, InputWrapper())
    }
    override val age: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_AGE.name, InputWrapper())
    }
    override val isProcessed: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_IS_PROCESSED.name, InputWrapper())
    }
    override val desc: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MemberReportFields.MEMBER_REPORT_DESC.name, InputWrapper())
    }

    override val areInputsValid =
        combine(reportMark, age)
        { stateFlowsArray ->
            var errorIdResult = true
            for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
            errorIdResult
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initMemberReportMarks(com.oborodulin.jwsuite.domain.R.array.territory_marks)
    }

    private fun initMemberReportMarks(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in TerritoryReportMark.entries) _reportMarks.value[type] = resArray[type.ordinal]
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MemberReportUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(TerritoryMemberReportUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MemberReportUiAction.Load -> when (action.territoryMemberReportId) {
                null -> {
                    setDialogTitleResId(R.string.territory_report_new_subheader)
                    submitState(UiState.Success(TerritoryMemberReportUi()))
                }

                else -> {
                    setDialogTitleResId(R.string.territory_report_subheader)
                    loadMemberReport(action.territoryMemberReportId)
                }
            }

            is MemberReportUiAction.Save -> saveMemberReport()
        }
        return job
    }

    private fun loadMemberReport(
        territoryMemberReportId: UUID? = null,
        territoryStreetId: UUID? = null,
        houseId: UUID? = null,
        roomId: UUID? = null
    ): Job {
        Timber.tag(TAG).d(
            "loadMemberReport(...) called: territoryMemberReportId = %s; territoryStreetId = %s; houseId = %s; roomId = %s",
            territoryMemberReportId, territoryStreetId, houseId, roomId
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMemberReportUseCase.execute(
                GetMemberReportUseCase.Request(
                    territoryReportId = territoryMemberReportId,
                    territoryStreetId = territoryStreetId,
                    houseId = houseId,
                    roomId = roomId
                )
            )
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveMemberReport(): Job {
        val territoryStreetUi = TerritoryStreetUi()
        territoryStreetUi.id = territoryStreet.value.item?.itemId
        val houseUi = HouseUi()
        houseUi.id = house.value.item?.itemId
        val roomUi = RoomUi()
        roomUi.id = room.value.item?.itemId
        val territoryMemberReportUi = TerritoryMemberReportUi(
            territoryStreet = territoryStreetUi,
            house = houseUi,
            room = roomUi,
            territoryReportMark = TerritoryReportMark.valueOf(reportMark.value.value),
            languageCode = null,
            gender = gender.value.value.toBooleanStrictOrNull(),
            age = age.value.value.toIntOrNull(),
            isProcessed = isProcessed.value.value.toBoolean(),
            territoryReportDesc = desc.value.value.ifEmpty { null }
        )
        territoryMemberReportUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d("saveMemberReport() called: UI model %s", territoryMemberReportUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveReportHouseUseCase.execute(
                SaveReportHouseUseCase.Request(
                    memberUiMapper.map(
                        territoryMemberReportUi
                    )
                )
            )
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

    override fun initFieldStatesByUiModel(uiModel: TerritoryMemberReportUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(TerritoryMemberReportUi) called: uiModel = %s", uiModel)
        uiModel.id?.let { initStateValue(MemberReportFields.MEMBER_REPORT_ID, id, it.toString()) }
        initStateValue(
            MemberReportFields.MEMBER_REPORT_TERRITORY_STREET, territoryStreet,
            ListItemModel(
                uiModel.territoryStreet?.id,
                uiModel.territoryStreet?.street?.streetFullName.orEmpty()
            )
        )
        uiModel.house?.let {
            initStateValue(MemberReportFields.MEMBER_REPORT_HOUSE, house, it.toHousesListItem())
        }
        initStateValue(
            MemberReportFields.MEMBER_REPORT_ROOM, room,
            ListItemModel(uiModel.room?.id, uiModel.room?.roomNum?.toString().orEmpty())
        )
        initStateValue(
            MemberReportFields.MEMBER_REPORT_MARK, reportMark, uiModel.territoryReportMark.name
        )
        initStateValue(MemberReportFields.MEMBER_REPORT_GENDER, gender, uiModel.gender.toString())
        initStateValue(MemberReportFields.MEMBER_REPORT_AGE, age, uiModel.age?.toString().orEmpty())
        initStateValue(
            MemberReportFields.MEMBER_REPORT_IS_PROCESSED, isProcessed,
            uiModel.isProcessed.toString()
        )
        initStateValue(
            MemberReportFields.MEMBER_REPORT_DESC, desc, uiModel.territoryReportDesc.orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MemberReportInputEvent.TerritoryStreet -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_TERRITORY_STREET, territoryStreet,
                        event.input,
                        MemberReportInputValidator.TerritoryStreet.isValid(event.input.headline)
                    )

                    is MemberReportInputEvent.House -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_HOUSE, house, event.input,
                        MemberReportInputValidator.House.isValid(event.input.headline)
                    )

                    is MemberReportInputEvent.Room -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_ROOM, room, event.input,
                        MemberReportInputValidator.Room.isValid(event.input.headline)
                    )

                    is MemberReportInputEvent.MemberReportMark -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_MARK, reportMark, event.input,
                        MemberReportInputValidator.MemberReportMark.isValid(event.input)
                    )

                    is MemberReportInputEvent.Language -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_LANGUAGE, language, event.input,
                        MemberReportInputValidator.Language.isValid(
                            event.input.headline, reportMark.value.value
                        )
                    )

                    is MemberReportInputEvent.Gender -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_GENDER, gender, event.input.toString(),
                        true
                    )

                    is MemberReportInputEvent.Age -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_AGE, age, event.input,
                        MemberReportInputValidator.Age.isValid(event.input)

                    )

                    is MemberReportInputEvent.Desc -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_DESC, desc, event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is MemberReportInputEvent.TerritoryStreet -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_TERRITORY_STREET, territoryStreet,
                        MemberReportInputValidator.TerritoryStreet.errorIdOrNull(event.input.headline)
                    )

                    is MemberReportInputEvent.House -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_HOUSE, house,
                        MemberReportInputValidator.House.errorIdOrNull(event.input.headline)
                    )

                    is MemberReportInputEvent.Room -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_ROOM, room,
                        MemberReportInputValidator.Room.errorIdOrNull(event.input.headline)
                    )

                    is MemberReportInputEvent.MemberReportMark -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_MARK, reportMark,
                        MemberReportInputValidator.MemberReportMark.errorIdOrNull(event.input)
                    )

                    is MemberReportInputEvent.Language -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_LANGUAGE, language,
                        MemberReportInputValidator.Language.errorIdOrNull(
                            event.input.headline,
                            reportMark.value.value
                        )
                    )

                    is MemberReportInputEvent.Gender -> setStateValue(
                        MemberReportFields.MEMBER_REPORT_GENDER, gender, null
                    )

                    is MemberReportInputEvent.Age ->
                        setStateValue(MemberReportFields.MEMBER_REPORT_AGE, age, null)

                    is MemberReportInputEvent.Desc ->
                        setStateValue(MemberReportFields.MEMBER_REPORT_DESC, desc, null)
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        MemberReportInputValidator.TerritoryStreet.errorIdOrNull(territoryStreet.value.item?.headline)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = MemberReportFields.MEMBER_REPORT_TERRITORY_STREET.name,
                        errorId = it
                    )
                )
            }
        MemberReportInputValidator.House.errorIdOrNull(house.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_REPORT_HOUSE.name, errorId = it)
            )
        }
        MemberReportInputValidator.Room.errorIdOrNull(room.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_REPORT_ROOM.name, errorId = it)
            )
        }
        MemberReportInputValidator.MemberReportMark.errorIdOrNull(reportMark.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_REPORT_MARK.name, errorId = it)
            )
        }
        MemberReportInputValidator.Language.errorIdOrNull(language.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_REPORT_LANGUAGE.name, errorId = it)
            )
        }
        MemberReportInputValidator.Age.errorIdOrNull(age.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = MemberReportFields.MEMBER_REPORT_AGE.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (MemberReportFields.valueOf(error.fieldName)) {
                MemberReportFields.MEMBER_REPORT_TERRITORY_STREET -> territoryStreet.value.copy(
                    errorId = error.errorId
                )

                MemberReportFields.MEMBER_REPORT_HOUSE -> house.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_REPORT_ROOM -> room.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_REPORT_MARK -> reportMark.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_REPORT_LANGUAGE -> language.value.copy(errorId = error.errorId)
                MemberReportFields.MEMBER_REPORT_AGE -> age.value.copy(errorId = error.errorId)
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

                override val reportMarks =
                    MutableStateFlow(mutableMapOf<TerritoryReportMark, String>())

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val territoryStreet =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val house =
                    MutableStateFlow(InputListItemWrapper<HousesListItem>())
                override val room = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val reportMark = MutableStateFlow(InputWrapper())
                override val language = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val gender = MutableStateFlow(InputWrapper())
                override val age = MutableStateFlow(InputWrapper())
                override val isProcessed = MutableStateFlow(InputWrapper())
                override val desc = MutableStateFlow(InputWrapper())

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

        fun previewUiModel(ctx: Context): TerritoryMemberReportUi {
            val memberUi = TerritoryMemberReportUi(
                house = HouseViewModelImpl.previewUiModel(ctx),
                territoryMemberId = UUID.randomUUID(),
                territoryReportMark = TerritoryReportMark.PP,
                languageCode = null,
                gender = true,
                age = 45,
                isProcessed = false,
                territoryReportDesc = "Александр"
            )
            memberUi.id = UUID.randomUUID()
            return memberUi
        }
    }
}