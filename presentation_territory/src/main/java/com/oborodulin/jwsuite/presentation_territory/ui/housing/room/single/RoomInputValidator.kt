package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Housing.RoomInputValidator"

sealed class RoomInputValidator : Validatable {
    data object Locality : RoomInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_empty_error
                else -> null
            }
    }

    data object Street : RoomInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.street_empty_error
                else -> null
            }
    }

    data object House : RoomInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.house_empty_error
                else -> null
            }
    }

    data object RoomNum : RoomInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> com.oborodulin.jwsuite.presentation_territory.R.string.room_num_empty_error
                else -> null
            }
    }
}
