package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryHouseReport(
    val house: House,
    val territoryReport: TerritoryReport
) : DomainModel()
