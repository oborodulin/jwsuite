package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import timber.log.Timber

private const val TAG = "App.navigation.BarNavigationHost"

@Composable
fun BarNavigationHost(
    paddingValues: PaddingValues,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("BarNavigationHost(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    NavHost(
        navController = appState.barNavController,
        route = Graph.BOTTOM_BAR,
        startDestination = session.startDestination
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
                appState = appState,
                paddingValues = paddingValues,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarActionsChange = onTopBarActionsChange
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
                appState = appState,
                paddingValues = paddingValues,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarActionsChange = onTopBarActionsChange
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
                appState = appState,
                paddingValues = paddingValues,
                //sharedViewModel = sharedViewModel,
                territoriesGridViewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
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
