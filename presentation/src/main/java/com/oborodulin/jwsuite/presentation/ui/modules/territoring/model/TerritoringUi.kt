package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

data class TerritoringUi(
    val isPrivateSector: Boolean = false,
    val territoryLocations: List<TerritoryLocationsListItem> = emptyList()
)
