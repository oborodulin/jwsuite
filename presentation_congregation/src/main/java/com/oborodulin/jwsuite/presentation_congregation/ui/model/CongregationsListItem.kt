package com.oborodulin.jwsuite.presentation_congregation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import java.util.UUID

data class CongregationsListItem(
    val id: UUID,
    val congregationName: String,
    val congregationNum: String,
    val territoryMark: String,
    val isFavorite: Boolean = false,
    val locality: LocalityUi
) : Parcelable, ListItemModel(
    itemId = id,
    headline = congregationName,
    supportingText = "№$congregationNum ${locality.localityName} [$territoryMark]"
)

fun ListItemModel.toCongregationsListItem() = CongregationsListItem(
    id = this.itemId ?: UUID.randomUUID(),
    congregationName = this.headline,
    congregationNum = this.supportingText?.substringAfter("№")?.substringBefore(" ")
        .orEmpty(),
    territoryMark = this.supportingText?.substringAfter("[")?.substringBeforeLast("]")
        .orEmpty(),
    isFavorite = false,
    locality = LocalityUi(
        localityName = this.supportingText?.substringAfter(" ")?.substringBeforeLast(" ")
            .orEmpty()
    )
)