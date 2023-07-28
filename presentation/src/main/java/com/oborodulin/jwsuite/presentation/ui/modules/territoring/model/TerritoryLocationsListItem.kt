package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import java.util.UUID

data class TerritoryLocationsListItem(
    val locationId: UUID?,
    val locationName: String,
    val territoryLocationType: TerritoryLocationType,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null
) : Parcelable, ListItemModel(
    itemId = locationId,
    headline = locationName,
    supportingText = territoryLocationType.name
)
