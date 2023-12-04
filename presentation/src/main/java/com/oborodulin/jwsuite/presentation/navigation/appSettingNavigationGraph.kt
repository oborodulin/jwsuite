package com.oborodulin.jwsuite.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.ui.appsetting.AppSettingScreen
import timber.log.Timber

private const val TAG = "Presentation.Navigation.appSettingNavigationGraph"

fun NavGraphBuilder.appSettingNavigationGraph(
    startDestination: String? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    navigation(
        route = Graph.SETTINGS,
        startDestination = startDestination ?: NavRoutes.Settings.route
    ) {
        composable(
            route = NavRoutes.Settings.route, arguments = NavRoutes.Settings.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to AppSettingScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Settings.arguments.firstOrNull()
            )
            AppSettingScreen(
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