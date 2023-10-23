package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.ui.main.MainScreen

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
// https://foso.github.io/Jetpack-Compose-Playground/general/compositionlocal/
@Composable
fun RootNavigationGraph() {//appState: AppState, session: SessionUi?, startDestination: String) {
    NavHost(
        navController = LocalAppState.current.commonNavController,
        route = Graph.ROOT,
        startDestination = LocalSession.current.route
    ) {
        authNavGraph()
        composable(Graph.MAIN) {
            MainScreen()
        }
    }
}

