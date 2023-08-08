package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.content.Context
import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class TerritoryDetailsListItem(
    val ctx: Context,
    val territoryStreetId: UUID?,
    val streetId: UUID,
    val streetInfo: String,
    val houseInfo: String?,
    val roomInfo: String?
) : Parcelable, ListItemModel(
    itemId = territoryStreetId ?: streetId,
    headline = streetInfo,
    supportingText = houseInfo ?: roomInfo
)
