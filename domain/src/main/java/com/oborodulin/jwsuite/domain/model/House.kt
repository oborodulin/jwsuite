package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.BuildingType

data class House(
    val street: GeoStreet,
    val localityDistrict: GeoLocalityDistrict?,
    val microdistrict: GeoMicrodistrict?,
    val territory: Territory?,
    val zipCode: String? = null,
    val houseNum: Int,
    val buildingNum: String? = null,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val houseEntrancesQty: Int? = null,
    val floorsByEntrance: Int? = null,
    val roomsByHouseFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val houseDesc: String? = null,
    val entrances: List<Entrance> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    var calculatedRooms = when (estimatedRooms) {
        null -> (houseEntrancesQty ?: 0) * (floorsByEntrance ?: 0) * (roomsByHouseFloor ?: 0)
        else -> estimatedRooms
    }
}
