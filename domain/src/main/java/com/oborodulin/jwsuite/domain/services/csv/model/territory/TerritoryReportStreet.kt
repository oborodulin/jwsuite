package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryReportStreet(
    val territoryStreet: TerritoryStreet,
    val territoryMemberReport: TerritoryMemberReport
) : DomainModel()
