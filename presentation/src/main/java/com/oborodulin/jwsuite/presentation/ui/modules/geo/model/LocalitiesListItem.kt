package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class LocalitiesListItem(
    val id: UUID,
    val localityCode: String,
    val localityShortName: String,
    val localityFullName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = localityFullName,
    supportingText = "$localityCode: $localityShortName"
)
