package com.oborodulin.jwsuite.presentation_dashboard.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi

data class CongregationTotalsUi(
    val congregation: CongregationUi = CongregationUi(),
    val totalGroups: Int = 0,
    val totalMembers: Int = 0,
    val totalFulltimeMembers: Int = 0,
    val totalTerritories: Int = 0,
    val totalTerritoryIssued: Int = 0,
    val totalTerritoryProcessed: Int = 0,
    val diffGroups: Int = 0,
    val diffMembers: Int = 0,
    val diffFulltimeMembers: Int = 0,
    val diffTerritories: Int = 0,
    val diffTerritoryIssued: Int = 0,
    val diffTerritoryProcessed: Int = 0
) : ModelUi()