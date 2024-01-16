package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryReportRoom(
    val room: Room,
    val territoryMemberReport: TerritoryMemberReport
) : DomainModel()
