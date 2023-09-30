package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.model.Member
import java.util.UUID

class MemberToMemberMovementEntityMapper : Mapper<Member, MemberMovementEntity> {
    override fun map(input: Member) = MemberMovementEntity(
        memberMovementId = input.memberMovementId ?: input.apply {
            memberMovementId = UUID.randomUUID()
        }.memberMovementId!!,
        memberType = input.memberType,
        movementDate = input.movementDate,
        mMembersId = input.id!!
    )
}