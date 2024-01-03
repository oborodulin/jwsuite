package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

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
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.ProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.TerritoryReportUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportHousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryReportHousesListConverter
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

private const val TAG = "Reporting.ReportHousesViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class ReportHousesViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryReportUseCases,
    private val converter: TerritoryReportHousesListConverter
) :
    ReportHousesViewModel,
    SingleViewModel<List<TerritoryReportHousesListItem>, UiState<List<TerritoryReportHousesListItem>>, ReportHousesUiAction, ReportHousesUiSingleEvent, ReportHousesFields, InputWrapper>(
        state
    ) {
    override val territoryStreet: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            ReportHousesFields.PARTIAL_HOUSES_TERRITORY_STREET.name, InputListItemWrapper()
        )
    }

    override fun singleSelectedItem(): ListItemModel? {
        if (LOG_MVI_LIST) Timber.tag(TAG).d("singleSelectedItem() called")
        var selectedItem: ListItemModel? = null
        uiState()?.let { uiState ->
            selectedItem = try {
                uiState.first { it.selected }
            } catch (e: NoSuchElementException) {
                uiState.getOrNull(0)
                //Timber.tag(TAG).e(e)
            }
            if (LOG_MVI_LIST) Timber.tag(TAG).d("selected %s list item", selectedItem)
        }
        return selectedItem
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: ReportHousesUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(ReportHousesUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is ReportHousesUiAction.Load -> loadReportHouses(
                action.territoryId, action.territoryStreetId
            )

            is ReportHousesUiAction.EditMemberReport -> submitSingleEvent(
                ReportHousesUiSingleEvent.OpenMemberReportScreen(
                    NavRoutes.MemberReport.routeForMemberReport(
                        NavigationInput.MemberReportInput(action.territoryMemberReportId)
                    )
                )
            )

            is ReportHousesUiAction.DeleteMemberReport -> deleteMemberReport(action.territoryMemberReportId)
            is ReportHousesUiAction.ProcessReport -> processMemberReport(action.territoryMemberReportId)
            is ReportHousesUiAction.CancelProcessReport -> cancelProcessMemberReport(action.territoryMemberReportId)
        }
        return job
    }

    private fun loadReportHouses(territoryId: UUID, territoryStreetId: UUID? = null): Job {
        Timber.tag(TAG)
            .d(
                "loadReportHouses(...) called: territoryId = %s; territoryStreetId = %s",
                territoryId, territoryStreetId
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getReportHousesUseCase.execute(
                GetReportHousesUseCase.Request(territoryId, territoryStreetId)
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

    override fun stateInputFields() = enumValues<ReportHousesFields>().map { it.name }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is ReportHousesInputEvent.Street -> setStateValue(
                        ReportHousesFields.PARTIAL_HOUSES_TERRITORY_STREET, territoryStreet,
                        event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is ReportHousesInputEvent.Street ->
                        setStateValue(
                            ReportHousesFields.PARTIAL_HOUSES_TERRITORY_STREET, territoryStreet,
                            null
                        )
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? = null
    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : ReportHousesViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<ReportHousesUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val territoryStreet =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())

                override fun singleSelectedItem() = null

                override fun submitAction(action: ReportHousesUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: ReportHousesFields, isFocused: Boolean
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
            TerritoryReportHousesListItem(
                id = UUID.randomUUID(),
                territoryMemberReportId = UUID.randomUUID(),
                houseNum = 1,
                houseFullNum = "1Б",
                streetFullName = "ул. Независимости",
                territoryMemberId = UUID.randomUUID(),
                territoryShortMark = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.territory_short_marks)[TerritoryReportMark.PP.ordinal],
                languageCode = null,
                genderInfo = ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.male_expr),
                ageInfo = "(45 ${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.age_expr)})",
                isProcessed = false
            ),
            TerritoryReportHousesListItem(
                id = UUID.randomUUID(),
                territoryMemberReportId = UUID.randomUUID(),
                houseNum = 145,
                houseFullNum = "145",
                streetFullName = "ул. Независимости",
                territoryMemberId = UUID.randomUUID(),
                territoryShortMark = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.territory_short_marks)[TerritoryReportMark.GO.ordinal],
                languageCode = null,
                genderInfo = ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.female_expr),
                ageInfo = "(54 ${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.age_expr)})",
                isProcessed = true
            )
        )
    }
}