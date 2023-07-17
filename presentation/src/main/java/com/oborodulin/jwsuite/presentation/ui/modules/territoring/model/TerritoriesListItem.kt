package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import java.util.UUID

data class TerritoriesListItem(
    val id: UUID,
    val congregation: CongregationUi,
    val territoryCategory: TerritoryCategoryUi,
    val locality: LocalityUi,
    val localityDistrictId: UUID? = null,
    val districtShortName: String? = null,
    val microdistrictId: UUID? = null,
    val microdistrictShortName: String? = null,
    val territoryNum: Int,
    val isPrivateSector: Boolean,
    val isBusiness: Boolean,
    val isGroupMinistry: Boolean,
    val isInPerimeter: Boolean,
    val isProcessed: Boolean,
    val isActive: Boolean,
    val territoryDesc: String? = null
) : ListItemModel(
    itemId = id,
    headline = "${congregation.territoryMark}${territoryCategory.territoryCategoryMark}$territoryNum",
    supportingText = territoryDesc
)
