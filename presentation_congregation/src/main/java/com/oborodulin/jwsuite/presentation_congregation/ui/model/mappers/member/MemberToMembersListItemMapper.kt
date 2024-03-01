package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group.GroupToGroupUiMapper
import java.util.UUID

class MemberToMembersListItemMapper : Mapper<Member, MembersListItem> {
    override fun map(input: Member) = MembersListItem(
        id = input.id ?: UUID.randomUUID(),
        memberNum = input.memberNum,
        fullNum = input.fullNum,
        memberFullName = input.fullName,
        memberShortName = input.shortName,
        phoneNumber = input.phoneNumber,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism,
        memberType = input.lastMovement?.memberType,
        movementDate = input.lastMovement?.movementDate
    )
}