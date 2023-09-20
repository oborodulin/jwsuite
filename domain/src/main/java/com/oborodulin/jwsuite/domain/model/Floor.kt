package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class Floor(
    val house: House,
    val entrance: Entrance?,
    val territory: Territory?,
    val floorNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val territoryDesc: String? = null,
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    val floorFullNum =
        "${house.street.streetFullName}, ${house.houseExpr} ${house.houseFullNum}, $floorNum"

    var calculatedRooms = when (estimatedRooms) {
        null -> roomsByFloor ?: 0
        else -> estimatedRooms
    }
    val existingRooms: Int
        get() = rooms.size
}
