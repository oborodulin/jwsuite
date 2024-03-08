package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_LIST
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import com.oborodulin.jwsuite.domain.usecases.territory.report.CancelProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.DeleteMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.ProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.TerritoryReportUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportRoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryReportRoomsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Reporting.ReportRoomsViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class ReportRoomsViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryReportUseCases,
    private val converter: TerritoryReportRoomsListConverter
) :
    ReportRoomsViewModel,
    SingleViewModel<List<TerritoryReportRoomsListItem>, UiState<List<TerritoryReportRoomsListItem>>, ReportRoomsUiAction, ReportRoomsUiSingleEvent, ReportRoomsFields, InputWrapper>(
        state
    ) {
    override val house: StateFlow<InputListItemWrapper<HousesListItem>> by lazy {
        state.getStateFlow(ReportRoomsFields.REPORT_ROOMS_HOUSE.name, InputListItemWrapper())
    }

    override fun singleSelectedItem(): ListItemModel? {
        if (LOG_MVI_LIST) {
            Timber.tag(TAG).d("singleSelectedItem() called")
        }
        var selectedItem: ListItemModel? = null
        uiState()?.let { uiState ->
            selectedItem = try {
                uiState.first { it.selected }
            } catch (e: NoSuchElementException) {
                uiState.getOrNull(0)
                //Timber.tag(TAG).e(e)
            }
            if (LOG_MVI_LIST) {
                Timber.tag(TAG).d("selected %s list item", selectedItem)
            }
        }
        return selectedItem
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: ReportRoomsUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(ReportHousesUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is ReportRoomsUiAction.Load -> loadReportRooms(action.territoryId, action.houseId)

            is ReportRoomsUiAction.EditMemberReport -> submitSingleEvent(
                ReportRoomsUiSingleEvent.OpenMemberReportScreen(
                    NavRoutes.MemberReport.routeForMemberReport(
                        NavigationInput.MemberReportInput(territoryMemberReportId = action.territoryMemberReportId)
                    )
                )
            )

            is ReportRoomsUiAction.DeleteMemberReport -> deleteMemberReport(action.territoryMemberReportId)
            is ReportRoomsUiAction.ProcessReport -> processMemberReport(action.territoryMemberReportId)
            is ReportRoomsUiAction.CancelProcessReport -> cancelProcessMemberReport(action.territoryMemberReportId)
        }
        return job
    }

    private fun loadReportRooms(territoryId: UUID, houseId: UUID? = null): Job {
        Timber.tag(TAG)
            .d(
                "loadReportRooms(...) called: territoryId = %s; houseId = %s",
                territoryId, houseId
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getReportRoomsUseCase.execute(
                GetReportRoomsUseCase.Request(territoryId, houseId)
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteMemberReport(memberReportId: UUID): Job {
        Timber.tag(TAG).d("deleteMemberReport(...) called: memberReportId = %s", memberReportId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteMemberReportUseCase.execute(
                DeleteMemberReportUseCase.Request(memberReportId)
            ).collect {}
        }
        return job
    }

    private fun processMemberReport(memberReportId: UUID): Job {
        Timber.tag(TAG).d("processMemberReport(...) called: memberReportId = %s", memberReportId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.processMemberReportUseCase.execute(
                ProcessMemberReportUseCase.Request(memberReportId)
            ).collect {}
        }
        return job
    }

    private fun cancelProcessMemberReport(memberReportId: UUID): Job {
        Timber.tag(TAG)
            .d("cancelProcessMemberReport(...) called: memberReportId = %s", memberReportId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.cancelProcessMemberReportUseCase.execute(
                CancelProcessMemberReportUseCase.Request(memberReportId)
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<ReportRoomsFields>().map { it.name }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is ReportRoomsInputEvent.House -> setStateValue(
                        ReportRoomsFields.REPORT_ROOMS_HOUSE, house, event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is ReportRoomsInputEvent.House ->
                        setStateValue(ReportRoomsFields.REPORT_ROOMS_HOUSE, house, null)
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? = null
    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : ReportRoomsViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<ReportRoomsUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val house = MutableStateFlow(InputListItemWrapper<HousesListItem>())

                override fun singleSelectedItem() = null

                override fun submitAction(action: ReportRoomsUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: ReportRoomsFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryReportRoomsListItem(
                id = UUID.randomUUID(),
                territoryMemberReportId = UUID.randomUUID(),
                roomNum = 1,
                houseFullNum = "1Б",
                streetFullName = "ул. Независимости",
                territoryMemberId = UUID.randomUUID(),
                territoryShortMark = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.territory_short_marks)[TerritoryReportMark.PP.ordinal],
                languageInfo = null,
                personInfo = "${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.male_expr)} (45 ${
                    ctx.resources?.getString(
                        com.oborodulin.jwsuite.domain.R.string.age_expr
                    )
                })",
                isProcessed = false
            ),
            TerritoryReportRoomsListItem(
                id = UUID.randomUUID(),
                territoryMemberReportId = UUID.randomUUID(),
                roomNum = 1,
                houseFullNum = "145",
                streetFullName = "ул. Независимости",
                territoryMemberId = UUID.randomUUID(),
                territoryShortMark = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.territory_short_marks)[TerritoryReportMark.GO.ordinal],
                languageInfo = null,
                personInfo = "${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.female_expr)} (54 ${
                    ctx.resources?.getString(
                        com.oborodulin.jwsuite.domain.R.string.age_expr
                    )
                })",
                isProcessed = true
            )
        )
    }
}