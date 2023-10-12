package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi

class SessionToSessionUiMapper(private val mapper: RolesListToRolesListItemMapper) :
    Mapper<Session, SessionUi>, NullableMapper<Session, SessionUi> {
    override fun map(input: Session) = SessionUi(
        isSigned = input.isSigned,
        isLogged = input.isLogged,
        roles = mapper.map(input.roles),
        startDestination = when (input.isSigned) {
            true -> when (input.isLogged) {
                true -> when (input.startDestination) {
                    null -> Graph.MAIN  // navigate to MainScreen()
                    else -> input.startDestination // navigate to previous Destination
                }

                else -> NavRoutes.Login.route // navigate to LoginScreen
            }

            else -> NavRoutes.Signup.route // navigate to SignupScreen
        }
    )

    override fun nullableMap(input: Session?) = input?.let { map(it) }
}