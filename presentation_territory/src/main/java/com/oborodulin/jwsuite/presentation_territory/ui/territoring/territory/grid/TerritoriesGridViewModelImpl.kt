package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.extensions.toFullFormatOffsetDateTime
import com.oborodulin.home.common.extensions.toOffsetDateTime
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_LIST
import com.oborodulin.jwsuite.data_territory.R
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetProcessAndLocationTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.HandOutTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.ProcessTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoriesGridConverter
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Territoring.TerritoriesListViewModelImpl"

@HiltViewModel
class TerritoriesGridViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryUseCases,
    private val listConverter: TerritoriesGridConverter
) : TerritoriesGridViewModel,
    DialogViewModel<List<TerritoriesListItem>, UiState<List<TerritoriesListItem>>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent, TerritoriesFields, InputWrapper>(
        state//, TerritoriesFields.TERRITORY_GRID_ID.name
        //TerritoriesFields.TERRITORY_MEMBER
    ) {
    private val _handOutSearchText = MutableStateFlow(TextFieldValue(""))
    override val handOutSearchText = _handOutSearchText.asStateFlow()
    private val _atWorkSearchText = MutableStateFlow(TextFieldValue(""))
    override val atWorkSearchText = _atWorkSearchText.asStateFlow()
    private val _idleSearchText = MutableStateFlow(TextFieldValue(""))
    override val idleSearchText = _idleSearchText.asStateFlow()

    override fun onHandOutSearchTextChange(text: TextFieldValue) {
        _handOutSearchText.value = text
    }

    override fun onAtWorkSearchTextChange(text: TextFieldValue) {
        _atWorkSearchText.value = text
    }

    override fun onIdleSearchTextChange(text: TextFieldValue) {
        _idleSearchText.value = text
    }

    override val member: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoriesFields.TERRITORY_MEMBER.name, InputListItemWrapper())
    }

    //.ofPattern(Constants.APP_OFFSET_DATE_TIME)
    override val receivingDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            TerritoriesFields.TERRITORY_RECEIVING_DATE.name, InputWrapper(
                OffsetDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
            )
        )
    }

    override val deliveryDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            TerritoriesFields.TERRITORY_DELIVERY_DATE.name, InputWrapper(
                OffsetDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
            )
        )
    }

    private val _checkedListItems: MutableStateFlow<List<TerritoriesListItem>> =
        MutableStateFlow(emptyList())
    override val checkedListItems = _checkedListItems.asStateFlow()

    override val areInputsValid = checkedListItems.map { it.isNotEmpty() }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    override val areHandOutInputsValid = combine(member, receivingDate, checkedListItems)
    { member, receivingDate, checkedTerritories ->
        member.errorId == null && receivingDate.errorId == null && checkedTerritories.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override val areAtWorkProcessInputsValid = combine(deliveryDate, checkedListItems)
    { deliveryDate, checkedTerritories ->
        deliveryDate.errorId == null && checkedTerritories.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun observeCheckedListItems() {
        if (LOG_MVI_LIST) {
            Timber.tag(TAG).d("observeCheckedListItems() called")
        }
        uiState()?.let { uiState ->
            _checkedListItems.value = uiState.filter { it.checked }
            if (LOG_MVI_LIST) {
                Timber.tag(TAG).d(
                    "checked %s territories; areHandOutInputsValid = %s",
                    _checkedListItems.value.size,
                    areHandOutInputsValid.value
                )
            }
        }
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoriesGridUiAction): Job? {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(TerritoriesListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is TerritoriesGridUiAction.Load -> loadTerritories(
                action.congregationId,
                action.territoryProcessType, action.territoryLocationType,
                action.locationId, action.isPrivateSector
            )

            is TerritoriesGridUiAction.EditTerritory -> submitSingleEvent(
                TerritoriesGridUiSingleEvent.OpenTerritoryScreen(
                    NavRoutes.Territory.routeForTerritory(TerritoryInput(action.territoryId))
                )
            )

            is TerritoriesGridUiAction.DeleteTerritory -> deleteTerritory(action.territoryId)

            is TerritoriesGridUiAction.HandOutInitConfirmation -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_hand_out_subheader)
                null
            }

            is TerritoriesGridUiAction.HandOutConfirmation -> submitSingleEvent(
                TerritoriesGridUiSingleEvent.OpenHandOutConfirmationScreen(
                    NavRoutes.HandOutConfirmation.routeForHandOutConfirmation()
                )
            )

            is TerritoriesGridUiAction.HandOut -> handOutTerritories()

            is TerritoriesGridUiAction.ProcessInitConfirmation -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_at_work_process_subheader)
                null
            }

            is TerritoriesGridUiAction.ProcessConfirmation -> submitSingleEvent(
                TerritoriesGridUiSingleEvent.OpenProcessConfirmationScreen(
                    NavRoutes.ProcessConfirmation.routeForProcessConfirmation()
                )
            )

            is TerritoriesGridUiAction.Process -> processTerritories()

            is TerritoriesGridUiAction.ReportStreets -> submitSingleEvent(
                TerritoriesGridUiSingleEvent.OpenReportStreetsScreen(
                    NavRoutes.ReportStreets.routeForReportStreets(TerritoryInput(action.territoryId))
                )
            )

            is TerritoriesGridUiAction.ReportHouses -> submitSingleEvent(
                TerritoriesGridUiSingleEvent.OpenReportHousesScreen(
                    NavRoutes.ReportHouses.routeForReportHouses(TerritoryInput(action.territoryId))
                )
            )

            is TerritoriesGridUiAction.ReportRooms -> submitSingleEvent(
                TerritoriesGridUiSingleEvent.OpenReportRoomsScreen(
                    NavRoutes.ReportRooms.routeForReportRooms(TerritoryInput(action.territoryId))
                )
            )
        }
        return job
    }

    private fun loadTerritories(
        congregationId: UUID?,
        territoryProcessType: TerritoryProcessType, territoryLocationType: TerritoryLocationType,
        locationId: UUID? = null, isPrivateSector: Boolean = false
    ): Job {
        Timber.tag(TAG).d("loadTerritories(...) called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getProcessAndLocationTerritoriesUseCase.execute(
                GetProcessAndLocationTerritoriesUseCase.Request(
                    congregationId, territoryProcessType, territoryLocationType,
                    locationId, isPrivateSector
                )
            )
                .map {
                    listConverter.convert(it)
                }
                .collect {
                    submitState(it)
                    observeCheckedListItems()
                }
        }
        return job
    }

    private fun deleteTerritory(territoryId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteTerritory(...) called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryUseCase.execute(DeleteTerritoryUseCase.Request(territoryId))
                .collect {}
        }
        return job
    }

    private fun handOutTerritories(): Job {
        val memberId = member.value.item?.itemId
        val territoryIds = checkedListItems.value.map { it.id }
        val receivingDate = receivingDate.value.value.toFullFormatOffsetDateTime()
        Timber.tag(TAG)
            .d(
                "handOutTerritories() called: memberId = %s; territoryIds = %s; receivingDate = %s",
                member.value.item?.itemId,
                territoryIds,
                receivingDate
            )
        val job = viewModelScope.launch(errorHandler) {
            memberId?.let { memberId ->
                useCases.handOutTerritoriesUseCase.execute(
                    HandOutTerritoriesUseCase.Request(memberId, territoryIds, receivingDate)
                ).collect {

                }
            }
        }
        return job
    }

    private fun processTerritories(): Job {
        val territoryIds = checkedListItems.value.map { it.id }
        val deliveryDate = deliveryDate.value.value.toFullFormatOffsetDateTime()
        Timber.tag(TAG)
            .d(
                "processTerritories() called:  territoryIds = %s; deliveryDate = %s",
                territoryIds, deliveryDate
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.processTerritoriesUseCase.execute(
                ProcessTerritoriesUseCase.Request(territoryIds, deliveryDate)
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoriesFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: List<TerritoriesListItem>): Job? = null

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# observeInputEvents() called")
        }
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoriesInputEvent.Member -> setStateValue(
                        TerritoriesFields.TERRITORY_MEMBER, member, event.input,
                        TerritoriesInputValidator.Member.isValid(event.input.headline)
                    )

                    is TerritoriesInputEvent.ReceivingDate -> setStateValue(
                        TerritoriesFields.TERRITORY_RECEIVING_DATE, receivingDate, event.input,
                        TerritoriesInputValidator.ReceivingDate.isValid(event.input)
                    )

                    is TerritoriesInputEvent.DeliveryDate -> setStateValue(
                        TerritoriesFields.TERRITORY_DELIVERY_DATE, deliveryDate, event.input,
                        TerritoriesInputValidator.DeliveryDate.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoriesInputEvent.Member -> setStateValue(
                        TerritoriesFields.TERRITORY_MEMBER, member,
                        TerritoriesInputValidator.Member.errorIdOrNull(event.input.headline)
                    )

                    is TerritoriesInputEvent.ReceivingDate -> setStateValue(
                        TerritoriesFields.TERRITORY_RECEIVING_DATE, receivingDate,
                        TerritoriesInputValidator.Member.errorIdOrNull(event.input)
                    )

                    is TerritoriesInputEvent.DeliveryDate -> setStateValue(
                        TerritoriesFields.TERRITORY_DELIVERY_DATE, deliveryDate,
                        TerritoriesInputValidator.Member.errorIdOrNull(event.input)
                    )
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        }
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoriesInputValidator.Member.errorIdOrNull(member.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoriesFields.TERRITORY_MEMBER.name, errorId = it)
            )
        }
        TerritoriesInputValidator.ReceivingDate.errorIdOrNull(receivingDate.value.value)?.let {
            inputErrors.add(
                InputError(
                    fieldName = TerritoriesFields.TERRITORY_RECEIVING_DATE.name, errorId = it
                )
            )
        }
        TerritoriesInputValidator.DeliveryDate.errorIdOrNull(deliveryDate.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoriesFields.TERRITORY_DELIVERY_DATE.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG)
                .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        }
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritoriesFields.valueOf(error.fieldName)) {
                TerritoriesFields.TERRITORY_MEMBER -> member.value.copy(errorId = error.errorId)
                TerritoriesFields.TERRITORY_RECEIVING_DATE -> receivingDate.value.copy(errorId = error.errorId)
                TerritoriesFields.TERRITORY_DELIVERY_DATE -> deliveryDate.value.copy(errorId = error.errorId)
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoriesGridViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<TerritoriesGridUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val checkedListItems = MutableStateFlow(previewList(ctx))

                override val handOutSearchText = MutableStateFlow(TextFieldValue(""))
                override val atWorkSearchText = MutableStateFlow(TextFieldValue(""))
                override val idleSearchText = MutableStateFlow(TextFieldValue(""))
                override fun onHandOutSearchTextChange(text: TextFieldValue) {}
                override fun onAtWorkSearchTextChange(text: TextFieldValue) {}
                override fun onIdleSearchTextChange(text: TextFieldValue) {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val member = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val receivingDate = MutableStateFlow(InputWrapper())
                override val deliveryDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)
                override val areHandOutInputsValid = MutableStateFlow(true)
                override val areAtWorkProcessInputsValid = MutableStateFlow(true)

                override fun observeCheckedListItems() {}
                override fun submitAction(action: TerritoriesGridUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}

                override fun onTextFieldFocusChanged(
                    focusedField: TerritoriesFields, isFocused: Boolean
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

        fun previewList(ctx: Context) = listOf(
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory1_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory1_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory1_card_num)} ${
                    ctx.resources.getString(R.string.def_territory1_card_location)
                }",
                territoryNum = 1,
                isPrivateSector = false,
                isBusiness = false,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory1_desc),
                member = MemberUi(
                    group = GroupUi(),
                    memberNum = ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_num),
                    memberFullName = "${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_surname)} ${
                        ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_name)
                    } ${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_patronymic)} [${
                        ctx.resources.getString(
                            com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_pseudonym
                        )
                    }]",
                    memberShortName = "${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_surname)} ${
                        ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_name)[0]
                    }.${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_patronymic)[0]}. [${
                        ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_pseudonym)
                    }]",
                    pseudonym = ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_pseudonym),
                    dateOfBirth = "1981-08-01T14:29:10.212+03:00".toOffsetDateTime()
                )
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory2_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory2_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory2_card_num)} ${
                    ctx.resources.getString(R.string.def_territory2_card_location)
                }",
                territoryNum = 2,
                isPrivateSector = false,
                isBusiness = false,
                isGroupMinistry = true,
                isInPerimeter = true,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory2_desc)
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory3_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory3_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory3_card_num)} ${
                    ctx.resources.getString(R.string.def_territory3_card_location)
                }",
                territoryNum = 3,
                isPrivateSector = false,
                isBusiness = true,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory3_desc)
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory4_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory4_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory4_card_num)} ${
                    ctx.resources.getString(R.string.def_territory4_card_location)
                }",
                territoryNum = 4,
                isPrivateSector = true,
                isBusiness = true,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory4_desc)
            )
        )
    }
}