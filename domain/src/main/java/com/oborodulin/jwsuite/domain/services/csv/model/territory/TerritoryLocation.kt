package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import java.util.UUID

data class TerritoryLocation(
    val territoryLocationType: TerritoryLocationType,
    val congregationId: UUID,
    val isPrivateSector: Boolean?,
    val locationId: UUID?,
    val locationShortName: String
) : DomainModel()
