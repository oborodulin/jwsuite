package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class TerritoryReportRoomsListItem(
    val id: UUID,
    val roomNum: Int,
    val houseFullNum: String,
    val streetFullName: String,
    val territoryMemberReportId: UUID? = null,
    val territoryMemberId: UUID,
    val territoryShortMark: String? = null,
    val languageInfo: String? = null,
    val personInfo: String? = null,
    val isProcessed: Boolean? = null
) : Parcelable, ListItemModel(
    itemId = id,
    headline = roomNum.toString(),
    supportingText = "$streetFullName, $houseFullNum"
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "${territoryShortMark.orEmpty()}$roomNum$houseFullNum$streetFullName",
            "${territoryShortMark.orEmpty()} $roomNum $houseFullNum $streetFullName"
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