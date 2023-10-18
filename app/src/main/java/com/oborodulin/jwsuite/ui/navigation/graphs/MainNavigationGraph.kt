package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
@Composable
fun MainNavigationGraph(
    appState: AppState,
    paddingValues: PaddingValues,
    onChangeActionBar: (@Composable (() -> Unit)?) -> Unit,
    onChangeActionBarTitle: (String) -> Unit,
    onChangeActionBarSubtitle: (String) -> Unit,
    areUsingNestedScrollConnection: (Boolean) -> Unit,
    onChangeTopBarActions: (@Composable RowScope.() -> Unit) -> Unit,
    areUsingBottomNavigation: (Boolean) -> Unit,
    onChangeFab: (@Composable () -> Unit) -> Unit
) {
    NavHost(
        navController = appState.commonNavController,
        route = Graph.MAIN,
        startDestination = NavRoutes.Home.route
    ) {
        areUsingNestedScrollConnection(false)
        areUsingBottomNavigation(false)
        congregationNavGraph(appState, paddingValues, onChangeTopBarActions)
        territoryNavGraph(appState, paddingValues, onChangeTopBarActions)
        geoNavGraph(appState, paddingValues, onChangeTopBarActions)
        housingNavGraph(appState, paddingValues, onChangeTopBarActions)
        composable(NavRoutes.Home.route) {
            areUsingNestedScrollConnection(true)
            areUsingBottomNavigation(true)
            bottomBarNavGraph(
                appState, paddingValues, onChangeActionBar, onChangeActionBarTitle,
                onChangeTopBarActions, onChangeFab
            )
        }
    }
}

