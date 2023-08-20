package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class StreetsListItem(
    val id: UUID,
    val isPrivateSector: Boolean,
    val estimatedHouses: Int? = null,
    val streetName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = streetName,
    supportingText = isPrivateSector.toString()
)