package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryWithFloors(
    val territory: Territory,
    val floors: List<Floor> = emptyList()
) : DomainModel()