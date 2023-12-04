package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.home.common.util.rememberParentEntry
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.CongregatingScreen
import com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.DashboardingScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork.ProcessConfirmationScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout.HandOutConfirmationScreen
import timber.log.Timber

private const val TAG = "App.navigation.BarNavigationHost"

@Composable
fun BarNavigationHost(
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    val appState = LocalAppState.current
    val session = LocalSession.current
    Timber.tag(TAG)
        .d("BarNavigationHost(...) called: session.startDestination = %s", session.startDestination)
    NavHost(
        navController = appState.barNavController,
        route = Graph.BOTTOM_BAR,
        startDestination = session.startDestination //NavRoutes.Dashboarding.route,
        //modifier = Modifier.padding(innerPadding)
    ) {
        // DashboardingScreen:
        composable(NavRoutes.Dashboarding.route) {
            // dashboarding: TOTALS: Congregations: Groups, Members; Territories; Ministries: Territories, Members and etc.
            Timber.tag(TAG)
                .d("Navigation Graph: to DashboardingScreen [route = '%s']", it.destination.route)
            // https://stackoverflow.com/questions/68857820/how-to-share-a-viewmodel-between-two-or-more-jetpack-composables-inside-a-compos
            // https://proandroiddev.com/jetpack-navigation-component-manual-implementation-of-multiple-back-stacks-62b33e95795c
            val parentEntry =
                remember(it) { appState.barNavController.getBackStackEntry(NavRoutes.Dashboarding.route) }
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            Timber.tag(TAG).d("Navigation Graph: get sharedViewModel")
            if (appState.sharedViewModel.value == null) {
                appState.sharedViewModel.value = sharedViewModel
            }
            Timber.tag(TAG).d("Navigation Graph: sharedViewModel saved in appState")
            DashboardingScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // CongregatingScreen:
        composable(
            route = NavRoutes.Congregating.route, arguments = NavRoutes.Congregating.arguments
        ) {
            // congregating: [Congregation, Members]; [Groups (favorite congregation), Members]
            Timber.tag(TAG)
                .d("Navigation Graph: to CongregatingScreen [route = '%s']", it.destination.route)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            CongregatingScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // TerritoringScreen:
        composable(
            route = NavRoutes.Territoring.route, arguments = NavRoutes.Territoring.arguments
        ) {
            // territoring: Territories Grid [hand_out, at_work, idle], Territory Details
            Timber.tag(TAG)
                .d("Navigation Graph: to TerritoringScreen [route = '%s']", it.destination.route)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            TerritoringScreen(
                //sharedViewModel = sharedViewModel,
                territoriesGridViewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(route = NavRoutes.HandOutConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HandOutConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            HandOutConfirmationScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(route = NavRoutes.ProcessConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to ProcessConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            ProcessConfirmationScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // MinistringScreen:
        composable(route = NavRoutes.Ministring.route, arguments = NavRoutes.Ministring.arguments) {
            // ministring:
            Timber.tag(TAG)
                .d("Navigation Graph: to MinistringScreen [route = '%s']", it.destination.route)
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            //MinistringScreen(appState = appState, paddingValues = paddingValues,
            //                onActionBarTitleChange = onActionBarTitleChange,
            //                onTopBarActionsChange = onTopBarActionsChange)
        }
    }
}
