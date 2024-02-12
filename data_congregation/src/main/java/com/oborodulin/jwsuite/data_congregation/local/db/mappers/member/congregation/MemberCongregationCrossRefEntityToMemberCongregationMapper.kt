package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.domain.model.congregation.MemberCongregation

class MemberCongregationCrossRefEntityToMemberCongregationMapper :
    NullableMapper<MemberCongregationCrossRefEntity, MemberCongregation>,
    Mapper<MemberCongregationCrossRefEntity, MemberCongregation> {
    override fun map(input: MemberCongregationCrossRefEntity): MemberCongregation {
        val memberCongregation = MemberCongregation(
            memberId = input.mcMembersId,
            congregationId = input.mcCongregationsId,
            activityDate = input.activityDate
        )
        memberCongregation.id = input.memberCongregationId
        return memberCongregation
    }

    override fun nullableMap(input: MemberCongregationCrossRefEntity?) = input?.let { map(it) }
}