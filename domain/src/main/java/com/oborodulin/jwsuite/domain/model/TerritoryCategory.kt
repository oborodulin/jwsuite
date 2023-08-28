package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.TerritoryCategoryType

data class TerritoryCategory(
    val territoryCategoryCode: TerritoryCategoryType,
    val territoryCategoryMark: String,
    val territoryCategoryName: String
) : DomainModel()
