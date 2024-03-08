package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list

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
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Housing.HousesListView"

@Composable
fun HousesListView(
    housesListViewModel: HousesListViewModelImpl = hiltViewModel(),
    roomsListViewModel: RoomsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    streetInput: StreetInput? = null,
    territoryInput: TerritoryInput? = null
) {
    Timber.tag(TAG).d(
        "HousesListView(...) called: streetInput = %s; territoryInput = %s",
        streetInput, territoryInput
    )
    LaunchedEffect(streetInput?.streetId, territoryInput?.territoryId) {
        Timber.tag(TAG)
            .d("HousesListView -> LaunchedEffect()")
        housesListViewModel.submitAction(
            HousesListUiAction.Load(
                streetId = streetInput?.streetId, territoryId = territoryInput?.territoryId
            )
        )
    }
    val searchText by housesListViewModel.searchText.collectAsStateWithLifecycle()
    housesListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            when (territoryInput?.territoryId) {
                null -> EditableListViewComponent(
                    items = it,
                    searchedText = searchText.text,
                    dlgConfirmDelResId = R.string.dlg_confirm_del_house,
                    emptyListResId = R.string.houses_list_empty_text,
                    onEdit = { house ->
                        housesListViewModel.submitAction(HousesListUiAction.EditHouse(house.itemId!!))
                    },
                    onDelete = { house ->
                        housesListViewModel.submitAction(HousesListUiAction.DeleteHouse(house.itemId!!))
                    }
                ) { house ->
                    housesListViewModel.singleSelectItem(house)
                    roomsListViewModel.submitAction(RoomsListUiAction.Load(houseId = house.itemId!!))
                }

                else -> EditableListViewComponent(
                    items = it,
                    searchedText = searchText.text,
                    dlgConfirmDelResId = R.string.dlg_confirm_del_territory_house,
                    emptyListResId = R.string.territory_houses_list_empty_text,
                    onDelete = { house ->
                        housesListViewModel.submitAction(
                            HousesListUiAction.DeleteTerritoryHouse(house.itemId!!)
                        )
                    }
                ) { house ->
                    housesListViewModel.singleSelectItem(house)
                }
            }
        }
        LaunchedEffect(Unit) {
            Timber.tag(TAG)
                .d("HousesListView -> LaunchedEffect() -> collect single Event Flow")
            housesListViewModel.singleEventFlow.collectLatest {
                Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
                when (it) {
                    is HousesListUiSingleEvent.OpenHouseScreen -> {
                        navController.navigate(it.navRoute)
                    }
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHousesEditableList() {
    JWSuiteTheme {
        Surface {
            /*StreetHousesList(
                houses = HousesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
