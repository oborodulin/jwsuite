package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.congregation.MemberMovement
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group.GroupUiToGroupMapper

class MemberUiToMemberMapper(
    private val congregationUiMapper: CongregationUiToCongregationMapper,
    private val groupUiMapper: GroupUiToGroupMapper
) :
    Mapper<MemberUi, Member> {
    override fun map(input: MemberUi): Member {
        val movement = MemberMovement(
            memberId = input.id!!,
            memberType = input.memberType,
            movementDate = input.movementDate
        )
        movement.id = input.memberMovementId
        val member = Member(
            congregation = congregationUiMapper.map(input.congregation),
            group = groupUiMapper.nullableMap(input.group),
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
            lastMovement = movement
        )
        member.id = input.id
        return member
    }
}