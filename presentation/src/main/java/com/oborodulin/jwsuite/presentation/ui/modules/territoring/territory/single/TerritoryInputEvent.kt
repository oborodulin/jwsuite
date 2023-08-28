package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoriesListItem

sealed class TerritoryInputEvent(val value: String) : Inputable {
    data class Congregation(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class Category(val input: TerritoryCategoriesListItem) :
        TerritoryInputEvent(input.headline)

    data class Locality(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class LocalityDistrict(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class Microdistrict(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class TerritoryNum(val input: Int) : TerritoryInputEvent(input.toString())
    data class IsPrivateSector(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class IsBusiness(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class IsGroupMinistry(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class IsActive(val input: Boolean) : TerritoryInputEvent(input.toString())
    data class TerritoryDesc(val input: String) : TerritoryInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
