package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv

class MemberMovementCsvToMemberMovementEntityMapper :
    Mapper<MemberMovementCsv, MemberMovementEntity> {
    override fun map(input: MemberMovementCsv) = MemberMovementEntity(
        memberMovementId = input.memberMovementId,
        memberType = input.memberType,
        movementDate = input.movementDate,
        mMembersId = input.mMembersId
    )
}