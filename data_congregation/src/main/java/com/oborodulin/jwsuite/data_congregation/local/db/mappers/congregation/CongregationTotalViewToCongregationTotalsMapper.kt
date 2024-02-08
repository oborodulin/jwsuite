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
        totalGroups = input.totalGroups,
        totalMembers = input.totalMembers,
        totalFulltimeMembers = input.totalFulltimeMembers,
        diffGroups = input.diffGroups,
        diffMembers = input.diffMembers,
        diffFulltimeMembers = input.diffFulltimeMembers
    )

    override fun nullableMap(input: CongregationTotalView?) = input?.let { map(it) }
}