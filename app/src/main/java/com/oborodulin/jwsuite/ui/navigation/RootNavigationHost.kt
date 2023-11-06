package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.authNavGraph
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.ui.main.MainActivity
import com.oborodulin.jwsuite.ui.main.MainScreen
import timber.log.Timber

private const val TAG = "App.Navigation.RootNavigationHost"

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
// https://foso.github.io/Jetpack-Compose-Playground/general/compositionlocal/
@Composable
fun RootNavigationHost(activity: MainActivity, sessionViewModel: SessionViewModel) {
    Timber.tag(TAG).d("RootNavigationHost(...) called")
    val session = LocalSession.current
    Timber.tag(TAG).d(
        "RootNavigationHost(...): session.rootRoute = %s; session.authStartDestination = %s",
        session.rootRoute,
        session.authStartDestination
    )
    NavHost(
        navController = LocalAppState.current.rootNavController,
        route = Graph.ROOT,
        startDestination = session.rootRoute // Graph.AUTH
    ) {
        Timber.tag(TAG).d("RootNavigationHost(...) -> NavHost()")
        authNavGraph(
            startDestination = session.authStartDestination,
            sessionViewModel = sessionViewModel
        )
        composable(Graph.MAIN) {
            Timber.tag(TAG).d("RootNavigationHost(...) -> NavHost.composable(Graph.MAIN)")
            activity.initDatabase()
            MainScreen(sessionViewModel = sessionViewModel)
        }
    }
}

