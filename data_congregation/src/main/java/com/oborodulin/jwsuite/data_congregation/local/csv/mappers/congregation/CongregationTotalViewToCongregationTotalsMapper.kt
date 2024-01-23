package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView

class CongregationTotalViewToCongregationTotalsMapper(
    private val mapper: FavoriteCongregationViewToCongregationMapper
) : Mapper<CongregationTotalView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv>,
    NullableMapper<CongregationTotalView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv> {
    override fun map(input: CongregationTotalView) =
        com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv(
            congregation = mapper.map(input.congregation),
            totalMembers = input.totalMembers,
            prevMemberTotals = input.prevMemberTotals,
            totalFulltimeMembers = input.totalFulltimeMembers,
            prevFulltimeMemberTotals = input.prevFulltimeMemberTotals
        )

    override fun nullableMap(input: CongregationTotalView?) = input?.let { map(it) }
}