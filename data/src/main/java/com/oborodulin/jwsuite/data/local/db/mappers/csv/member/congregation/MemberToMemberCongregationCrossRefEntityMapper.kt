package com.oborodulin.jwsuite.data.local.db.mappers.csv.member.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import java.util.UUID

class MemberToMemberCongregationCrossRefEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv, MemberCongregationCrossRefEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv) = MemberCongregationCrossRefEntity(
        memberCongregationId = input.memberCongregationId ?: input.apply {
            memberCongregationId = UUID.randomUUID()
        }.memberCongregationId!!,
        activityDate = input.activityDate,
        mcCongregationsId = input.congregationId,
        mcMembersId = input.id!!
    )
}