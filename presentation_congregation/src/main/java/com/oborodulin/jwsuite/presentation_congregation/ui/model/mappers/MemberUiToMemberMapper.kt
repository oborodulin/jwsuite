package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi

class MemberUiToMemberMapper(private val groupUiMapper: GroupUiToGroupMapper) :
    Mapper<MemberUi, Member> {
    override fun map(input: MemberUi): Member {
        val member = Member(
            group = groupUiMapper.map(input.group),
            memberNum = input.memberNum,
            memberName = input.memberName,
            surname = input.surname,
            patronymic = input.patronymic,
            pseudonym = input.pseudonym,
            phoneNumber = input.phoneNumber,
            dateOfBirth = input.dateOfBirth,
            dateOfBaptism = input.dateOfBaptism,
            memberCongregationId = input.memberCongregationId,
            congregationId = input.congregationId!!,
            activityDate = input.activityDate,
            memberMovementId = input.memberMovementId,
            memberType = input.memberType,
            movementDate = input.movementDate
        )
        member.id = input.id
        return member
    }
}