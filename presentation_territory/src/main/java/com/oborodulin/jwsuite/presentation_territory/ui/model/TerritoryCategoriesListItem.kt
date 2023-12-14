package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.types.TerritoryCategoryType
import java.util.UUID

data class TerritoryCategoriesListItem(
    val id: UUID,
    val territoryCategoryCode: TerritoryCategoryType,
    val territoryCategoryMark: String,
    val territoryCategoryName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = territoryCategoryName,
    supportingText = "$territoryCategoryCode [$territoryCategoryMark]"
)

fun ListItemModel.toTerritoryCategoriesListItem() = TerritoryCategoriesListItem(
    id = this.itemId ?: UUID.randomUUID(),
    territoryCategoryCode = this.supportingText?.let {
        TerritoryCategoryType.valueOf(it.substringBefore(" "))
    } ?: TerritoryCategoryType.UNDEFINED,
    territoryCategoryMark = this.supportingText?.substringAfter("[")?.substringBeforeLast("]")
        .orEmpty(),
    territoryCategoryName = this.headline
)