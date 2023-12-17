package com.oborodulin.jwsuite.presentation_territory.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation_territory.ui.housing.HousingScreen
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseScreen
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single.RoomScreen
import timber.log.Timber

private const val TAG = "App.Navigation.housingNavGraph"

fun NavGraphBuilder.housingNavGraph(
    startDestination: String? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    navigation(
        route = Graph.HOUSING, startDestination = startDestination ?: NavRoutes.Housing.route
    ) {
        // House, Entrance, Floor, Room:
       /*composable(route = NavRoutes.Housing.route, arguments = NavRoutes.Housing.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HousingScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Housing.arguments
                )
            HousingScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
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
                //territoryViewModel = territoryViewModel,
                houseInput = NavRoutes.House.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
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
                //territoryViewModel = territoryViewModel,
                roomInput = NavRoutes.Room.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }*/
    }
}
