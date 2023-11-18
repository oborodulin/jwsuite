package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Presentation.AppSettingInputValidator"

sealed class AppSettingInputValidator : Validatable {
    data object TerritoryProcessingPeriod : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_processing_period_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryAtHandPeriod : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_at_hand_period_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryIdlePeriod : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_idle_period_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryRoomsLimit : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_rooms_limit_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryMaxRooms : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_max_rooms_empty_error
                //etc..
                else -> null
            }
    }
}
