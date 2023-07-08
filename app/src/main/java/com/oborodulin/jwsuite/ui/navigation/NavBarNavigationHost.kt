package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.CongregatingScreen
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.DashboardingScreen
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
            DashboardingScreen(
                appState = appState,
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
            // territoring: []Meters; Meter values, Meter verifications
            Timber.tag(TAG)
                .d("Navigation Graph: to TerritoringScreen [route = '%s']", it.destination.route)
            //TerritoringScreen(navController)
        }
        composable(route = NavRoutes.Ministring.route, arguments = NavRoutes.Ministring.arguments) {
            // Receipts
            Timber.tag(TAG)
                .d("Navigation Graph: to MinistringScreen [route = '%s']", it.destination.route)
            //MinistringScreen(navController)
        }
    }
}
