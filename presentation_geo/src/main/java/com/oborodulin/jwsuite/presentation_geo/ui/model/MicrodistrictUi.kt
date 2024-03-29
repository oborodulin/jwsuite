package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.MicrodistrictType

data class MicrodistrictUi(
    val locality: LocalityUi? = null,
    val localityDistrict: LocalityDistrictUi? = null,
    val microdistrictType: MicrodistrictType = MicrodistrictType.SUBURB,
    val microdistrictShortName: String = "",
    val microdistrictName: String = "",

    val microdistrictFullName: String = ""
) : ModelUi()

fun MicrodistrictUi.toMicrodistrictsListItem() = MicrodistrictsListItem(
    id = this.id!!,
    microdistrictShortName = this.microdistrictShortName,
    microdistrictFullName = this.microdistrictFullName
)