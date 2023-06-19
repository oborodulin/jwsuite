package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class Entrance(
    val houseId: UUID,
    val territoryId: UUID? = null,
    val entranceNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val floorsQty: Int? = null,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val territoryDesc: String? = null,
    val floors: List<Floor> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    var calculatedRooms = when (estimatedRooms) {
        null -> (floorsQty ?: 0) * (roomsByFloor ?: 0)
        else -> estimatedRooms
    }
    val existingRooms: Int
        get() {
            return rooms.size
        }
}
