package com.oborodulin.jwsuite.presentation_dashboard.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi

data class TerritoryTotalsUi(
    val congregation: CongregationUi = CongregationUi(),
    val totalTerritories: Int = 0,
    val totalTerritoryIssued: Int = 0,
    val totalTerritoryProcessed: Int = 0,
    val diffTerritories: Int = 0,
    val diffTerritoryIssued: Int = 0,
    val diffTerritoryProcessed: Int = 0
) : ModelUi()