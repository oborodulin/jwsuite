package com.oborodulin.jwsuite.presentation_territory.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.home.common.util.rememberParentEntry
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.TerritoryHouseScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.TerritoryRoomScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.TerritoryDetailsScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork.AtWorkTerritoriesConfirmationScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout.HandOutTerritoriesConfirmationScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.TerritoryStreetScreen
import timber.log.Timber

private const val TAG = "App.Navigation.territoryNavGraph"

fun NavGraphBuilder.territoryNavGraph(
    startDestination: String? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    navigation(
        route = Graph.TERRITORY,
        startDestination = startDestination ?: NavRoutes.HandOutTerritoriesConfirmation.route
    ) {
        composable(route = NavRoutes.HandOutTerritoriesConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HandOutTerritoriesConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(LocalAppState.current.barNavController))
            HandOutTerritoriesConfirmationScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.AtWorkTerritoriesConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to AtWorkTerritoriesConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(LocalAppState.current.barNavController))
            AtWorkTerritoriesConfirmationScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.Territory.route, arguments = NavRoutes.Territory.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Territory.arguments.firstOrNull()
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoryViewModel,
                territoryInput = NavRoutes.Territory.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(
            route = NavRoutes.TerritoryDetails.route,
            arguments = NavRoutes.TerritoryDetails.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryDetailsScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryDetails.arguments.firstOrNull()
                )
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryDetailsScreen(
                territoryViewModel = territoryViewModel,
                territoryInput = NavRoutes.TerritoryDetails.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(
            route = NavRoutes.TerritoryCategory.route,
            arguments = NavRoutes.TerritoryCategory.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryCategoryScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryCategory.arguments.firstOrNull()
                )
            TerritoryCategoryScreen(
                territoryCategoryInput = NavRoutes.TerritoryCategory.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }

        // Territory: Street, House, Entrace, Floor, Room:
        composable(
            route = NavRoutes.TerritoryStreet.route, arguments = NavRoutes.TerritoryStreet.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryStreetScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryStreet.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryStreetScreen(
                //sharedViewModel = sharedViewModel,
                territoryViewModel = territoryViewModel,
                territoryStreetInput = NavRoutes.TerritoryStreet.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(
            route = NavRoutes.TerritoryHouse.route, arguments = NavRoutes.TerritoryHouse.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryHouseScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryHouse.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryHouseScreen(
                territoryViewModel = territoryViewModel,
                territoryHouseInput = NavRoutes.TerritoryHouse.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }

        composable(
            route = NavRoutes.TerritoryRoom.route, arguments = NavRoutes.TerritoryRoom.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryRoomScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryRoom.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryRoomScreen(
                territoryViewModel = territoryViewModel,
                territoryRoomInput = NavRoutes.TerritoryRoom.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
    }
}
