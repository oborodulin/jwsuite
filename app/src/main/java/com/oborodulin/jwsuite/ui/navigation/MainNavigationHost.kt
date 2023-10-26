package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.ui.navigation.graphs.congregationNavGraph
import com.oborodulin.jwsuite.ui.navigation.graphs.geoNavGraph
import com.oborodulin.jwsuite.ui.navigation.graphs.housingNavGraph
import com.oborodulin.jwsuite.ui.navigation.graphs.territoryNavGraph
import timber.log.Timber

private const val TAG = "App.Navigation.MainNavigationHost"

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
// https://stackoverflow.com/questions/69738397/jetpackcompose-navigation-nested-graphs-cause-viewmodelstore-should-be-set-befo
@Composable
fun MainNavigationHost(
    paddingValues: PaddingValues,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    areUsingNestedScrollConnection: (Boolean) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit,
    areUsingBottomNavigation: (Boolean) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("MainNavigationHost(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val context = LocalContext.current
    NavHost(
        navController = appState.mainNavController,
        route = Graph.MAIN,
        startDestination = session.mainRoute
    ) {
        onActionBarChange(null)
        onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
        areUsingNestedScrollConnection(false)
        areUsingBottomNavigation(false)
        onTopBarActionsChange {}
        onFabChange {}
        congregationNavGraph(
            appState = appState,
            startDestination = session.startDestination,
            paddingValues = paddingValues,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        territoryNavGraph(
            appState = appState,
            startDestination = session.startDestination,
            paddingValues = paddingValues,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        geoNavGraph(
            appState = appState,
            startDestination = session.startDestination,
            paddingValues = paddingValues,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        housingNavGraph(
            appState = appState,
            startDestination = session.startDestination,
            paddingValues = paddingValues,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        composable(NavRoutes.Home.route) {
            Timber.tag(TAG).d("MainNavigationHost(...) -> Home.route called")
            onTopBarNavImageVectorChange(Icons.Outlined.Menu)
            onTopBarNavClickChange { context.toast("Menu navigation button clicked...") }
            onActionBarSubtitleChange(appState.actionBarSubtitle.value)
            areUsingNestedScrollConnection(true)
            areUsingBottomNavigation(true)
            BarNavigationHost(
                paddingValues = paddingValues,
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
    }
}

