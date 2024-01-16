package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryWithEntrances(
    val territory: Territory,
    val entrances: List<com.oborodulin.jwsuite.domain.services.csv.model.territory.Entrance> = emptyList()
) : DomainModel()