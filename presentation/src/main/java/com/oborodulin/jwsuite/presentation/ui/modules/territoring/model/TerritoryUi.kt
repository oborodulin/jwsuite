package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import java.util.UUID

data class TerritoryUi(
    val congregation: CongregationUi = CongregationUi(),
    val territoryCategory: TerritoryCategoryUi = TerritoryCategoryUi(),
    val locality: LocalityUi = LocalityUi(),
    val localityDistrictId: UUID? = null,
    val districtShortName: String? = null,
    val microdistrictId: UUID? = null,
    val microdistrictShortName: String? = null,
    val territoryNum: Int,
    val isPrivateSector: Boolean = false,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val isActive: Boolean = true,
    val territoryDesc: String? = null
) : ModelUi()

fun TerritoryUi.toTerritoriesListItem() = TerritoriesListItem(
    id = this.id!!,
    congregation = this.congregation,
    territoryCategory = this.territoryCategory,
    locality = this.locality,
    cardNum = "${this.congregation.territoryMark}${this.territoryCategory.territoryCategoryMark}".plus(
        "-${this.territoryNum}"
    ),
    cardLocation = "[".plus(if (this.locality.id != this.congregation.locality.id) "${this.locality.localityShortName}:" else "")
        .plus(if (!this.districtShortName.isNullOrEmpty()) "${this.districtShortName}:" else "")
        .plus(if (!this.microdistrictShortName.isNullOrEmpty()) "${this.microdistrictShortName}]" else "]")
        .replace("[]", "")
        .replace(":]", "]"),
    territoryNum = this.territoryNum,
    isPrivateSector = this.isPrivateSector,
    isBusiness = this.isBusiness,
    isGroupMinistry = this.isGroupMinistry,
    isInPerimeter = this.isInPerimeter,
    isProcessed = this.isProcessed,
    isActive = this.isActive,
    territoryDesc = this.territoryDesc
)