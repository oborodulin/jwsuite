package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import java.time.OffsetDateTime
import java.util.UUID

data class CongregationUi(
    val congregationNum: String = "",
    val congregationName: String = "",
    val territoryMark: String = "",
    val isFavorite: Boolean = false,
    val lastVisitDate: OffsetDateTime? = null,
    val locality: LocalityUi = LocalityUi()
) : ModelUi()

fun CongregationUi.toCongregationsListItem() = CongregationsListItem(
    id = this.id ?: UUID.randomUUID(),
    congregationName = this.congregationName,
    congregationNum = this.congregationNum,
    territoryMark = this.territoryMark,
    isFavorite = this.isFavorite,
    locality = this.locality
)

fun ListItemModel?.toCongregationUi() =
    CongregationUi(congregationName = this?.headline.orEmpty()).also { it.id = this?.itemId }