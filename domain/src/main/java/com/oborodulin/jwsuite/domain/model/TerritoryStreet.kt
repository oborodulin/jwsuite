package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class TerritoryStreet(
    val territoryId: UUID,
    val street: GeoStreet,
    val isEven: Boolean? = null,
    val isPrivateSector: Boolean? = null
) : DomainModel()
