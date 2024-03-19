package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_territory.R

sealed class TerritoriesInputValidator : Validatable {
    data object Member : TerritoriesInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> com.oborodulin.jwsuite.presentation.R.string.member_empty_error
                else -> null
            }
    }

    data object ReceivingDate : TerritoriesInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.territory_receiving_date_empty_error
                else -> null
            }
    }

    data object DeliveryDate : TerritoriesInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.territory_delivery_date_empty_error
                else -> null
            }
    }
}