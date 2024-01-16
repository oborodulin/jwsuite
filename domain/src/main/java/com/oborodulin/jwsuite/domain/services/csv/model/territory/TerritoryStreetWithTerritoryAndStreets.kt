package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv

data class TerritoryStreetWithTerritoryAndStreets(
    val territoryStreet: TerritoryStreet? = null,
    val territory: Territory,
    val streets: List<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv> = emptyList()
) : DomainModel()