package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem

sealed class TerritoringInputEvent(val value: String) : Inputable {
    data class IsPrivateSector(val input: Boolean) : TerritoringInputEvent(input.toString())
    data class Location(val input: TerritoryLocationsListItem) :
        TerritoringInputEvent(input.headline)

    override fun value() = this.value
}
