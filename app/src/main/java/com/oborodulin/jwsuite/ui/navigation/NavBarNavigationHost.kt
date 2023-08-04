package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.home.common.util.rememberParentEntry
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.CongregatingScreen
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.DashboardingScreen
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.TerritoringScreen
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid.TerritoriesGridViewModelImpl
import timber.log.Timber

private const val TAG = "App.navigation.NavBarNavigationHost"

@Composable
fun NavBarNavigationHost(
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("NavBarNavigationHost(...) called")
    NavHost(
        appState.navBarNavController, startDestination = NavRoutes.Dashboarding.route
    ) {
        composable(NavRoutes.Dashboarding.route) {
            // dashboarding: TOTALS: Congregations: Groups, Members; Territories; Ministries: Territories, Members and etc.
            Timber.tag(TAG)
                .d("Navigation Graph: to DashboardingScreen [route = '%s']", it.destination.route)
            val parentEntry =
                remember(it) { appState.navBarNavController.getBackStackEntry(NavRoutes.Dashboarding.route) }
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            appState.sharedViewModel.value = sharedViewModel
            DashboardingScreen(
                appState = appState,
                sharedViewModel = sharedViewModel,
                nestedScrollConnection = nestedScrollConnection,
                bottomBar = bottomBar
            ) //setFabOnClick = setFabOnClick
        }
        composable(
            route = NavRoutes.Congregating.route, arguments = NavRoutes.Congregating.arguments
        ) {
            // congregating: [Congregation, Members]; [Groups (favorite congregation), Members]
            Timber.tag(TAG)
                .d("Navigation Graph: to CongregatingScreen [route = '%s']", it.destination.route)
            CongregatingScreen(
                appState = appState,
                nestedScrollConnection = nestedScrollConnection,
                bottomBar = bottomBar
            )
        }
        composable(
            route = NavRoutes.Territoring.route, arguments = NavRoutes.Territoring.arguments
        ) {
            // territoring: Territories Grid [hand_out, at_work, idle], Territory Details
            Timber.tag(TAG)
                .d("Navigation Graph: to TerritoringScreen [route = '%s']", it.destination.route)
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            TerritoringScreen(
                appState = appState,
                territoriesGridViewModel = territoriesGridViewModel,
                nestedScrollConnection = nestedScrollConnection,
                bottomBar = bottomBar
            )
        }
        composable(route = NavRoutes.Ministring.route, arguments = NavRoutes.Ministring.arguments) {
            // ministring:
            Timber.tag(TAG)
                .d("Navigation Graph: to MinistringScreen [route = '%s']", it.destination.route)
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            //MinistringScreen(navController)
        }
    }
}
