package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel

data class CongregationTotals(
    val congregation: Congregation,
    val totalGroups: Int,
    val totalMembers: Int,
    val totalFulltimeMembers: Int,
    val totalTerritories: Int,
    val totalTerritoryIssued: Int,
    val totalTerritoryProcessed: Int,
    val diffGroups: Int,
    val diffMembers: Int,
    val diffFulltimeMembers: Int,
    val diffTerritories: Int,
    val diffTerritoryIssued: Int,
    val diffTerritoryProcessed: Int
) : DomainModel()