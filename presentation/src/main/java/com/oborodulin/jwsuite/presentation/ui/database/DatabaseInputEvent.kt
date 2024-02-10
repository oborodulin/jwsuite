package com.oborodulin.jwsuite.presentation.ui.database

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class DatabaseInputEvent(val value: String) : Inputable {
    data class TerritoryProcessingPeriod(val input: String) : DatabaseInputEvent(input)
    data class TerritoryAtHandPeriod(val input: String) : DatabaseInputEvent(input)
    data class TerritoryIdlePeriod(val input: String) : DatabaseInputEvent(input)
    data class TerritoryRoomsLimit(val input: String) : DatabaseInputEvent(input)
    data class TerritoryMaxRooms(val input: String) : DatabaseInputEvent(input)

    override fun value() = this.value
}
