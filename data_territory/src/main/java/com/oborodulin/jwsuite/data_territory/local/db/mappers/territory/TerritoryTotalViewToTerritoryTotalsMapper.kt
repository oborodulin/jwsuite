package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.FavoriteCongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryTotalView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryTotals

class TerritoryTotalViewToTerritoryTotalsMapper(
    private val mapper: FavoriteCongregationViewToCongregationMapper
) : Mapper<TerritoryTotalView, TerritoryTotals>,
    NullableMapper<TerritoryTotalView, TerritoryTotals> {
    override fun map(input: TerritoryTotalView) = TerritoryTotals(
        congregation = mapper.map(input.congregation),
        totalTerritories = input.totalTerritories,
        totalTerritoryIssued = input.totalTerritoryIssued,
        totalTerritoryProcessed = input.totalTerritoryProcessed,
        diffTerritories = input.diffTerritories,
        diffTerritoryIssued = input.diffTerritoryIssued,
        diffTerritoryProcessed = input.diffTerritoryProcessed
    )

    override fun nullableMap(input: TerritoryTotalView?) = input?.let { map(it) }
}