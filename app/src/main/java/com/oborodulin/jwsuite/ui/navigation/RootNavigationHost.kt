package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.ui.main.MainActivity
import com.oborodulin.jwsuite.ui.main.MainScreen
import com.oborodulin.jwsuite.ui.navigation.graphs.authNavGraph
import timber.log.Timber

private const val TAG = "App.Navigation.RootNavigationHost"

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
// https://foso.github.io/Jetpack-Compose-Playground/general/compositionlocal/
@Composable
fun RootNavigationHost(
    activity: MainActivity,
    viewModel: SessionViewModel
) {//appState: AppState, session: SessionUi?, startDestination: String) {
    Timber.tag(TAG).d("RootNavigationHost(...) called")
    val session = LocalSession.current
    NavHost(
        navController = LocalAppState.current.rootNavController,
        route = Graph.ROOT,
        startDestination = session.rootRoute
    ) {
        authNavGraph(startDestination = session.authStartDestination, viewModel = viewModel)
        composable(Graph.MAIN) {
            Timber.tag(TAG).d("NavHost.composable(Graph.MAIN) called")
            activity.initDatabase()
            MainScreen(viewModel = viewModel)
        }
    }
}

