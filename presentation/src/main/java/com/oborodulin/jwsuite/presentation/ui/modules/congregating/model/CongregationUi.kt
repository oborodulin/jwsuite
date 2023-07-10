package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi

data class CongregationUi(
    val congregationNum: String = "",
    val congregationName: String = "",
    val territoryMark: String = "",
    val isFavorite: Boolean = false,
    val locality: LocalityUi = LocalityUi()
) : ModelUi()

fun CongregationUi.toCongregationsListItem() = CongregationsListItem(
    id = this.id!!,
    congregationName = this.congregationName,
    congregationNum = this.congregationNum,
    territoryMark = this.territoryMark,
    isFavorite = this.isFavorite,
    locality = this.locality
)