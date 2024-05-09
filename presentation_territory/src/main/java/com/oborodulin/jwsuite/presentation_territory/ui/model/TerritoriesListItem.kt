package com.oborodulin.jwsuite.presentation_territory.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import kotlinx.parcelize.RawValue
import java.util.UUID

data class TerritoriesListItem(
    val id: UUID,
    val congregation: @RawValue CongregationUi,
    val territoryCategory: @RawValue TerritoryCategoryUi,
    val locality: @RawValue LocalityUi,
    val territoryNum: Int,
    val isBusiness: Boolean,
    val isGroupMinistry: Boolean,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean,
    val isActive: Boolean,
    val territoryDesc: String? = null,
    val streetNames: String? = null,
    val houseNums: String? = null,
    val member: @RawValue MemberUi? = null,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null,
    val cardNum: String,
    val cardLocation: String,
    val fullCardNum: String,
    val handOutDaysPeriod: String? = null,
    val expiredDaysPeriod: String? = null
) : Parcelable, ListItemModel(
    itemId = id, headline = "$cardNum $cardLocation", supportingText = territoryDesc
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$cardNum$cardLocation${member?.memberFullName.orEmpty()}${territoryDesc.orEmpty()}${streetNames.orEmpty()}${houseNums.orEmpty()}",
            "$cardNum $cardLocation ${member?.memberFullName.orEmpty()} ${territoryDesc.orEmpty()} ${streetNames.orEmpty()} ${houseNums.orEmpty()}".trim(),
            "$cardNum$cardLocation${member?.memberShortName.orEmpty()}${territoryDesc.orEmpty()}${streetNames.orEmpty()}${houseNums.orEmpty()}",
            "$cardNum $cardLocation ${member?.memberShortName.orEmpty()} ${territoryDesc.orEmpty()} ${streetNames.orEmpty()} ${houseNums.orEmpty()}".trim()
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
