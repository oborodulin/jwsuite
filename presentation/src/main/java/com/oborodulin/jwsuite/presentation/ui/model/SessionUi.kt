package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes

data class SessionUi(
    val isSigned: Boolean = false,
    val isLogged: Boolean = false,
    val roles: List<RolesListItem> = emptyList(),
    val startDestination: String? = null
) : ModelUi() {
    val route = when (isSigned) {
        true -> when (isLogged) {
            true -> when (startDestination) {
                null -> Graph.MAIN  // navigate to MainScreen()
                else -> startDestination // navigate to previous startDestination
            }

            else -> NavRoutes.Login.route // navigate to LoginScreen
        }

        else -> NavRoutes.Signup.route // navigate to SignupScreen
    }
}
