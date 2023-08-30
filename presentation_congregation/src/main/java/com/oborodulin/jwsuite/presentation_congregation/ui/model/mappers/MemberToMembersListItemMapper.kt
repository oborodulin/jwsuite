package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import java.util.UUID

class MemberToMembersListItemMapper(private val groupMapper: GroupToGroupUiMapper) :
    Mapper<Member, MembersListItem> {
    override fun map(input: Member) = MembersListItem(
        id = input.id ?: UUID.randomUUID(),
        group = groupMapper.map(input.group),
        memberNum = input.memberNum,
        memberFullName = input.fullName,
        memberShortName = input.shortName,
        phoneNumber = input.phoneNumber,
        memberType = input.memberType,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism,
        inactiveDate = input.inactiveDate
    )
}