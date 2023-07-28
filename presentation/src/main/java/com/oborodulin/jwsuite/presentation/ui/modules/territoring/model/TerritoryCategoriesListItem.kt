package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class TerritoryCategoriesListItem(
    val id: UUID,
    val territoryCategoryCode: String,
    val territoryCategoryMark: String,
    val territoryCategoryName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = territoryCategoryName,
    supportingText = "$territoryCategoryCode [$territoryCategoryMark]"
)
