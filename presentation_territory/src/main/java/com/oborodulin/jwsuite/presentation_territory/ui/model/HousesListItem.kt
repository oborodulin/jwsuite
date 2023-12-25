package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.types.BuildingType
import java.util.UUID

data class HousesListItem(
    val id: UUID,
    val zipCode: String? = null,
    val houseNum: Int,
    val houseFullNum: String,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val houseExpr: String? = null,
    val streetFullName: String,
    val territoryFullCardNum: String? = null,
    val info: List<String> = emptyList()
) : Parcelable, ListItemModel(
    itemId = id,
    headline = houseFullNum, // "$streetFullName, $houseExpr $houseFullNum"
    supportingText = territoryFullCardNum.orEmpty().plus(zipCode?.let { "($it) " }.orEmpty())
        .plus(if (info.isNotEmpty()) info.joinToString(", ") else "")
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$houseFullNum${info.joinToString("")}",
            "$houseFullNum ${info.joinToString(" ")}"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}

fun ListItemModel.toHousesListItem() = HousesListItem(
    id = this.itemId ?: UUID.randomUUID(),
    houseNum = this.headline.let { s -> s.substring(0, s.indexOf(s.first { it.isLetter() })) }
        .toInt(),
    houseFullNum = this.headline,
    streetFullName = ""
)