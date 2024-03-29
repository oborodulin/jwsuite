package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryWithEntrances(
    val territory: Territory,
    val entrances: List<Entrance> = emptyList()
) : DomainModel()