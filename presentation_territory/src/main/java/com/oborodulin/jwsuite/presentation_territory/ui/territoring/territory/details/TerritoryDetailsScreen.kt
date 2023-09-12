package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.fab.FabComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.domain.util.TerritoryCategoryType
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListView
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoryDetailsScreen"

@Composable
fun TerritoryDetailsScreen(
    appState: AppState,
    territoryViewModel: TerritoryViewModel,
    viewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
    territoryInput: TerritoryInput
) {
    Timber.tag(TAG).d("TerritoryDetailsScreen(...) called: territoryInput = %s", territoryInput)
    LaunchedEffect(territoryInput.territoryId) {
        Timber.tag(TAG).d("TerritoryDetailsScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryViewModel.submitAction(TerritoryUiAction.Load(territoryInput.territoryId))
    }
    var tabType by rememberSaveable { mutableStateOf(TerritoryDetailsTabType.STREETS.name) }
    val addActionOnClick: () -> Unit = {
        when (TerritoryDetailsTabType.valueOf(tabType)) {
            TerritoryDetailsTabType.STREETS -> viewModel.submitAction(
                TerritoryDetailsUiAction.EditTerritoryStreet(territoryInput.territoryId)
            )

            TerritoryDetailsTabType.HOUSES -> viewModel.submitAction(
                TerritoryDetailsUiAction.EditTerritoryHouse(territoryInput.territoryId)
            )

            TerritoryDetailsTabType.ENTRANCES -> viewModel.submitAction(
                TerritoryDetailsUiAction.EditTerritoryEntrance(territoryInput.territoryId)
            )

            TerritoryDetailsTabType.FLOORS -> viewModel.submitAction(
                TerritoryDetailsUiAction.EditTerritoryFloor(territoryInput.territoryId)
            )

            TerritoryDetailsTabType.ROOMS -> viewModel.submitAction(
                TerritoryDetailsUiAction.EditTerritoryRoom(territoryInput.territoryId)
            )
        }
    }
    val tabStreets = TabRowItem(
        title = stringResource(R.string.territory_details_tab_streets),
        onClick = { tabType = TerritoryDetailsTabType.STREETS.name }
    ) {
        TerritoryStreetsListView(
            navController = appState.commonNavController,
            territoryInput = territoryInput
        )
    }
    val tabHouses = TabRowItem(
        title = stringResource(R.string.territory_details_tab_houses),
        onClick = { tabType = TerritoryDetailsTabType.HOUSES.name }
    ) {
        HousesListView(
            navController = appState.commonNavController,
            territoryInput = territoryInput
        )
    }
    val tabEntraces = TabRowItem(
        title = stringResource(R.string.territory_details_tab_entrances),
        onClick = { tabType = TerritoryDetailsTabType.ENTRANCES.name }
    ) {}
    val tabFloors = TabRowItem(
        title = stringResource(R.string.territory_details_tab_floors),
        onClick = { tabType = TerritoryDetailsTabType.FLOORS.name }
    ) {}
    val tabRooms = TabRowItem(
        title = stringResource(R.string.territory_details_tab_rooms),
        onClick = { tabType = TerritoryDetailsTabType.ROOMS.name }
    ) {}
    var tabs by remember { mutableStateOf(emptyList<TabRowItem>()) }

    territoryViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        CommonScreen(state = state) { territory ->
            tabs = when (territory.territoryCategory.territoryCategoryCode) {
                TerritoryCategoryType.HOUSES -> listOf(tabStreets, tabHouses, tabEntraces)
                TerritoryCategoryType.FLOORS -> listOf(tabFloors)
                TerritoryCategoryType.ROOMS -> listOf(tabRooms)
                else -> emptyList()
            }
            appState.actionBarSubtitle.value = stringResource(
                com.oborodulin.jwsuite.presentation.R.string.nav_item_territory_details,
                territory.cardNum
            )
            JWSuiteTheme { //(darkTheme = true)
                ScaffoldComponent(
                    appState = appState,
                    topBarNavigationIcon = {
                        IconButton(onClick = { appState.commonNavigateUp() }) {
                            Icon(Icons.Outlined.ArrowBack, null)
                        }
                    },
                    topBarActions = {
                        IconButton(onClick = { appState.backToBottomBarScreen() }) {
                            Icon(Icons.Outlined.Done, null)
                        }
                    },
                    floatingActionButton = {
                        FabComponent(
                            enabled = true,
                            imageVector = Icons.Outlined.Add,
                            textResId = com.oborodulin.home.common.R.string.btn_add_lbl,
                            onClick = addActionOnClick
                        )
                    }
                ) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        CustomScrollableTabRow(tabs)
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryDetailsScreen: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryDetailsUiSingleEvent.OpenTerritoryStreetScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryHouseScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryEntranceScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryFloorScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryRoomScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryDetailsScreen() {
    /*TerritoryDetailsScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
