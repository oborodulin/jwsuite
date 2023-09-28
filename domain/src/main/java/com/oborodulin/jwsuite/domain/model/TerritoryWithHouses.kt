package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryWithHouses(
    val territory: Territory,
    val houses: List<House> = emptyList()
) : DomainModel()