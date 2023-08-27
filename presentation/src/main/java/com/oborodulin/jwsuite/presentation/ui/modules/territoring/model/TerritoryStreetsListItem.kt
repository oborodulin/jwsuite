package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class TerritoryStreetsListItem(
    val id: UUID,
    val streetId: UUID,
    val streetFullName: String,
    val info: List<String> = emptyList()
) : Parcelable, ListItemModel(
    itemId = id,
    headline = streetFullName,
    supportingText = info.joinToString(", ")
)
