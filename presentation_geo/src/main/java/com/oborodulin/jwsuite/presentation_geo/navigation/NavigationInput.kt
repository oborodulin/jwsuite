package com.oborodulin.jwsuite.presentation_geo.navigation

import java.util.UUID

sealed class NavigationInput(val id: UUID) {
    data class RegionInput(val regionId: UUID) : NavigationInput(regionId)
    data class RegionDistrictInput(val regionDistrictId: UUID) : NavigationInput(regionDistrictId)
    data class LocalityInput(val localityId: UUID) : NavigationInput(localityId)
    data class LocalityDistrictInput(val localityDistrictId: UUID) :
        NavigationInput(localityDistrictId)

    data class MicrodistrictInput(val microdistrictId: UUID) : NavigationInput(microdistrictId)
    data class StreetInput(val streetId: UUID) : NavigationInput(streetId)
}


