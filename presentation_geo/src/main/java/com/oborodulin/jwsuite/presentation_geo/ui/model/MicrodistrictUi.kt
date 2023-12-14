package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.VillageType

data class MicrodistrictUi(
    val locality: LocalityUi = LocalityUi(),
    val localityDistrict: LocalityDistrictUi = LocalityDistrictUi(),
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String = "",
    val microdistrictName: String = "",

    val microdistrictFullName: String = ""
) : ModelUi()

fun MicrodistrictUi.toMicrodistrictsListItem() = MicrodistrictsListItem(
    id = this.id!!,
    microdistrictShortName = this.microdistrictShortName,
    microdistrictFullName = this.microdistrictFullName
)