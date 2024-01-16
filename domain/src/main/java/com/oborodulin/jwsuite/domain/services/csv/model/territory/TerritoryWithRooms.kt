package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryWithRooms(
    val territory: Territory,
    val rooms: List<Room> = emptyList()
) : DomainModel()