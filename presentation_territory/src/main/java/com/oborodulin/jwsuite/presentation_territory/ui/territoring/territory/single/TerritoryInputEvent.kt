package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem

sealed class TerritoryInputEvent(val value: String) : Inputable {
    data class Congregation(val input: CongregationsListItem) : TerritoryInputEvent(input.headline)
    data class Category(val input: TerritoryCategoriesListItem) :
        TerritoryInputEvent(input.headline)

    data class Locality(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class LocalityDistrict(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class Microdistrict(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class TerritoryNum(val input: Int?) : TerritoryInputEvent(input?.toString().orEmpty())
    data class IsBusiness(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class IsGroupMinistry(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class IsActive(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class TerritoryDesc(val input: String) : TerritoryInputEvent(input)

    override fun value() = this.value
}
