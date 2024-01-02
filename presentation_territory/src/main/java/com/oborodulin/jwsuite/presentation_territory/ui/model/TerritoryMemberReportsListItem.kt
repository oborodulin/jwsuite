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
    val territoryShortMark: String? = null,
    val languageCode: String? = null,
    val genderInfo: String? = null,
    val ageInfo: String? = null
) : Parcelable, ListItemModel(
    itemId = id,
    headline = (territoryShortMark?.let { "$it:" }.orEmpty()
        .plus(genderInfo?.let { " $it" }.orEmpty())
        .plus(ageInfo?.let { " $it" }.orEmpty())
        .plus(languageCode?.let { " [$it]" }.orEmpty())).trim(),
    supportingText = deliveryDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(territoryShortMark.orEmpty())
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