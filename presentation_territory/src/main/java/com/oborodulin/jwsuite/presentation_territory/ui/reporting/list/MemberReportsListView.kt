package com.oborodulin.jwsuite.presentation_territory.ui.reporting.list

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.components.list.ListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Reporting.MemberReportsListView"

@Composable
fun MemberReportsListView(
    viewModel: MemberReportsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryStreetId: UUID? = null,
    houseId: UUID? = null,
    roomId: UUID? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "MemberReportsListView(...) called: territoryStreetId = %s; houseId = %s; roomId = %s",
        territoryStreetId, houseId, roomId
    )
    LaunchedEffect(territoryStreetId, houseId, roomId) {
        Timber.tag(TAG)
            .d("MemberReportsListView -> LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            MemberReportsListUiAction.Load(
                territoryStreetId = territoryStreetId,
                houseId = houseId,
                roomId = roomId
            )
        )
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_territory_report,
                        emptyListResId = R.string.territory_reports_list_empty_text,
                        onEdit = { memberReport ->
                            viewModel.submitAction(
                                MemberReportsListUiAction.EditMemberReport(memberReport.itemId!!)
                            )
                        },
                        onDelete = { memberReport ->
                            viewModel.submitAction(
                                MemberReportsListUiAction.DeleteMemberReport(memberReport.itemId!!)
                            )
                        }
                    ) { memberReport -> viewModel.singleSelectItem(memberReport) }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.territory_reports_list_empty_text
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("MemberReportsListView -> LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MemberReportsListUiSingleEvent.OpenMemberReportScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMemberReportsList() {
    JWSuiteTheme {
        Surface {
            /*TerritoryStreetsEditableList(
                territoryStreets = MemberReportsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}