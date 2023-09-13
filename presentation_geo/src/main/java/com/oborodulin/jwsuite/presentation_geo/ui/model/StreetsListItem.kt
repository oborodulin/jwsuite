package com.oborodulin.jwsuite.presentation_geo.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class StreetsListItem(
    val id: UUID,
    val isPrivateSector: Boolean,
    val estimatedHouses: Int? = null,
    val streetFullName: String,
    val isPrivateSectorInfo: String? = null,
    val estHousesInfo: String? = null
) : Parcelable, ListItemModel(
    itemId = id,
    headline = streetFullName,
    supportingText = "${
        isPrivateSectorInfo?.let { "$it: " }.orEmpty()
    } ${estHousesInfo.orEmpty()}".trim()
)

fun ListItemModel.toStreetsListItem() = StreetsListItem(
    id = this.itemId ?: UUID.randomUUID(),
    isPrivateSector = false,
    streetFullName = this.headline
)