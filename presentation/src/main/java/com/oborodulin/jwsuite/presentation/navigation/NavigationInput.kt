package com.oborodulin.jwsuite.presentation.navigation

import java.util.UUID

sealed class NavigationInput(val id: UUID) {
    // Geo:
    data class RegionInput(val regionId: UUID) : NavigationInput(regionId)
    data class RegionDistrictInput(val regionDistrictId: UUID) : NavigationInput(regionDistrictId)
    data class LocalityInput(val localityId: UUID) : NavigationInput(localityId)
    data class LocalityDistrictInput(val localityDistrictId: UUID) :
        NavigationInput(localityDistrictId)

    data class MicrodistrictInput(val microdistrictId: UUID) : NavigationInput(microdistrictId)
    data class StreetInput(val streetId: UUID) : NavigationInput(streetId)
    data class StreetLocalityDistrictInput(val streetId: UUID) : NavigationInput(streetId)

    data class StreetMicrodistrictInput(val streetId: UUID) : NavigationInput(streetId)

    // Congregation:
    data class CongregationInput(val congregationId: UUID) : NavigationInput(congregationId)
    data class GroupInput(val groupId: UUID) : NavigationInput(groupId)
    data class MemberInput(val memberId: UUID) : NavigationInput(memberId)

    // Territory:
    data class TerritoryCategoryInput(val territoryCategoryId: UUID) :
        NavigationInput(territoryCategoryId)

    data class TerritoryInput(val territoryId: UUID) : NavigationInput(territoryId)

    // Territory Details:
    data class TerritoryStreetInput(val territoryId: UUID, val territoryStreetId: UUID? = null) :
        NavigationInput(territoryStreetId ?: territoryId)

    data class TerritoryHouseInput(val territoryId: UUID, val houseId: UUID? = null) :
        NavigationInput(houseId ?: territoryId)

    data class TerritoryEntranceInput(val territoryId: UUID, val entranceId: UUID? = null) :
        NavigationInput(entranceId ?: territoryId)

    data class TerritoryFloorInput(val territoryId: UUID, val floorId: UUID? = null) :
        NavigationInput(floorId ?: territoryId)

    data class TerritoryRoomInput(val territoryId: UUID, val roomId: UUID? = null) :
        NavigationInput(roomId ?: territoryId)

    data class HouseInput(val houseId: UUID) : NavigationInput(houseId)
    data class EntranceInput(val entranceId: UUID) : NavigationInput(entranceId)
    data class FloorInput(val floorId: UUID) : NavigationInput(floorId)
    data class RoomInput(val roomId: UUID) : NavigationInput(roomId)
}


