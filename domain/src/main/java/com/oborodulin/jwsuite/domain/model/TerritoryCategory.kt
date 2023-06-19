package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class TerritoryCategory(
    val territoryCategoryCode: String,
    val territoryCategoryMark: String,
    val territoryCategoryName: String
) : DomainModel()
