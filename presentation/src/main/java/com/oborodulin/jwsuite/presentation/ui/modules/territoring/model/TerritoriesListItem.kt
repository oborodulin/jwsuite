package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi
import java.util.UUID

data class TerritoriesListItem(
    val id: UUID,
    val congregation: CongregationUi,
    val territoryCategory: TerritoryCategoryUi,
    val locality: com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityUi,
    val cardNum: String,
    val cardLocation: String,
    val territoryNum: Int,
    val isBusiness: Boolean,
    val isGroupMinistry: Boolean,
    val isInPerimeter: Boolean,
    val isProcessed: Boolean,
    val isActive: Boolean,
    val territoryDesc: String? = null,
    val streetNames: String? = null,
    val houseNums: String? = null,
    val member: MemberUi? = null,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null,
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
