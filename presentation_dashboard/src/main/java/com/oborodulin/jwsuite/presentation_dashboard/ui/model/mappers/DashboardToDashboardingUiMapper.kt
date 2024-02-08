package com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Dashboard
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardingUi

class DashboardToDashboardingUiMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val congregationTotalsMapper: CongregationTotalsToCongregationTotalsUiMapper,
    private val territoryTotalsMapper: TerritoryTotalsToTerritoryTotalsUiMapper
) : Mapper<Dashboard, DashboardingUi> {
    override fun map(input: Dashboard) = DashboardingUi(
        favoriteCongregation = congregationMapper.nullableMap(input.favoriteCongregation),
        congregationTotals = congregationTotalsMapper.nullableMap(input.congregationTotals),
        territoryTotals = territoryTotalsMapper.nullableMap(input.territoryTotals),
    )
}