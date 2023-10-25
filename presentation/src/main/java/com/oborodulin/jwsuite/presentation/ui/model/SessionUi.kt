package com.oborodulin.jwsuite.presentation.ui.model

import androidx.compose.runtime.compositionLocalOf
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes

val LocalSession = compositionLocalOf<SessionUi> { error("No session found!") }

data class SessionUi(
    val isSigned: Boolean = false,
    val isLogged: Boolean = false,
    val roles: List<RolesListItem> = emptyList(),
    val lastDestination: String? = null
) : ModelUi() {
    val rootRoute = when (isSigned && isLogged) {
        true -> Graph.MAIN  // navigate to MainScreen()
        else -> Graph.AUTH // navigate to SignupScreen or LoginScreen
    }

    val authStartDestination = when (isSigned) {
        true -> when (isLogged) {
            true -> Graph.MAIN  // navigate to MainScreen()
            else -> NavRoutes.Login.route // navigate to LoginScreen
        }

        else -> NavRoutes.Signup.route // navigate to SignupScreen
    }

    val mainStartDestination = when (isSigned) {
        true -> when (isLogged) {
            true -> when (lastDestination) {
                null -> NavRoutes.Dashboarding.route  // navigate to DashboardingScreen()
                else -> lastDestination // navigate to previous startDestination
            }

            else -> NavRoutes.Login.route // navigate to LoginScreen
        }

        else -> NavRoutes.Signup.route // navigate to SignupScreen
    }

    val mainRoute = when {
        lastDestination == null -> NavRoutes.Home.route  // navigate to DashboardingScreen()
        listOf(
            NavRoutes.Dashboarding.route,
            NavRoutes.Congregating.route,
            NavRoutes.Territoring.route
        ).contains(lastDestination) -> NavRoutes.Home.route

        else -> NavRoutes.Home.route
    }

    override fun toString(): String {
        return "SessionUi(isSigned=$isSigned, isLogged=$isLogged, roles=$roles, lastDestination='$lastDestination', rootRoute='$rootRoute', authStartDestination='$authStartDestination', mainRoute='$mainRoute', mainStartDestination='$mainStartDestination')"
    }
}
