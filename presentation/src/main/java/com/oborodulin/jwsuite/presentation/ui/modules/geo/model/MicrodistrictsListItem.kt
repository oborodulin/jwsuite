package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.VillageType
import java.util.UUID

data class MicrodistrictsListItem(
    val id: UUID,
    val microdistrictShortName: String,
    val microdistrictName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = microdistrictName,
    supportingText = microdistrictShortName
)
