package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.ui.housing.HousingScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single.HouseScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single.RoomScreen
import timber.log.Timber

private const val TAG = "App.Navigation.housingNavGraph"

fun NavGraphBuilder.housingNavGraph(
    appState: AppState,
    paddingValues: PaddingValues,
    onChangeTopBarActions: (@Composable RowScope.() -> Unit) -> Unit
) {
    navigation(route = Graph.HOUSING, startDestination = NavRoutes.Housing.route) {
        // House, Entrance, Floor, Room:
        composable(route = NavRoutes.Housing.route, arguments = NavRoutes.Housing.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HousingScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Housing.arguments
                )
            HousingScreen(appState = appState)
        }
        composable(
            route = NavRoutes.House.route, arguments = NavRoutes.House.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HouseScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.House.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            //val territoryViewModel = hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(appState.commonNavController))
            HouseScreen(
                appState = appState,
                //territoryViewModel = territoryViewModel,
                houseInput = NavRoutes.House.fromEntry(it)
            )
        }
        composable(
            route = NavRoutes.Room.route, arguments = NavRoutes.Room.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to RoomScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Room.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            //val territoryViewModel = hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(appState.commonNavController))
            RoomScreen(
                appState = appState,
                //territoryViewModel = territoryViewModel,
                roomInput = NavRoutes.Room.fromEntry(it)
            )
        }
    }
}
