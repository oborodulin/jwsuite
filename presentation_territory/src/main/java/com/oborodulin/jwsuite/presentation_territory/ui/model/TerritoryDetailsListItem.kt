package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class TerritoryDetailsListItem(
    val territoryStreetId: UUID?,
    val streetId: UUID,
    val streetInfo: String,
    val housesInfo: String? = null,
    val entrancesInfo: String? = null,
    val roomsInfo: String? = null
) : Parcelable, ListItemModel(
    itemId = territoryStreetId ?: streetId,
    headline = streetInfo,
    supportingText = housesInfo ?: entrancesInfo ?: roomsInfo
)
