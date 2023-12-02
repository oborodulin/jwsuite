package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.appSettingNavigationGraph
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation_congregation.navigation.congregationNavGraph
import com.oborodulin.jwsuite.presentation_geo.navigation.geoNavGraph
import com.oborodulin.jwsuite.presentation_territory.navigation.housingNavGraph
import com.oborodulin.jwsuite.presentation_territory.navigation.territoryNavGraph
import timber.log.Timber

private const val TAG = "App.Navigation.MainNavigationHost"

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
// https://stackoverflow.com/questions/69738397/jetpackcompose-navigation-nested-graphs-cause-viewmodelstore-should-be-set-befo
@Composable
fun MainNavigationHost(
    innerPadding: PaddingValues,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onNavIconChange: (@Composable (() -> Unit)?) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    shouldUseNestedScrollConnection: (Boolean) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    areUsingBottomNavigation: (Boolean) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("MainNavigationHost(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val context = LocalContext.current
    Timber.tag(TAG).d("MainNavigationHost: session.mainRoute = %s", session.mainRoute)
    NavHost(
        navController = appState.mainNavController,// .rootNavController,
        route = Graph.MAIN,
        startDestination = session.mainRoute, //NavRoutes.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        Timber.tag(TAG).d("MainNavigationHost -> NavHost(...)")
        /*onActionBarChange(null)
        shouldUseNestedScrollConnection(false)
        areUsingBottomNavigation(false)
        onNavIconChange(null)
        onTopBarActionsChange (true) {}
        onFabChange {}*/
        congregationNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        territoryNavGraph(
            startDestination = session.startDestination,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        geoNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        housingNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        appSettingNavigationGraph(
            startDestination = session.startDestination,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        composable(NavRoutes.Home.route) {
            Timber.tag(TAG).d("MainNavigationHost -> composable(Home.route) called")
            /*onActionBarSubtitleChange(appState.actionBarSubtitle.value)
            onTopBarNavImageVectorChange(Icons.Outlined.Menu)
            onTopBarNavClickChange { context.toast("Menu navigation button clicked...") }
            shouldUseNestedScrollConnection(true)
            areUsingBottomNavigation(true)*/
            BarNavigationHost(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
    }
}

