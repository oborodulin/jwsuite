package com.oborodulin.jwsuite.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.login.LoginScreen
import com.oborodulin.jwsuite.presentation.ui.session.signup.SignupScreen
import timber.log.Timber

private const val TAG = "App.Navigation.authNavGraph"

fun NavGraphBuilder.authNavGraph(startDestination: String, sessionViewModel: SessionViewModel) {
    Timber.tag(TAG).d("authNavGraph() called")
    navigation(
        route = Graph.AUTH,
        startDestination = startDestination //NavRoutes.Login.route
    ) {
        Timber.tag(TAG)
            .d("authNavGraph.navigation(...) called: startDestination = %s", startDestination)
        composable(route = NavRoutes.Login.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to LoginScreen [route = '%s']", it.destination.route
                )
            LoginScreen(sessionViewModel)
        }
        composable(route = NavRoutes.Signup.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to SignupScreen [route = '%s']", it.destination.route
                )
            SignupScreen(sessionViewModel)
        }
        Timber.tag(TAG).d("authNavGraph.navigation(...) ended")
    }
    Timber.tag(TAG).d("authNavGraph() ended")
}
