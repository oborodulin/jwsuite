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
    headline = "$houseExpr $houseFullNum", // "$streetFullName, $houseExpr $houseFullNum"
    supportingText = streetFullName.plus(zipCode?.let { ", $it" }.orEmpty())
        .plus(if (info.isNotEmpty()) "\n${info.joinToString(", ")} " else "")
        .plus(territoryFullCardNum?.let { "[$it]" }.orEmpty())
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$houseFullNum$streetFullName${info.joinToString("")}",
            "$houseFullNum $streetFullName ${info.joinToString(" ")}"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}

fun ListItemModel.toHousesListItem() = HousesListItem(
    id = this.itemId ?: UUID.randomUUID(),
    houseNum = this.headline.let { s ->
        val cs = s.substringAfter(' ')
        try {
            cs.substringBefore(cs.first { it.isLetter() || it == '-' })
        } catch (e: NoSuchElementException) {
            cs
        }
    }.toInt(),
    houseFullNum = this.headline,
    streetFullName = this.supportingText?.let { s ->
        try {
            s.substringBefore(s.first { it == ',' || it == '\n' })
        } catch (e: NoSuchElementException) {
            s
        }
    }.orEmpty()
)