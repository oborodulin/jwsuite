package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.role.RolesListToRolesListItemMapper

class SessionToSessionUiMapper(private val mapper: RolesListToRolesListItemMapper) :
    Mapper<Session, SessionUi>, NullableMapper<Session, SessionUi> {
    override fun map(input: Session) = SessionUi(
        isSigned = input.isSigned,
        isLogged = input.isLogged,
        roles = mapper.map(input.roles),
        lastDestination = input.lastDestination
    )

    override fun nullableMap(input: Session?) = input?.let { map(it) }
}