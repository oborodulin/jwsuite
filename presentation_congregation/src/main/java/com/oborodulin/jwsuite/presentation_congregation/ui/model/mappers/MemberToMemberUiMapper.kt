package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi

class MemberToMemberUiMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val groupMapper: GroupToGroupUiMapper
) : NullableMapper<Member, MemberUi>, Mapper<Member, MemberUi> {
    override fun map(input: Member): MemberUi {
        val memberUi = MemberUi(
            congregation = congregationMapper.map(input.congregation),
            group = groupMapper.nullableMap(input.group),
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
            loginExpiredDate = input.loginExpiredDate,
            memberCongregationId = input.memberCongregationId,
            congregationId = input.congregationId,
            activityDate = input.activityDate,
            memberMovementId = input.lastMovement.id,
            memberType = input.lastMovement.memberType,
            movementDate = input.lastMovement.movementDate
        )
        memberUi.id = input.id
        return memberUi
    }

    override fun nullableMap(input: Member?) = input?.let { map(it) }
}