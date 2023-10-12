package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
@Composable
fun MainNavigationGraph(
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    NavHost(
        navController = appState.commonNavController,
        route = Graph.MAIN,
        startDestination = NavRoutes.Home.route
    ) {
        congregationNavGraph(appState)
        territoryNavGraph(appState)
        geoNavGraph(appState)
        housingNavGraph(appState)
        composable(NavRoutes.Home.route) {
            bottomBarNavGraph(appState, nestedScrollConnection, bottomBar)
        }
    }
}

