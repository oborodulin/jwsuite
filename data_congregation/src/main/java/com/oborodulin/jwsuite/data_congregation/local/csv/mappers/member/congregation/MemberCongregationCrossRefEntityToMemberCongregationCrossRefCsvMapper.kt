package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCongregationCrossRefCsv

class MemberCongregationCrossRefEntityToMemberCongregationCrossRefCsvMapper :
    Mapper<MemberCongregationCrossRefEntity, MemberCongregationCrossRefCsv> {
    override fun map(input: MemberCongregationCrossRefEntity) = MemberCongregationCrossRefCsv(
        memberCongregationId = input.memberCongregationId,
        activityDate = input.activityDate,
        mcMembersId = input.mcMembersId,
        mcCongregationsId = input.mcCongregationsId
    )
}