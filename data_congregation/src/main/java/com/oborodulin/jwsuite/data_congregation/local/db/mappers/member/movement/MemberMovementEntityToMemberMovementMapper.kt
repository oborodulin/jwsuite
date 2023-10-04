package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.model.MemberMovement

class MemberMovementEntityToMemberMovementMapper :
    NullableMapper<MemberMovementEntity, MemberMovement>,
    Mapper<MemberMovementEntity, MemberMovement> {
    override fun map(input: MemberMovementEntity): MemberMovement {
        val member = MemberMovement(
            memberType = input.memberType,
            movementDate = input.movementDate,
            memberId = input.mMembersId,
        )
        member.id = input.memberMovementId
        return member
    }

    override fun nullableMap(input: MemberMovementEntity?) = input?.let { map(it) }
}