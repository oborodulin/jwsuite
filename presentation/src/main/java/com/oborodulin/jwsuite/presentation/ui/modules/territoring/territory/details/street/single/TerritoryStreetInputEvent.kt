package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoryStreetInputEvent(val value: String) : Inputable {
    data class Region(val input: ListItemModel) : TerritoryStreetInputEvent(input.headline)
    data class RegionDistrict(val input: ListItemModel) : TerritoryStreetInputEvent(input.headline)
    data class TerritoryStreetCode(val input: String) : TerritoryStreetInputEvent(input)
    data class TerritoryStreetShortName(val input: String) : TerritoryStreetInputEvent(input)
    data class TerritoryStreetType(val input: String) : TerritoryStreetInputEvent(input)
    data class TerritoryStreetName(val input: String) : TerritoryStreetInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
