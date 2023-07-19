package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.TerritoryDistrictType
import java.util.UUID

data class TerritoryDistrict(
    val territoryDistrictType: TerritoryDistrictType,
    val congregationId: UUID,
    val isPrivateSector: Boolean?,
    val districtId: UUID?,
    val districtName: String
) : DomainModel()
