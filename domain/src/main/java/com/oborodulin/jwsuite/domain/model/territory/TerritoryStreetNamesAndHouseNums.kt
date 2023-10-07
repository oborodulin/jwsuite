package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class TerritoryStreetNamesAndHouseNums(
    val territoryId: UUID,
    val streetNames: String,
    val houseFullNums: String?
) : DomainModel()
