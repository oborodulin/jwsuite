package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.session.signup.SignupScreen
import timber.log.Timber

private const val TAG = "App.Navigation.authNavGraph"

fun NavGraphBuilder.authNavGraph(
    appState: AppState,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit
) {
    navigation(route = Graph.AUTH, startDestination = NavRoutes.Signup.route) {
        composable(route = NavRoutes.Signup.route, arguments = NavRoutes.Signup.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to SignupScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Signup.arguments
                )
            SignupScreen(
                appState = appState,
                paddingValues = paddingValues,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavClickChange = onTopBarNavClickChange
            )
        }
        composable(route = NavRoutes.Login.route, arguments = NavRoutes.Login.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to LoginScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Login.arguments
                )
            //LoginScreen(
            // appState = appState,
            //                paddingValues = paddingValues,
            //                onActionBarSubtitleChange = onActionBarSubtitleChange,
            //                onTopBarNavClickChange = onTopBarNavClickChange)
        }
    }
}
