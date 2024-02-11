package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class TerritorySettingInputEvent(val value: String) : Inputable {
    data class TerritoryProcessingPeriod(val input: String) : TerritorySettingInputEvent(input)
    data class TerritoryAtHandPeriod(val input: String) : TerritorySettingInputEvent(input)
    data class TerritoryIdlePeriod(val input: String) : TerritorySettingInputEvent(input)
    data class TerritoryRoomsLimit(val input: String) : TerritorySettingInputEvent(input)
    data class TerritoryMaxRooms(val input: String) : TerritorySettingInputEvent(input)

    override fun value() = this.value
}
