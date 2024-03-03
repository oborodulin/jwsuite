package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group.GroupToGroupUiMapper

class MemberToMemberUiMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val groupMapper: GroupToGroupUiMapper
) : NullableMapper<Member, MemberUi>, Mapper<Member, MemberUi> {
    override fun map(input: Member) = MemberUi(
        congregation = congregationMapper.map(input.congregation!!),
        group = groupMapper.nullableMap(input.group),
        memberNum = input.memberNum,
        memberName = input.memberName,
        surname = input.surname,
        patronymic = input.patronymic,
        pseudonym = input.pseudonym,
        fullNum = input.fullNum,
        memberFullName = input.fullName,
        memberShortName = input.shortName,
        phoneNumber = input.phoneNumber,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism,
        loginExpiredDate = input.loginExpiredDate,
        memberCongregationId = input.lastCongregation?.id,
        congregationId = input.lastCongregation?.congregationId,
        activityDate = input.lastCongregation?.activityDate,
        memberMovementId = input.lastMovement?.id,
        memberType = input.lastMovement?.memberType!!,
        movementDate = input.lastMovement?.movementDate!!
    ).also { it.id = input.id }

    override fun nullableMap(input: Member?) = input?.let { map(it) }
}