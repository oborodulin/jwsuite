package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberLastCongregationView
import com.oborodulin.jwsuite.domain.model.congregation.MemberCongregation

class MemberLastCongregationViewToMemberCongregationMapper(private val mapper: MemberCongregationCrossRefEntityToMemberCongregationMapper) :
    NullableMapper<MemberLastCongregationView, MemberCongregation>,
    Mapper<MemberLastCongregationView, MemberCongregation> {
    override fun map(input: MemberLastCongregationView) = mapper.map(input.lastMemberCongregation)

    override fun nullableMap(input: MemberLastCongregationView?) = input?.let { map(it) }
}