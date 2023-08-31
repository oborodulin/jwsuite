package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_territory.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.LocalityUi
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.MicrodistrictUi

data class TerritoryUi(
    val congregation: CongregationUi = CongregationUi(),
    val territoryCategory: TerritoryCategoryUi = TerritoryCategoryUi(),
    val locality: LocalityUi = LocalityUi(),
    val localityDistrict: LocalityDistrictUi? = null,
    val microdistrict: MicrodistrictUi? = null,
    val territoryNum: Int = 0,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val isActive: Boolean = true,
    val territoryDesc: String? = null
) : ModelUi()