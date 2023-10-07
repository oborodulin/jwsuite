package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView
import com.oborodulin.jwsuite.domain.model.congregation.CongregationTotals

class CongregationTotalViewToCongregationTotalsMapper(
    private val mapper: FavoriteCongregationViewToCongregationMapper
) : Mapper<CongregationTotalView, CongregationTotals>,
    NullableMapper<CongregationTotalView, CongregationTotals> {
    override fun map(input: CongregationTotalView) = CongregationTotals(
        congregation = mapper.map(input.congregation),
        totalMembers = input.totalMembers,
        prevMemberTotals = input.prevMemberTotals,
        totalFulltimeMembers = input.totalFulltimeMembers,
        prevFulltimeMemberTotals = input.prevFulltimeMemberTotals
    )

    override fun nullableMap(input: CongregationTotalView?) = input?.let { map(it) }
}