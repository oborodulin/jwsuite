package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class FloorsListItem(
    val id: UUID,
    val floorNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val floorDesc: String? = null,
    val houseFullNum: String,
    val floorFullNum: String,
    val territoryFullCardNum: String? = null,
    val info: List<String> = emptyList()
) : Parcelable, ListItemModel(
    itemId = id,
    headline = floorFullNum,
    supportingText = territoryFullCardNum.orEmpty()
        .plus(if (info.isNotEmpty()) info.joinToString(", ") else "")
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf("$houseFullNum$floorNum", "$houseFullNum $floorNum")
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
