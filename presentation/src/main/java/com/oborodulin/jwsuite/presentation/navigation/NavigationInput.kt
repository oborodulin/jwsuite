package com.oborodulin.jwsuite.presentation.navigation

import java.util.UUID

sealed class NavigationInput(val id: UUID) {
    // Geo:
    data class RegionInput(val regionId: UUID) : NavigationInput(regionId)
    data class RegionDistrictInput(val regionDistrictId: UUID) : NavigationInput(regionDistrictId)
    data class LocalityInput(val localityId: UUID) : NavigationInput(localityId)

    // Congregation:
    data class CongregationInput(val congregationId: UUID) : NavigationInput(congregationId)
    data class GroupInput(val groupId: UUID) : NavigationInput(groupId)
    data class MemberInput(val memberId: UUID) : NavigationInput(memberId)

    // Territory:
    data class TerritoryInput(val territoryId: UUID) : NavigationInput(territoryId)
}


