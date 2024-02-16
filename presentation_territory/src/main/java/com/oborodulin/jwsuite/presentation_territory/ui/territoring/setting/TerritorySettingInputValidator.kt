package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_territory.R

private const val TAG = "Territoring.TerritorySettingInputValidator"

sealed class TerritorySettingInputValidator : Validatable {
    data object TerritoryProcessingPeriod : TerritorySettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_processing_period_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryAtHandPeriod : TerritorySettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_at_hand_period_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryIdlePeriod : TerritorySettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_idle_period_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryRoomsLimit : TerritorySettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_rooms_limit_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryMaxRooms : TerritorySettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_max_rooms_empty_error
                //etc..
                else -> null
            }
    }
}
