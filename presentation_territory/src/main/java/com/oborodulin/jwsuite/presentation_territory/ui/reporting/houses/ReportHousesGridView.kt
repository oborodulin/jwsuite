package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportHousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.list.MemberReportsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.list.MemberReportsListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.util.Constants
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Reporting.ReportHousesGridView"

@Composable
fun ReportHousesGridView(
    reportHousesViewModel: ReportHousesViewModelImpl = hiltViewModel(),
    memberReportsListViewModel: MemberReportsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryStreetInput: TerritoryStreetInput
) {
    Timber.tag(TAG).d(
        "ReportHousesGridView(...) called: territoryStreetInput = %s", territoryStreetInput
    )
    val appState = LocalAppState.current
    LaunchedEffect(territoryStreetInput.territoryId, territoryStreetInput.territoryStreetId) {
        Timber.tag(TAG)
            .d("ReportHousesGridView -> LaunchedEffect()")
        reportHousesViewModel.submitAction(
            ReportHousesUiAction.Load(
                territoryId = territoryStreetInput.territoryId,
                territoryStreetId = territoryStreetInput.territoryStreetId
            )
        )
    }
    val searchText by reportHousesViewModel.searchText.collectAsStateWithLifecycle()
    reportHousesViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            TerritoryReportHousesGrid(
                reportHouses = it,
                searchedText = searchText.text,
                onAddMemberReport = { reportHouse ->
                    appState.mainNavigate(
                        NavRoutes.MemberReport.routeForMemberReport(
                            NavigationInput.MemberReportInput(houseId = reportHouse.id)
                        )
                    )
                },
                onEditMemberReport = { reportHouse ->
                    reportHousesViewModel.submitAction(
                        ReportHousesUiAction.EditMemberReport(reportHouse.territoryMemberReportId!!)
                    )
                },
                onDeleteMemberReport = { reportHouse ->
                    reportHousesViewModel.submitAction(
                        ReportHousesUiAction.DeleteMemberReport(reportHouse.territoryMemberReportId!!)
                    )
                },
                onProcessHouse = { reportHouse ->
                    reportHousesViewModel.submitAction(
                        ReportHousesUiAction.ProcessReport(reportHouse.territoryMemberReportId!!)
                    )
                },
                onCancelProcessHouse = { reportHouse ->
                    reportHousesViewModel.submitAction(
                        ReportHousesUiAction.CancelProcessReport(reportHouse.territoryMemberReportId!!)
                    )
                }
            ) { reportHouse ->
                with(memberReportsListViewModel) {
                    submitAction(MemberReportsListUiAction.Load(houseId = reportHouse.id))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("ReportHousesGridView -> LaunchedEffect() -> collect single Event Flow")
        reportHousesViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is ReportHousesUiSingleEvent.OpenMemberReportScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoryReportHousesGrid(
    reportHouses: List<TerritoryReportHousesListItem>,
    searchedText: String = "",
    onAddMemberReport: (TerritoryReportHousesListItem) -> Unit = {},
    onEditMemberReport: (TerritoryReportHousesListItem) -> Unit = {},
    onDeleteMemberReport: (TerritoryReportHousesListItem) -> Unit = {},
    onProcessHouse: (TerritoryReportHousesListItem) -> Unit = {},
    onCancelProcessHouse: (TerritoryReportHousesListItem) -> Unit = {},
    onClick: (TerritoryReportHousesListItem) -> Unit = {}
) {
    Timber.tag(TAG).d("TerritoryReportHousesGrid(...) called")
    if (reportHouses.isNotEmpty()) {
        val filteredItems = remember(reportHouses, searchedText) {
            if (searchedText.isEmpty()) {
                reportHouses
            } else {
                reportHouses.filter { it.doesMatchSearchQuery(searchedText) }
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
                ReportHouseGridItemComponent(
                    reportHouse = filteredItems[index],
                    onAddMemberReport = onAddMemberReport,
                    onEditMemberReport = onEditMemberReport,
                    onDeleteMemberReport = onDeleteMemberReport,
                    onProcessHouse = onProcessHouse,
                    onCancelProcessHouse = onCancelProcessHouse,
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
fun PreviewHousesEditableList() {
    JWSuiteTheme {
        Surface {
            /*StreetHousesList(
                houses = ReportHousesGridViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
