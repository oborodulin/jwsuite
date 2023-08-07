package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import java.util.UUID

data class TerritoryDetailsListItem(
    val locationId: UUID?,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val housesQty: Int? = null,
    val streetName: String,
    val isEven: Boolean? = null,
) : Parcelable, ListItemModel(
    itemId = locationId,
    headline = locationShortName,
    supportingText = territoryLocationType.name
)
