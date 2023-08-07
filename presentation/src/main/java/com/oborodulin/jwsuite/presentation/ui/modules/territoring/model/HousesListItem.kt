package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.BuildingType
import java.util.UUID

data class HousesListItem(
    val id: UUID,
    val zipCode: String? = null,
    val houseNum: Int,
    val buildingNum: String? = null,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val calculatedRooms: Int? = null,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val houseDesc: String? = null,
) : Parcelable, ListItemModel(
    itemId = id,
    headline = "$houseNum".plus(if (buildingNum != null) "-$buildingNum" else "")
        .plus(if (calculatedRooms != null) " ($calculatedRooms кв.)" else ""),
    supportingText = (if (zipCode != null) "$zipCode: " else "").plus("$houseDesc")
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$houseNum$buildingNum",
            "$houseNum $buildingNum"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
