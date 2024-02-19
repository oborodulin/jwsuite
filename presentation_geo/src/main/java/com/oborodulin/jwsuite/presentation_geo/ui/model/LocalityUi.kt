package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toLocalityName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.LocalityType
import java.util.UUID

data class LocalityUi(
    val region: RegionUi = RegionUi(),
    val regionDistrict: RegionDistrictUi? = null,
    val localityCode: String = "",
    val localityType: LocalityType = LocalityType.CITY,
    val localityShortName: String = "",
    val localityName: String = "",

    val localityFullName: String = ""
) : ModelUi()

fun LocalityUi.toListItemModel() = ListItemModel(
    itemId = this.id ?: UUID.randomUUID(),
    headline = this.localityFullName,
    supportingText = "${this.localityCode}: ${this.localityShortName}"
)

fun ListItemModel?.toLocalityUi() =
    LocalityUi(localityName = this?.headline.toLocalityName()).also { it.id = this?.itemId }