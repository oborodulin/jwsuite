package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Territoring.TerritoryHouseInputValidator"

sealed class TerritoryRoomInputValidator : Validatable {
    data object Room : TerritoryRoomInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.house_empty_error
                else -> null
            }
    }
}
