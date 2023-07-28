package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem

sealed class TerritoringInputEvent(val value: String) : Inputable {
    data class Location(val input: TerritoryLocationsListItem) :
        TerritoringInputEvent(input.headline)

    data class IsPrivateSector(val input: Boolean) : TerritoringInputEvent(input.toString())

    override fun value(): String {
        return this.value
    }
}
