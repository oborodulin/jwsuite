package com.oborodulin.jwsuite.data.local.db.mappers.csv.member.movement

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity

class MemberMovementEntityToMemberMovementMapper :
    NullableMapper<MemberMovementEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv>,
    Mapper<MemberMovementEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv> {
    override fun map(input: MemberMovementEntity): com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv {
        val member =
            com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv(
                memberType = input.memberType,
                movementDate = input.movementDate,
                memberId = input.mMembersId,
            )
        member.id = input.memberMovementId
        return member
    }

    override fun nullableMap(input: MemberMovementEntity?) = input?.let { map(it) }
}