package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import com.oborodulin.home.accounting.R
import com.oborodulin.home.common.ui.components.field.util.Validatable
import java.math.BigDecimal

private const val TAG = "Accounting.ui.PayerInputValidator"

sealed class CongregationInputValidator : Validatable {
    object ErcCode : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() -> R.string.erc_code_empty_error
                //etc..
                else -> null
            }
    }

    object FullName : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() -> R.string.full_name_empty_error
                else -> null
            }
    }

    object Address : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() -> R.string.address_empty_error
                else -> null
            }
    }

    object TotalArea : CongregationInputValidator() {
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

    object LivingSpace : CongregationInputValidator() {
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

    object HeatedVolume : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isNotEmpty() ->
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                else -> null
            }
    }

    object PaymentDay : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() || (inputs[0].toIntOrNull()
                    ?: 0) <= 0 -> R.string.payment_day_empty_error
                else -> null
            }
    }

    object PersonsNum : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String): Int? =
            when {
                inputs[0].isEmpty() || (inputs[0].toIntOrNull()
                    ?: 0) <= 0 -> R.string.persons_num_empty_error
                else -> null
            }
    }
}
