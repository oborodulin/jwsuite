package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.ui.main.MainScreen

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
@Composable
fun RootNavigationGraph(appState: AppState, startDestination: String) {
    NavHost(
        navController = appState.commonNavController,
        route = Graph.ROOT,
        startDestination = startDestination
    ) {
        authNavGraph(appState)
        composable(Graph.MAIN) {
            MainScreen(appState)
        }
    }
}

