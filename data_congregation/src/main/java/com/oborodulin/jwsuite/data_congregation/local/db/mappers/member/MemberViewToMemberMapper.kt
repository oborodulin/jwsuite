package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberMovementEntityToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.domain.model.Member

class MemberViewToMemberMapper(
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val groupMapper: GroupViewToGroupMapper,
    private val movementMapper: MemberMovementEntityToMemberMovementMapper
) : NullableMapper<MemberView, Member>, Mapper<MemberView, Member> {
    override fun map(input: MemberView): Member {
        val member = Member(
            congregation = congregationMapper.map(input.memberCongregation),
            group = groupMapper.nullableMap(input.group),
            memberNum = input.member.memberNum,
            memberName = input.member.memberName,
            surname = input.member.surname,
            patronymic = input.member.patronymic,
            pseudonym = input.member.pseudonym,
            phoneNumber = input.member.phoneNumber,
            dateOfBirth = input.member.dateOfBirth,
            dateOfBaptism = input.member.dateOfBaptism,
            loginExpiredDate = input.member.loginExpiredDate,
            memberCongregationId = input.lastCongregation.memberCongregationId,
            congregationId = input.lastCongregation.mcCongregationsId,
            activityDate = input.lastCongregation.activityDate,
            lastMovement = movementMapper.map(input.lastMovement)
        )
        member.id = input.member.memberId
        return member
    }

    override fun nullableMap(input: MemberView?) = input?.let { map(it) }
}