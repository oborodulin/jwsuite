package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class House(
    val streetId: UUID,
    val localityDistrictId: UUID? = null,
    val microdistrictId: UUID? = null,
    val territoryId: UUID? = null,
    val zipCode: String? = null,
    val houseNum: Int,
    val buildingNum: String? = null,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val entrancesQty: Int? = null,
    val floorsQty: Int? = null,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val isHostel: Boolean = false,
    val territoryDesc: String? = null,
    val entrances: List<Entrance> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    var calculatedRooms = when (estimatedRooms) {
        null -> (entrancesQty ?: 0) * (floorsQty ?: 0) * (roomsByFloor ?: 0)
        else -> estimatedRooms
    }
}
