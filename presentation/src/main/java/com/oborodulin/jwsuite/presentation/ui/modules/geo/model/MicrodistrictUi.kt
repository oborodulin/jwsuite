package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.VillageType

data class MicrodistrictUi(
    val locality: LocalityUi = LocalityUi(),
    val localityDistrict: LocalityDistrictUi = LocalityDistrictUi(),
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String = "",
    val microdistrictName: String = ""
) : ModelUi()

fun MicrodistrictUi.toMicrodistrictsListItem() = MicrodistrictsListItem(
    id = this.id!!,
    microdistrictShortName = this.microdistrictShortName,
    microdistrictFullName = this.microdistrictName
)