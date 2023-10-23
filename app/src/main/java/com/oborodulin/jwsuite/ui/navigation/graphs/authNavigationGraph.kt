package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.session.login.LoginScreen
import com.oborodulin.jwsuite.presentation.ui.session.signup.SignupScreen
import timber.log.Timber

private const val TAG = "App.Navigation.authNavGraph"

fun NavGraphBuilder.authNavGraph() {
    navigation(route = Graph.AUTH, startDestination = NavRoutes.Signup.route) {
        composable(route = NavRoutes.Signup.route, arguments = NavRoutes.Signup.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to SignupScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Signup.arguments
                )
            SignupScreen()
        }
        composable(route = NavRoutes.Login.route, arguments = NavRoutes.Login.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to LoginScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Login.arguments
                )
            LoginScreen()
        }
    }
}
