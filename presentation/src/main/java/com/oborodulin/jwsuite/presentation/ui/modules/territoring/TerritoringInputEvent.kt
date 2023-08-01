package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem

sealed class TerritoringInputEvent(val value: String) : Inputable {
    data class IsPrivateSector(val input: Boolean) : TerritoringInputEvent(input.toString())
    data class Location(val input: TerritoryLocationsListItem) :
        TerritoringInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
