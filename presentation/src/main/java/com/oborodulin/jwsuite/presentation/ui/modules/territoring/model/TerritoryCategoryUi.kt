package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryCategoryUi(
    val territoryCategoryCode: String = "",
    val territoryCategoryMark: String = "",
    val territoryCategoryName: String = ""
) : ModelUi()

fun TerritoryCategoryUi.toTerritoryCategoriesListItem() = TerritoryCategoriesListItem(
    id = this.id!!,
    territoryCategoryCode = this.territoryCategoryCode,
    territoryCategoryMark = this.territoryCategoryMark,
    territoryCategoryName = this.territoryCategoryName
)