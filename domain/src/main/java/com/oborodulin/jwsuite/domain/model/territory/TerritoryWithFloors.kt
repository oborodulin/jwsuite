package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryWithFloors(
    val territory: Territory,
    val floors: List<Floor> = emptyList()
) : DomainModel()