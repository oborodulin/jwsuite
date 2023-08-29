package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryStreetWithTerritoryAndStreets(
    val territoryStreet: TerritoryStreet,
    val territory: Territory,
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()