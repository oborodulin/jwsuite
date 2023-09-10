package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.BuildingType
import java.util.UUID

data class HousesListItem(
    val id: UUID,
    val zipCode: String? = null,
    val houseInfo: String,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val houseDesc: String? = null
) : Parcelable, ListItemModel(
    itemId = id,
    headline = houseInfo,
    supportingText = (if (zipCode != null) "$zipCode: " else "").plus(houseDesc.orEmpty())
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(houseInfo)
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
