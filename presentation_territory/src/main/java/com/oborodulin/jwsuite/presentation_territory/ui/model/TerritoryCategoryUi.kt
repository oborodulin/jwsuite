package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.TerritoryCategoryType
import java.util.UUID

data class TerritoryCategoryUi(
    val territoryCategoryCode: TerritoryCategoryType = TerritoryCategoryType.HOUSES,
    val territoryCategoryMark: String = "",
    val territoryCategoryName: String = ""
) : ModelUi()

fun TerritoryCategoryUi.toTerritoryCategoriesListItem() = TerritoryCategoriesListItem(
    id = this.id ?: UUID.randomUUID(),
    territoryCategoryCode = this.territoryCategoryCode,
    territoryCategoryMark = this.territoryCategoryMark,
    territoryCategoryName = this.territoryCategoryName
)