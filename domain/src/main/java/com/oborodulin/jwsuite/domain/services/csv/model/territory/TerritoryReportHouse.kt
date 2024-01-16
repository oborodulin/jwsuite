package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryReportHouse(
    val house: House,
    val territoryMemberReport: TerritoryMemberReport
) : DomainModel()
