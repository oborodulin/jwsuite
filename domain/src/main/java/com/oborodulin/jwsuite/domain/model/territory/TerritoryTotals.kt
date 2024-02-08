package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.model.congregation.Congregation

data class TerritoryTotals(
    val congregation: Congregation,
    val totalTerritories: Int,
    val totalTerritoryIssued: Int,
    val totalTerritoryProcessed: Int,
    val diffTerritories: Int,
    val diffTerritoryIssued: Int,
    val diffTerritoryProcessed: Int
) : DomainModel()