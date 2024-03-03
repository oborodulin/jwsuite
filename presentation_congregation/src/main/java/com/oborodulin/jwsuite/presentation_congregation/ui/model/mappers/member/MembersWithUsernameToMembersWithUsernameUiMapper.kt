package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.MembersWithUsername
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersWithUsernameUi

class MembersWithUsernameToMembersWithUsernameUiMapper(
    private val mapper: MembersListToMembersListItemMapper
) : NullableMapper<MembersWithUsername, MembersWithUsernameUi>,
    Mapper<MembersWithUsername, MembersWithUsernameUi> {
    override fun map(input: MembersWithUsername) = MembersWithUsernameUi(
        username = input.username,
        members = mapper.map(input.members)
    )

    override fun nullableMap(input: MembersWithUsername?) = input?.let { map(it) }
}