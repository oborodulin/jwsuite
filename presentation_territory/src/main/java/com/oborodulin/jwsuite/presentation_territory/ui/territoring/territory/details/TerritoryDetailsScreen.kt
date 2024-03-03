package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.fab.ExtFabComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.domain.types.TerritoryCategoryType
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list.HousesListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListView
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoryDetailsScreen"

@Composable
fun TerritoryDetailsScreen(
    territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
    territoryInput: TerritoryInput,
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("TerritoryDetailsScreen(...) called: territoryInput = %s", territoryInput)
    val appState = LocalAppState.current
    LaunchedEffect(territoryInput.territoryId) {
        Timber.tag(TAG).d("TerritoryDetailsScreen -> LaunchedEffect()")
        territoryViewModel.submitAction(TerritoryUiAction.Load(territoryInput.territoryId))
    }
    //val detailsTabType
    val tabType by viewModel.detailsTabType.collectAsStateWithLifecycle() //by rememberSaveable { mutableStateOf(detailsTabType.name) }
    val onTabChange: (TerritoryDetailsTabType) -> Unit =
        { viewModel.setDetailsTabType(it) } // tabType = it.name;
    val handleActionAdd: () -> Unit = {
        viewModel.submitAction(
            when (tabType) { // TerritoryDetailsTabType.valueOf(tabType)
                TerritoryDetailsTabType.STREETS ->
                    TerritoryDetailsUiAction.EditTerritoryStreet(territoryInput.territoryId)

                TerritoryDetailsTabType.HOUSES ->
                    TerritoryDetailsUiAction.EditTerritoryHouse(territoryInput.territoryId)

                TerritoryDetailsTabType.ENTRANCES ->
                    TerritoryDetailsUiAction.EditTerritoryEntrance(territoryInput.territoryId)

                TerritoryDetailsTabType.FLOORS ->
                    TerritoryDetailsUiAction.EditTerritoryFloor(territoryInput.territoryId)

                TerritoryDetailsTabType.ROOMS ->
                    TerritoryDetailsUiAction.EditTerritoryRoom(territoryInput.territoryId)
            }
        )
    }
    val tabStreets = TabRowItem(
        title = stringResource(R.string.territory_details_tab_streets),
        onClick = { onTabChange(TerritoryDetailsTabType.STREETS) }
    ) {
        TerritoryStreetsListView(
            navController = appState.mainNavController,
            territoryInput = territoryInput
        )
    }
    val tabHouses = TabRowItem(
        title = stringResource(R.string.territory_details_tab_houses),
        onClick = { onTabChange(TerritoryDetailsTabType.HOUSES) }
    ) {
        HousesListView(
            navController = appState.mainNavController,
            territoryInput = territoryInput
        )
    }
    val tabEntraces = TabRowItem(
        title = stringResource(R.string.territory_details_tab_entrances),
        onClick = { onTabChange(TerritoryDetailsTabType.ENTRANCES) }
    ) {}
    val tabFloors = TabRowItem(
        title = stringResource(R.string.territory_details_tab_floors),
        onClick = { onTabChange(TerritoryDetailsTabType.FLOORS) }
    ) {}
    val tabRooms = TabRowItem(
        title = stringResource(R.string.territory_details_tab_rooms),
        onClick = { onTabChange(TerritoryDetailsTabType.ROOMS) }
    ) {}
    var tabs by remember { mutableStateOf(emptyList<TabRowItem>()) }

    territoryViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        CommonScreen(state = state) { territory ->
            when (territory.territoryCategory.territoryCategoryCode) {
                TerritoryCategoryType.ROOMS -> onTabChange(TerritoryDetailsTabType.ROOMS)
                TerritoryCategoryType.FLOORS -> onTabChange(TerritoryDetailsTabType.FLOORS)
                else -> onTabChange(TerritoryDetailsTabType.STREETS)
            }
            ScaffoldComponent(
                topBarSubtitle = stringResource(
                    com.oborodulin.jwsuite.presentation.R.string.nav_item_territory_details,
                    territory.cardNum
                ),
                defTopBarActions = defTopBarActions,
                topBarActions = {
                    IconButton(onClick = { appState.backToBottomBarScreen(NavRoutes.Territoring.route) }) {
                        Icon(Icons.Outlined.Done, null)
                    }
                },
                floatingActionButton = {
                    ExtFabComponent(
                        enabled = true,
                        imageVector = Icons.Outlined.Add,
                        textResId = com.oborodulin.home.common.R.string.btn_add_lbl,
                        onClick = handleActionAdd
                    )
                }
            ) { innerPadding ->
                tabs = when (territory.territoryCategory.territoryCategoryCode) {
                    TerritoryCategoryType.HOUSES -> listOf(tabStreets, tabHouses, tabEntraces)
                    TerritoryCategoryType.FLOORS -> listOf(tabFloors)
                    TerritoryCategoryType.ROOMS -> listOf(tabRooms)
                    else -> emptyList()
                }
                //onActionBarSubtitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_territory_details, territory.cardNum))
                val upNavigation = { appState.mainNavigateUp() }
                // Scaffold Hoisting:
                //onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
                appState.handleTopBarNavClick.value = upNavigation
                /*
            onTopBarActionsChange(true) {
                IconButton(onClick = { appState.mainNavigateUp() }) { //backToBottomBarScreen() }) {
                    Icon(Icons.Outlined.Done, null)
                }
            }
            onFabChange {
                ExtFabComponent(
                    enabled = true,
                    imageVector = Icons.Outlined.Add,
                    textResId = com.oborodulin.home.common.R.string.btn_add_lbl,
                    onClick = handleActionAdd
                )
            }
             */
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CustomScrollableTabRow(tabs)
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryDetailsScreen -> LaunchedEffect() -> collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryDetailsUiSingleEvent.OpenTerritoryStreetScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryHouseScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryEntranceScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryFloorScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoryDetailsUiSingleEvent.OpenTerritoryRoomScreen -> {
                    appState.mainNavigate(it.navRoute)
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
