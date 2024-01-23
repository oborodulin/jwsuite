package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv
import java.util.UUID

class MemberToMemberMovementEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv, MemberMovementEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv) = MemberMovementEntity(
        memberMovementId = input.lastMovement.id ?: input.lastMovement.apply {
            id = UUID.randomUUID()
        }.id!!,
        memberType = input.lastMovement.memberType,
        movementDate = input.lastMovement.movementDate,
        mMembersId = input.id!!
    )
}