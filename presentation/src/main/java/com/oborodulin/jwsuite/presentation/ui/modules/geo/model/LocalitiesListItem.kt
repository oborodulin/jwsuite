package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.LocalityType
import java.util.UUID

data class LocalitiesListItem(
    val id: UUID,
    val localityCode: String,
    val localityType: LocalityType,
    val localityShortName: String,
    val localityName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = localityName,
    supportingText = "$localityCode: $localityShortName"
)
