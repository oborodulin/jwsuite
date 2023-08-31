package com.oborodulin.jwsuite.presentation_geo.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class MicrodistrictsListItem(
    val id: UUID,
    val microdistrictShortName: String,
    val microdistrictFullName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = microdistrictFullName,
    supportingText = microdistrictShortName
)
