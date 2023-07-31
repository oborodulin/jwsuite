package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import java.util.UUID

data class TerritoryLocation(
    val territoryLocationType: TerritoryLocationType,
    val congregationId: UUID,
    val isPrivateSector: Boolean?,
    val locationId: UUID?,
    val locationShortName: String
) : DomainModel()
