package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class EntrancesListItem(
    val id: UUID,
    val entranceNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val entranceDesc: String? = null,
    val houseFullNum: String,
    val entranceFullNum: String,
    val territoryFullCardNum: String? = null,
    val info: List<String> = emptyList()
) : Parcelable, ListItemModel(
    itemId = id,
    headline = entranceFullNum,
    supportingText = territoryFullCardNum.orEmpty()
        .plus(if (info.isNotEmpty()) info.joinToString(", ") else "")
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf("$houseFullNum$entranceNum", "$houseFullNum $entranceNum")
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
