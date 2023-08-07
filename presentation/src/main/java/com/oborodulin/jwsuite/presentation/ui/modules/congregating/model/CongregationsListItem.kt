package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityUi
import java.util.UUID

data class CongregationsListItem(
    val id: UUID,
    val congregationName: String,
    val congregationNum: String,
    val territoryMark: String,
    val isFavorite: Boolean = false,
    val locality: com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityUi
) : Parcelable, ListItemModel(
    itemId = id,
    headline = congregationName,
    supportingText = "№$congregationNum ${locality.localityName} [$territoryMark]"
)
