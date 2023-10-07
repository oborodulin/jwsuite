package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet

data class TerritoryStreetWithTerritoryAndStreets(
    val territoryStreet: TerritoryStreet? = null,
    val territory: Territory,
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()