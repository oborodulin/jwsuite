package com.oborodulin.jwsuite.presentation.ui.model

import androidx.compose.runtime.compositionLocalOf
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.MemberRoleType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes

val LocalSession = compositionLocalOf<SessionUi> { error("No session found!") }

data class SessionUi(
    val isSigned: Boolean = false,
    val isLogged: Boolean = false,
    val roles: List<RolesListItem> = emptyList(),
    val lastDestination: String? = null
) : ModelUi() {
    /*val rootRoute = when (isSigned) {
        true -> Graph.MAIN  // navigate to MainScreen()
        else -> Graph.AUTH // navigate to SignupScreen or LoginScreen
    }*/

    val authStartDestination = when (isSigned) {
        true -> NavRoutes.Login.route // navigate to LoginScreen()
        else -> NavRoutes.Signup.route // navigate to SignupScreen() (Graph.MAIN)
    }

    val mainRoute = NavRoutes.mainRouteByDestination(lastDestination)

    val startDestination = when (lastDestination) {
        null,
        NavRoutes.Home.route -> NavRoutes.Dashboarding.route  // navigate to DashboardingScreen()
        else -> lastDestination // navigate to previous startDestination
    }

    fun containsRole(roleType: MemberRoleType) = roles.map { it.roleType }.contains(roleType)

    fun containsRoles(roleTypes: List<MemberRoleType>) =
        roles.map { it.roleType }.containsAll(roleTypes)

    override fun toString(): String {
        return "SessionUi(isSigned=$isSigned, isLogged=$isLogged, roles=$roles, lastDestination='$lastDestination', authStartDestination='$authStartDestination', mainRoute='$mainRoute', startDestination='$startDestination')"
    }
}
