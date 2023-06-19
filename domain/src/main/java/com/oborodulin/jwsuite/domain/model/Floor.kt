package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class Floor(
    val houseId: UUID,
    val entranceId: UUID? = null,
    val territoryId: UUID? = null,
    val floorNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val territoryDesc: String? = null,
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    var calculatedRooms = when (estimatedRooms) {
        null -> roomsByFloor ?: 0
        else -> estimatedRooms
    }
    val existingRooms: Int
        get() {
            return rooms.size
        }
}
