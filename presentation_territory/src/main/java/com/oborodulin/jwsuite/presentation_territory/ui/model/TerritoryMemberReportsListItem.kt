package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

data class TerritoryMemberReportsListItem(
    val id: UUID,
    val deliveryDate: OffsetDateTime? = null,
    val memberShortName: String,
    val territoryShortMark: String,
    val languageInfo: String? = null,
    val personInfo: String? = null,
    val territoryReportDesc: String? = null
) : Parcelable, ListItemModel(
    itemId = id,
    headline = territoryShortMark.plus(personInfo?.let { ": $it" }.orEmpty())
        .plus(languageInfo?.let { " $it" }.orEmpty()),
    supportingText = deliveryDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
        .orEmpty().plus(if (memberShortName.isNotEmpty()) ": $memberShortName" else "")
        .plus(territoryReportDesc?.let { "\n$it" }.orEmpty())
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(territoryShortMark)
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