package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class StreetInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : StreetInputEvent(input.headline)
    //data class LocalityDistrict(val input: ListItemModel) : StreetInputEvent(input.headline)
    //data class Microdistrict(val input: ListItemModel) : StreetInputEvent(input.headline)
    data class RoadType(val input: String) : StreetInputEvent(input)
    data class IsPrivateSector(val input: Boolean) : StreetInputEvent(input.toString())
    data class EstimatedHouses(val input: Int?) : StreetInputEvent(input?.toString().orEmpty())
    data class StreetName(val input: String) : StreetInputEvent(input)

    override fun value() = this.value
}
