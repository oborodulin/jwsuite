package com.oborodulin.jwsuite.presentation_territory.ui.model

data class TerritoringUi(
    val isPrivateSector: Boolean = false,
    val territoryLocations: List<TerritoryLocationsListItem> = emptyList()
)
