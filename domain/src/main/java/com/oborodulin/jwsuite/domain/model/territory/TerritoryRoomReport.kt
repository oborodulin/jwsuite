package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryRoomReport(
    val room: Room,
    val territoryReport: TerritoryReport
) : DomainModel()
