package com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryTotals
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.TerritoryTotalsUi

class TerritoryTotalsToTerritoryTotalsUiMapper(private val mapper: CongregationToCongregationUiMapper) :
    NullableMapper<TerritoryTotals, TerritoryTotalsUi>,
    Mapper<TerritoryTotals, TerritoryTotalsUi> {
    override fun map(input: TerritoryTotals) = TerritoryTotalsUi(
        congregation = mapper.map(input.congregation),
        totalTerritories = input.totalTerritories,
        totalTerritoryIssued = input.totalTerritoryIssued,
        totalTerritoryProcessed = input.totalTerritoryProcessed,
        diffTerritories = input.diffTerritories,
        diffTerritoryIssued = input.diffTerritoryIssued,
        diffTerritoryProcessed = input.diffTerritoryProcessed
    ).also { it.id = input.id }

    override fun nullableMap(input: TerritoryTotals?) = input?.let { map(it) }
}