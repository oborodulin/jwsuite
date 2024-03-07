package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toRegionName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.RegionType
import java.util.UUID

data class RegionUi(
    val country: CountryUi? = null,
    val regionCode: String = "",
    val regionType: RegionType = RegionType.REGION,
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val coordinates: CoordinatesUi = CoordinatesUi(),
    val regionName: String = "",
    val regionFullName: String = ""
) : ModelUi()

fun RegionUi?.toListItemModel() = ListItemModel(
    itemId = this?.id ?: UUID.randomUUID(),
    headline = this?.regionFullName.orEmpty(),
    supportingText = this?.regionCode
)

fun ListItemModel?.toRegionUi() =
    RegionUi(regionName = this?.headline.toRegionName()).also { it.id = this?.itemId }