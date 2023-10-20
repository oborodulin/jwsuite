package com.oborodulin.jwsuite.ui.navigation.graphs

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
import com.oborodulin.jwsuite.presentation.ui.AppState

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
@Composable
fun MainNavigationGraph(
    appState: AppState,
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
    val context = LocalContext.current
    NavHost(
        navController = appState.commonNavController,
        route = Graph.MAIN,
        startDestination = NavRoutes.Home.route
    ) {
        onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
        areUsingNestedScrollConnection(false)
        areUsingBottomNavigation(false)
        congregationNavGraph(
            appState = appState,
            paddingValues = paddingValues,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        territoryNavGraph(
            appState = appState,
            paddingValues = paddingValues,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        geoNavGraph(
            appState = appState,
            paddingValues = paddingValues,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        housingNavGraph(
            appState = appState,
            paddingValues = paddingValues,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange
        )
        composable(NavRoutes.Home.route) {
            onTopBarNavImageVectorChange(Icons.Outlined.Menu)
            onTopBarNavClickChange { context.toast("Menu navigation button clicked...") }
            onActionBarSubtitleChange(appState.actionBarSubtitle.value)
            areUsingNestedScrollConnection(true)
            areUsingBottomNavigation(true)
            bottomBarNavGraph(
                appState = appState,
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

