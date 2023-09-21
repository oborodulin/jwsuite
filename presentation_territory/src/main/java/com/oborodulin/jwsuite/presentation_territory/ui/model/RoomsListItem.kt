package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class RoomsListItem(
    val id: UUID,
    val roomNum: Int,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val houseFullNum: String,
    val roomFullNum: String,
    val territoryFullCardNum: String? = null,
    val info: List<String> = emptyList()
) : Parcelable, ListItemModel(
    itemId = id,
    headline = roomFullNum,
    supportingText = territoryFullCardNum.orEmpty()
        .plus(if (info.isNotEmpty()) info.joinToString(", ") else "")
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf("$houseFullNum$roomNum", "$houseFullNum $roomNum")
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
