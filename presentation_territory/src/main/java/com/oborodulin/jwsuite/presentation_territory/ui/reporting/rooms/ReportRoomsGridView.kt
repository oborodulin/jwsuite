package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.components.list.EmptyListTextComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportRoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.list.MemberReportsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.list.MemberReportsListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.util.Constants
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Reporting.ReportRoomsGridView"

@Composable
fun ReportRoomsGridView(
    reportRoomsViewModel: ReportRoomsViewModelImpl = hiltViewModel(),
    memberReportsListViewModel: MemberReportsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryHouseInput: NavigationInput.TerritoryHouseInput
) {
    Timber.tag(TAG).d(
        "ReportRoomsGridView(...) called: territoryHouseInput = %s", territoryHouseInput
    )
    val appState = LocalAppState.current
    LaunchedEffect(territoryHouseInput.territoryId, territoryHouseInput.houseId) {
        Timber.tag(TAG)
            .d("ReportRoomsGridView -> LaunchedEffect() BEFORE collect ui state flow")
        reportRoomsViewModel.submitAction(
            ReportRoomsUiAction.Load(
                territoryId = territoryHouseInput.territoryId,
                houseId = territoryHouseInput.houseId
            )
        )
    }
    val searchText by reportRoomsViewModel.searchText.collectAsStateWithLifecycle()
    reportRoomsViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            TerritoryReportRoomsGrid(
                reportRooms = it,
                searchedText = searchText.text,
                onAddMemberReport = { reportRoom ->
                    appState.mainNavigate(
                        NavRoutes.MemberReport.routeForMemberReport(
                            NavigationInput.MemberReportInput(roomId = reportRoom.id)
                        )
                    )
                },
                onEditMemberReport = { reportRoom ->
                    reportRoomsViewModel.submitAction(
                        ReportRoomsUiAction.EditMemberReport(reportRoom.territoryMemberReportId!!)
                    )
                },
                onDeleteMemberReport = { reportRoom ->
                    reportRoomsViewModel.submitAction(
                        ReportRoomsUiAction.DeleteMemberReport(reportRoom.territoryMemberReportId!!)
                    )
                },
                onProcessRoom = { reportRoom ->
                    reportRoomsViewModel.submitAction(
                        ReportRoomsUiAction.ProcessReport(reportRoom.territoryMemberReportId!!)
                    )
                },
                onCancelProcessRoom = { reportRoom ->
                    reportRoomsViewModel.submitAction(
                        ReportRoomsUiAction.CancelProcessReport(reportRoom.territoryMemberReportId!!)
                    )
                }
            ) { reportRoom ->
                with(memberReportsListViewModel) {
                    submitAction(MemberReportsListUiAction.Load(roomId = reportRoom.id))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("ReportRoomsGridView -> LaunchedEffect() AFTER collect single Event Flow")
        reportRoomsViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is ReportRoomsUiSingleEvent.OpenMemberReportScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoryReportRoomsGrid(
    reportRooms: List<TerritoryReportRoomsListItem>,
    searchedText: String = "",
    onAddMemberReport: (TerritoryReportRoomsListItem) -> Unit = {},
    onEditMemberReport: (TerritoryReportRoomsListItem) -> Unit = {},
    onDeleteMemberReport: (TerritoryReportRoomsListItem) -> Unit = {},
    onProcessRoom: (TerritoryReportRoomsListItem) -> Unit = {},
    onCancelProcessRoom: (TerritoryReportRoomsListItem) -> Unit = {},
    onClick: (TerritoryReportRoomsListItem) -> Unit = {}
) {
    Timber.tag(TAG).d("TerritoryReportRoomsGrid(...) called")
    if (reportRooms.isNotEmpty()) {
        val filteredItems = remember(reportRooms, searchedText) {
            if (searchedText.isEmpty()) {
                reportRooms
            } else {
                reportRooms.filter { it.doesMatchSearchQuery(searchedText) }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(Constants.CELL_SIZE),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(4.dp)
                .focusable(enabled = true),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(filteredItems.size) { index ->
                ReportRoomGridItemComponent(
                    reportRoom = filteredItems[index],
                    onAddMemberReport = onAddMemberReport,
                    onEditMemberReport = onEditMemberReport,
                    onDeleteMemberReport = onDeleteMemberReport,
                    onProcessRoom = onProcessRoom,
                    onCancelProcessRoom = onCancelProcessRoom,
                    onClick = onClick
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.territories_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRoomsEditableList() {
    JWSuiteTheme {
        Surface {
            /*StreetRoomsList(
                houses = ReportRoomsGridViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
