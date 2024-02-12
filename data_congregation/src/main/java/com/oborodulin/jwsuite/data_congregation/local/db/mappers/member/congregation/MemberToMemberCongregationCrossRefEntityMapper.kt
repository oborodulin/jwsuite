package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.domain.model.congregation.Member
import java.util.UUID

class MemberToMemberCongregationCrossRefEntityMapper :
    Mapper<Member, MemberCongregationCrossRefEntity> {
    override fun map(input: Member) = MemberCongregationCrossRefEntity(
        memberCongregationId = input.lastCongregation.id ?: input.lastCongregation.apply {
            id = UUID.randomUUID()
        }.id!!,
        activityDate = input.lastCongregation.activityDate,
        mcCongregationsId = input.lastCongregation.congregationId,
        mcMembersId = input.lastCongregation.memberId
    )
}