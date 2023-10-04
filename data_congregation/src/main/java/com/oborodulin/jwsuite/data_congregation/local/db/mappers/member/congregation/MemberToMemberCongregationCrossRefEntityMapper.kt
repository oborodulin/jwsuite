package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.domain.model.Member
import java.util.UUID

class MemberToMemberCongregationCrossRefEntityMapper :
    Mapper<Member, MemberCongregationCrossRefEntity> {
    override fun map(input: Member) = MemberCongregationCrossRefEntity(
        memberCongregationId = input.memberCongregationId ?: input.apply {
            memberCongregationId = UUID.randomUUID()
        }.memberCongregationId!!,
        activityDate = input.activityDate,
        mcCongregationsId = input.congregationId,
        mcMembersId = input.id!!
    )
}