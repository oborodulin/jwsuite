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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.fab.FabComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.jwsuite.domain.util.TerritoryCategoryType
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListView
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoryDetailsScreen"

@Composable
fun TerritoryDetailsScreen(
    appState: AppState,
    territoryViewModel: TerritoryViewModel,
    territoryInput: TerritoryInput
) {
    Timber.tag(TAG).d("TerritoryDetailsScreen(...) called: territoryInput = %s", territoryInput)
    appState.actionBarSubtitle.value =
        stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_territory_details)
    val tabStreets = TabRowItem(
        title = stringResource(R.string.territory_details_tab_streets),
        view = {
            TerritoryStreetsListView(
                navController = appState.commonNavController,
                territoryInput = territoryInput
            )
        }
    )
    val tabHouses = TabRowItem(
        title = stringResource(R.string.territory_details_tab_houses),
        view = {}
    )
    val tabEntraces = TabRowItem(
        title = stringResource(R.string.territory_details_tab_entrances),
        view = {}
    )
    val tabFloors = TabRowItem(
        title = stringResource(R.string.territory_details_tab_floors),
        view = {}
    )
    val tabRooms = TabRowItem(
        title = stringResource(R.string.territory_details_tab_rooms),
        view = {}
    )
    var tabs by remember { mutableStateOf(emptyList<TabRowItem>()) }
    val category by territoryViewModel.category.collectAsStateWithLifecycle()
    tabs = when (category.item?.territoryCategoryCode) {
        TerritoryCategoryType.HOUSES -> listOf(tabStreets, tabHouses, tabEntraces)
        TerritoryCategoryType.FLOORS -> listOf(tabFloors)
        TerritoryCategoryType.ROOMS -> listOf(tabRooms)
        else -> emptyList()
    }
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
                    imageVector = Icons.Outlined.Add,
                    textResId = com.oborodulin.home.common.R.string.btn_add_lbl
                ) {
                    Timber.tag(TAG).d("FabComponent(...): FAB onClick...")
                    appState.commonNavController.navigate(
                        NavRoutes.TerritoryStreet.routeForTerritoryStreet(
                            NavigationInput.TerritoryStreetInput(
                                territoryId = territoryInput.territoryId
                            )
                        )
                    )
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                CustomScrollableTabRow(tabs)
            }
        }
    }
    /*        }
            LaunchedEffect(Unit) {
                Timber.tag(TAG).d("TerritoryDetailsScreen: LaunchedEffect() AFTER collect ui state flow")
                viewModel.singleEventFlow.collectLatest {
                    Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
                    when (it) {
                        is CongregatingUiSingleEvent.OpenPayerScreen -> {
                            appState.commonNavController.navigate(it.navRoute)
                        }
                    }
                }
            }

     */
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
