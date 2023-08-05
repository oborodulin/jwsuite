package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryDetail(
    val territoryStreets: List<TerritoryStreet> = emptyList(),
    val houses: List<House> = emptyList(),
    val entrances: List<Entrance> = emptyList(),
    val floors: List<Floor> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel()
