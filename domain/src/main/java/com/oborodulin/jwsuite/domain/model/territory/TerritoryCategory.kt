package com.oborodulin.jwsuite.domain.model.territory

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.types.TerritoryCategoryType

data class TerritoryCategory(
    val territoryCategoryCode: TerritoryCategoryType,
    val territoryCategoryMark: String,
    val territoryCategoryName: String
) : DomainModel()
