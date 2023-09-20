package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class EntrancesListItem(
    val id: UUID,
    val territory: TerritoryUi?,
    val roomNum: Int,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val territoryDesc: String? = null,
    val fullRoomNum: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = fullRoomNum,
    supportingText = territory?.let { "${it.fullCardNum}: " }.orEmpty()
        .plus(territoryDesc.orEmpty())
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf("$roomNum")
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
