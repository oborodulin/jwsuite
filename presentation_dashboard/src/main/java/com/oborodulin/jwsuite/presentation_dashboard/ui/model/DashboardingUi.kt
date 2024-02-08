package com.oborodulin.jwsuite.presentation_dashboard.ui.model

import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi

data class DashboardingUi(
    val favoriteCongregation: CongregationUi? = null,
    val congregationTotals: CongregationTotalsUi? = null,
    val territoryTotals: TerritoryTotalsUi? = null
)
