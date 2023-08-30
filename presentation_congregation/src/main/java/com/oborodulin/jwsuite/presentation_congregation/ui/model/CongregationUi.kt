package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_congregation.ui.geo.model.LocalityUi

data class CongregationUi(
    val congregationNum: String = "",
    val congregationName: String = "",
    val territoryMark: String = "",
    val isFavorite: Boolean = false,
    val locality: com.oborodulin.jwsuite.presentation_congregation.ui.geo.model.LocalityUi = com.oborodulin.jwsuite.presentation_congregation.ui.geo.model.LocalityUi()
) : ModelUi()

fun CongregationUi.toCongregationsListItem() = CongregationsListItem(
    id = this.id!!,
    congregationName = this.congregationName,
    congregationNum = this.congregationNum,
    territoryMark = this.territoryMark,
    isFavorite = this.isFavorite,
    locality = this.locality
)