package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetsListView"

@Composable
fun TerritoryStreetsListView(
    viewModel: TerritoryStreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryInput: TerritoryInput,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "TerritoryStreetsListView(...) called: territoryInput = %s", territoryInput
    )
    LaunchedEffect(territoryInput.territoryId) {
        Timber.tag(TAG)
            .d("TerritoryStreetsListView -> LaunchedEffect()")
        viewModel.submitAction(TerritoryStreetsListUiAction.Load(territoryInput.territoryId))
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
                        dlgConfirmDelResId = R.string.dlg_confirm_del_territory_street,
                        emptyListResId = R.string.territory_streets_list_empty_text,
                        onEdit = { territoryStreet ->
                            viewModel.submitAction(
                                TerritoryStreetsListUiAction.EditTerritoryStreet(
                                    territoryInput.territoryId, territoryStreet.itemId!!
                                )
                            )
                        },
                        onDelete = { territoryStreet ->
                            viewModel.submitAction(
                                TerritoryStreetsListUiAction.DeleteTerritoryStreet(territoryStreet.itemId!!)
                            )
                        }
                    ) { territoryStreet -> viewModel.singleSelectItem(territoryStreet) }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.territory_streets_list_empty_text
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryStreetsListView -> LaunchedEffect() -> collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryStreetsListUiSingleEvent.OpenTerritoryStreetScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryStreetsList() {
    JWSuiteTheme {
        Surface {
            /*TerritoryStreetsEditableList(
                territoryStreets = TerritoryStreetsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}