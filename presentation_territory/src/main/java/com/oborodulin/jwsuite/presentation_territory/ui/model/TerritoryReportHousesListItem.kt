package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class TerritoryReportHousesListItem(
    val id: UUID,
    val houseNum: Int,
    val houseFullNum: String,
    val streetFullName: String,
    val territoryMemberId: UUID,
    val territoryShortMark: String? = null,
    val languageCode: String? = null,
    val genderInfo: String? = null,
    val ageInfo: String? = null,
    val isProcessed: Boolean = false
) : Parcelable, ListItemModel(
    itemId = id,
    headline = houseFullNum,
    supportingText = streetFullName
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "${territoryShortMark.orEmpty()}$houseFullNum$streetFullName",
            "${territoryShortMark.orEmpty()} $houseFullNum $streetFullName"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
/*
fun ListItemModel.toTerritoryHouseReportsListItem() = TerritoryHouseReportsListItem(
    id = this.itemId ?: UUID.randomUUID(),
    houseNum = this.headline.let { s ->
        try {
            s.substringBefore(s.first { it.isLetter() || it == '-' })
        } catch (e: NoSuchElementException) {
            s
        }
    }.toInt(),
    houseFullNum = this.headline,
    streetFullName = this.supportingText.orEmpty()
)*/