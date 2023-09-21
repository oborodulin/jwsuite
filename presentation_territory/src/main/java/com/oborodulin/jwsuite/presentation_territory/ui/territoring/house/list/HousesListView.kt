package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.HousesListView"

@Composable
fun HousesListView(
    viewModel: HousesListViewModelImpl = hiltViewModel(),
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
            .d("HousesListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            HousesListUiAction.Load(streetInput?.streetId, territoryInput?.territoryId)
        )
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (territoryInput?.territoryId) {
                null -> StreetHousesList(
                    houses = it,
                    viewModel = viewModel,
                    onEdit = { house ->
                        viewModel.submitAction(HousesListUiAction.EditHouse(house.id))
                    },
                    onDelete = { house ->
                        viewModel.submitAction(HousesListUiAction.DeleteHouse(house.id))
                    }
                ) {}

                else -> TerritoryHousesList(
                    houses = it,
                    viewModel = viewModel,
                    onProcess = { house ->
                        viewModel.submitAction(HousesListUiAction.EditHouse(house.id))
                    },
                    onDelete = { house ->
                        viewModel.submitAction(HousesListUiAction.DeleteTerritoryHouse(house.id))
                    }
                ) {}
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HousesListView: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
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
    viewModel: HousesListViewModel,
    onEdit: (HousesListItem) -> Unit,
    onDelete: (HousesListItem) -> Unit,
    onClick: (HousesListItem) -> Unit
) {
    Timber.tag(TAG).d("StreetHousesList(...) called")
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
                    onClick = {
                        viewModel.singleSelectItem(house)
                        onClick(house)
                    }
                )
            }
        }
    } else {
        Text(
            text = stringResource(R.string.houses_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TerritoryHousesList(
    houses: List<HousesListItem>,
    viewModel: HousesListViewModel,
    onProcess: (HousesListItem) -> Unit,
    onDelete: (HousesListItem) -> Unit,
    onClick: (HousesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoryHousesList(...) called")
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
                    onClick = {
                        viewModel.singleSelectItem(house)
                        onClick(house)
                    }
                )
            }
        }
    } else {
        Text(
            text = stringResource(R.string.territory_houses_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
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
                viewModel = HousesListViewModelImpl.previewModel(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
