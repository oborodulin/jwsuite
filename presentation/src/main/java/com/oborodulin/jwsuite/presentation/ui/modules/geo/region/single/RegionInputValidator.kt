package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.accounting.R
import com.oborodulin.home.common.ui.components.field.util.Validatable
import java.math.BigDecimal

private const val TAG = "Geo.ui.LocalityInputValidator"

sealed class RegionInputValidator : Validatable {
    object ErcCode : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() -> R.string.erc_code_empty_error
                //etc..
                else -> null
            }
    }

    object FullName : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() -> R.string.full_name_empty_error
                else -> null
            }
    }

    object Address : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() -> R.string.address_empty_error
                else -> null
            }
    }

    object TotalArea : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isNotEmpty() -> {
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                }
                else -> null
            }
    }

    object LivingSpace : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isNotEmpty() -> {
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                }
                else -> null
            }
    }

    object HeatedVolume : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isNotEmpty() ->
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                else -> null
            }
    }

    object PaymentDay : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() || (inputs[0].toIntOrNull()
                    ?: 0) <= 0 -> R.string.payment_day_empty_error
                else -> null
            }
    }

    object PersonsNum : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() || (inputs[0].toIntOrNull()
                    ?: 0) <= 0 -> R.string.persons_num_empty_error
                else -> null
            }
    }
}
