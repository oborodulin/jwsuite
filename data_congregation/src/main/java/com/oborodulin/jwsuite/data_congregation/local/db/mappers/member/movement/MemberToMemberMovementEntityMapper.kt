package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.model.congregation.Member
import java.util.UUID

class MemberToMemberMovementEntityMapper : Mapper<Member, MemberMovementEntity> {
    override fun map(input: Member) = MemberMovementEntity(
        memberMovementId = input.lastMovement?.id ?: input.lastMovement?.apply {
            id = UUID.randomUUID()
        }?.id!!,
        memberType = input.lastMovement?.memberType!!,
        movementDate = input.lastMovement?.movementDate!!,
        mMembersId = input.id!!
    )
}