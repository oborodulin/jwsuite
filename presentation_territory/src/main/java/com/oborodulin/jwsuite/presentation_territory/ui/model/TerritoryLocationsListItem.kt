package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import java.util.UUID

data class TerritoryLocationsListItem(
    val locationId: UUID?,
    val locationShortName: String,
    val territoryLocationType: TerritoryLocationType,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null
) : Parcelable, ListItemModel(
    itemId = locationId,
    headline = locationShortName,
    supportingText = territoryLocationType.name
)
