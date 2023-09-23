package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list.RoomsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list.RoomsListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.HousesListView"

@Composable
fun HousesListView(
    housesListViewModel: HousesListViewModelImpl = hiltViewModel(),
    roomsListViewModel: RoomsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    streetInput: StreetInput? = null,
    territoryInput: TerritoryInput? = null,
    isForTerritory: Boolean = false
) {
    Timber.tag(TAG).d(
        "HousesListView(...) called: isForTerritory = %s; streetInput = %s; territoryInput = %s",
        isForTerritory, streetInput, territoryInput
    )
    LaunchedEffect(streetInput?.streetId, territoryInput?.territoryId, isForTerritory) {
        Timber.tag(TAG)
            .d("HousesListView: LaunchedEffect() BEFORE collect ui state flow")
        if (isForTerritory && territoryInput != null) {
            housesListViewModel.submitAction(
                HousesListUiAction.LoadForTerritory(territoryInput.territoryId)
            )
        } else {
            housesListViewModel.submitAction(
                HousesListUiAction.Load(streetInput?.streetId, territoryInput?.territoryId)
            )
        }
    }
    housesListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            if (isForTerritory && territoryInput != null) {
                ForTerritoryHousesList(
                    houses = it,
                    onChecked = { housesListViewModel.observeCheckedListItems() }
                )
            } else {
                when (territoryInput?.territoryId) {
                    null -> StreetHousesList(
                        houses = it,
                        onEdit = { house ->
                            housesListViewModel.submitAction(HousesListUiAction.EditHouse(house.id))
                        },
                        onDelete = { house ->
                            housesListViewModel.submitAction(HousesListUiAction.DeleteHouse(house.id))
                        }
                    ) { house ->
                        housesListViewModel.singleSelectItem(house)
                        roomsListViewModel.submitAction(RoomsListUiAction.Load(houseId = house.id))
                    }

                    else -> TerritoryHousesList(
                        houses = it,
                        onProcess = { house ->
                            housesListViewModel.submitAction(HousesListUiAction.EditHouse(house.id))
                        },
                        onDelete = { house ->
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
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HousesListView: LaunchedEffect() AFTER collect single Event Flow")
        housesListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is HousesListUiSingleEvent.OpenHouseScreen -> {
                    navController.navigate(it.navRoute)
                }

                is HousesListUiSingleEvent.OpenTerritoryHouseScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun StreetHousesList(
    houses: List<HousesListItem>,
    onEdit: (HousesListItem) -> Unit,
    onDelete: (HousesListItem) -> Unit,
    onClick: (HousesListItem) -> Unit
) {
    Timber.tag(TAG).d("StreetHousesList(...) called: size = %d", houses.size)
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
                        ComponentUiAction.EditListItem { onEdit(house) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(R.string.dlg_confirm_del_house, house.houseFullNum)
                        ) { onDelete(house) }),
                    selected = house.selected,
                    onClick = { onClick(house) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.houses_list_empty_text)
    }
}

@Composable
fun TerritoryHousesList(
    houses: List<HousesListItem>,
    onProcess: (HousesListItem) -> Unit,
    onDelete: (HousesListItem) -> Unit,
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
                        ) { onDelete(house) }),
                    selected = house.selected,
                    onClick = { onClick(house) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.territory_houses_list_empty_text)
    }
}

@Composable
fun ForTerritoryHousesList(
    searchedText: String = "",
    houses: List<HousesListItem>,
    onChecked: (Boolean) -> Unit,
    onClick: (HousesListItem) -> Unit = {}
) {
    Timber.tag(TAG).d("ForTerritoryHousesList(...) called: size = %d", houses.size)
    if (houses.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = houses.filter { it.selected }
                .getOrNull(0)?.let { houses.indexOf(it) } ?: 0)
        var filteredItems: List<HousesListItem>
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            filteredItems = if (searchedText.isEmpty()) {
                houses
            } else {
                houses.filter { it.doesMatchSearchQuery(searchedText) }
            }
            itemsIndexed(filteredItems, key = { _, item -> item.id }) { _, house ->
                ForTerritoryHousesListItemComponent(
                    item = house,
                    //selected = house.selected,
                    onChecked = onChecked,
                    onClick = { onClick(house) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.for_territory_houses_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHousesEditableList() {
    JWSuiteTheme {
        Surface {
            StreetHousesList(
                houses = HousesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
