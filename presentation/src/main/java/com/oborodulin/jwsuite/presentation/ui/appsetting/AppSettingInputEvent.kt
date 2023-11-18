package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class AppSettingInputEvent(val value: String) : Inputable {
    data class TerritoryProcessingPeriod(val input: String) : AppSettingInputEvent(input)
    data class TerritoryAtHandPeriod(val input: String) : AppSettingInputEvent(input)
    data class TerritoryIdlePeriod(val input: String) : AppSettingInputEvent(input)
    data class TerritoryRoomsLimit(val input: String) : AppSettingInputEvent(input)
    data class TerritoryMaxRooms(val input: String) : AppSettingInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
