package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi

class MemberToMemberUiMapper(private val groupMapper: GroupToGroupUiMapper) :
    NullableMapper<Member, MemberUi>, Mapper<Member, MemberUi> {
    override fun map(input: Member): MemberUi {
        val memberUi = MemberUi(
            group = groupMapper.map(input.group),
            memberNum = input.memberNum,
            memberName = input.memberName,
            surname = input.surname,
            patronymic = input.patronymic,
            pseudonym = input.pseudonym,
            memberFullName = input.fullName,
            memberShortName = input.shortName,
            phoneNumber = input.phoneNumber,
            dateOfBirth = input.dateOfBirth,
            dateOfBaptism = input.dateOfBaptism,
            memberCongregationId = input.memberCongregationId,
            congregationId = input.congregationId,
            activityDate = input.activityDate,
            memberMovementId = input.memberMovementId,
            memberType = input.memberType,
            movementDate = input.movementDate
        )
        memberUi.id = input.id
        return memberUi
    }

    override fun nullableMap(input: Member?) = input?.let { map(it) }
}