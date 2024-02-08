package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.domain.model.congregation.CongregationTotals
import com.oborodulin.jwsuite.domain.model.territory.TerritoryTotals

data class Dashboard(
    val favoriteCongregation: Congregation? = null,
    val congregationTotals: CongregationTotals? = null,
    val territoryTotals: TerritoryTotals? = null
) : DomainModel()
