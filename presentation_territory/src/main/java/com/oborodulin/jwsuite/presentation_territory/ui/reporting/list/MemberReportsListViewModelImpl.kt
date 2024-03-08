package com.oborodulin.jwsuite.presentation_territory.ui.reporting.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import com.oborodulin.jwsuite.domain.usecases.territory.report.DeleteMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.TerritoryReportUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryMemberReportsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Reporting.TerritoryStreetsListViewModelImpl"

@HiltViewModel
class MemberReportsListViewModelImpl @Inject constructor(
    private val useCases: TerritoryReportUseCases,
    private val converter: TerritoryMemberReportsListConverter
) : MemberReportsListViewModel,
    ListViewModel<List<TerritoryMemberReportsListItem>, UiState<List<TerritoryMemberReportsListItem>>, MemberReportsListUiAction, MemberReportsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MemberReportsListUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(MemberReportsListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is MemberReportsListUiAction.Load -> loadMemberReports(
                territoryStreetId = action.territoryStreetId,
                houseId = action.houseId, roomId = action.roomId
            )

            is MemberReportsListUiAction.EditMemberReport -> {
                submitSingleEvent(
                    MemberReportsListUiSingleEvent.OpenMemberReportScreen(
                        NavRoutes.MemberReport.routeForMemberReport(
                            NavigationInput.MemberReportInput(territoryMemberReportId = action.territoryMemberReportId)
                        )
                    )
                )
            }

            is MemberReportsListUiAction.DeleteMemberReport -> deleteMemberReport(
                action.territoryMemberReportId
            )
        }
        return job
    }

    private fun loadMemberReports(
        territoryStreetId: UUID? = null, houseId: UUID? = null, roomId: UUID? = null
    ): Job {
        Timber.tag(TAG).d(
            "loadMemberReports() called: territoryStreetId = %s, houseId = %s, roomId = %s",
            territoryStreetId, houseId, roomId
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMemberReportsUseCase.execute(
                GetMemberReportsUseCase.Request(
                    territoryStreetId = territoryStreetId,
                    houseId = houseId,
                    roomId = roomId
                )
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteMemberReport(territoryMemberReportId: UUID): Job {
        Timber.tag(TAG)
            .d(
                "deleteMemberReport(...) called: territoryMemberReportId = %s",
                territoryMemberReportId
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteMemberReportUseCase.execute(
                DeleteMemberReportUseCase.Request(territoryMemberReportId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<TerritoryMemberReportsListItem>): Job? =
        null

    companion object {
        fun previewModel(ctx: Context) =
            object : MemberReportsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<MemberReportsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = null

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: MemberReportsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryMemberReportsListItem(
                id = UUID.randomUUID(),
                memberShortName = "",
                territoryShortMark = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.territory_short_marks)[TerritoryReportMark.PP.ordinal],
                languageInfo = null,
                personInfo = "${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.male_expr)} (45 ${
                    ctx.resources?.getString(
                        com.oborodulin.jwsuite.domain.R.string.age_expr
                    )
                })"
            ),
            TerritoryMemberReportsListItem(
                id = UUID.randomUUID(),
                memberShortName = "",
                territoryShortMark = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.territory_short_marks)[TerritoryReportMark.GO.ordinal],
                languageInfo = null,
                personInfo = "${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.female_expr)} (54 ${
                    ctx.resources?.getString(
                        com.oborodulin.jwsuite.domain.R.string.age_expr
                    )
                })"
            )
        )
    }
}