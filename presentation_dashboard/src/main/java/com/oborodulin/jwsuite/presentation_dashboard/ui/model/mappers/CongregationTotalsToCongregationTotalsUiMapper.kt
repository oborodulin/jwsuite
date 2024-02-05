package com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.CongregationTotals
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.CongregationTotalsUi

class CongregationTotalsToCongregationTotalsUiMapper(private val mapper: CongregationToCongregationUiMapper) :
    NullableMapper<CongregationTotals, CongregationTotalsUi>,
    Mapper<CongregationTotals, CongregationTotalsUi> {
    override fun map(input: CongregationTotals): CongregationTotalsUi {
        val totalsUi = CongregationTotalsUi(
            congregation = mapper.map(input.congregation),
            totalGroups = input.totalGroups,
            totalMembers = input.totalMembers,
            totalFulltimeMembers = input.totalFulltimeMembers,
            totalTerritories = input.totalTerritories,
            totalTerritoryIssued = input.totalTerritoryIssued,
            totalTerritoryProcessed = input.totalTerritoryProcessed,
            diffGroups = input.diffGroups,
            diffMembers = input.diffMembers,
            diffFulltimeMembers = input.diffFulltimeMembers,
            diffTerritories = input.diffTerritories,
            diffTerritoryIssued = input.diffTerritoryIssued,
            diffTerritoryProcessed = input.diffTerritoryProcessed
        )
        totalsUi.id = input.id
        return totalsUi
    }

    override fun nullableMap(input: CongregationTotals?) = input?.let { map(it) }
}