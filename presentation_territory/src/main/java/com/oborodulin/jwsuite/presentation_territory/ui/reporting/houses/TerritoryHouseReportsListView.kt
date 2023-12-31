package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.components.list.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.list.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.TerritoryHouseReportsListView"

@Composable
fun TerritoryHouseReportsListView(
    housesListViewModel: TerritoryHouseReportsListViewModelImpl = hiltViewModel(),
    roomsListViewModel: RoomsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    streetInput: StreetInput? = null,
    territoryInput: TerritoryInput? = null,
    territoryStreetInput: TerritoryStreetInput? = null
) {
    Timber.tag(TAG).d(
        "TerritoryHouseReportsListView(...) called: streetInput = %s; territoryInput = %s; territoryStreetInput = %s",
        streetInput, territoryInput, territoryStreetInput
    )
    LaunchedEffect(
        streetInput?.streetId,
        territoryInput?.territoryId ?: territoryStreetInput?.territoryId,
        territoryStreetInput?.territoryStreetId
    ) {
        Timber.tag(TAG)
            .d("TerritoryHouseReportsListView -> LaunchedEffect() BEFORE collect ui state flow")
        housesListViewModel.submitAction(
            HousesListUiAction.Load(
                streetId = streetInput?.streetId,
                territoryId = territoryInput?.territoryId ?: territoryStreetInput?.territoryId,
                territoryStreetId = territoryStreetInput?.territoryStreetId
            )
        )
    }
    val searchText by housesListViewModel.searchText.collectAsStateWithLifecycle()
    housesListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (territoryStreetInput?.territoryId) {
                null -> when (territoryInput?.territoryId) {
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

                else -> PrecessedTerritoryHousesList(
                    houses = it,
                    onProcess = { house ->
                        housesListViewModel.submitAction(HousesListUiAction.EditHouse(house.id))
                    },
                    onReport = { house ->
                        housesListViewModel.submitAction(
                            HousesListUiAction.DeleteTerritoryHouse(house.id)
                        )
                    }
                ) { house ->
                    housesListViewModel.singleSelectItem(house)
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryHouseReportsListView -> LaunchedEffect() AFTER collect single Event Flow")
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

@Composable
fun PrecessedTerritoryHousesList(
    houses: List<HousesListItem>,
    onReport: (HousesListItem) -> Unit,
    onProcess: (HousesListItem) -> Unit,
    onClick: (HousesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoryHousesList(...) called: size = %d", houses.size)
    if (houses.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = houses.filter { it.selected }
                .getOrNull(0)?.let { houses.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(houses, key = { _, item -> item.id }) { _, house ->
                ListItemComponent(
                    item = house,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onProcess(house) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_territory_house,
                                house.houseFullNum
                            )
                        ) { onReport(house) }),
                    selected = house.selected,
                    onClick = { onClick(house) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.territory_houses_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHousesEditableList() {
    JWSuiteTheme {
        Surface {
            /*StreetHousesList(
                houses = TerritoryHouseReportsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
