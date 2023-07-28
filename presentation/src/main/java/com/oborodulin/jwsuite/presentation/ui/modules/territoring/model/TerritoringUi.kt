package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

data class TerritoringUi(
    val territoryLocations: List<TerritoryLocationsListItem> = emptyList(),
    val isPrivateSector: Boolean? = null
)
