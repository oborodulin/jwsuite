package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.home.common.ui.components.field.util.Validatable
import java.math.BigDecimal

private const val TAG = "Geo.ui.LocalityInputValidator"

sealed class RegionDistrictInputValidator : Validatable {
    object ErcCode : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.congregation_num_empty_error
                //etc..
                else -> null
            }
    }

    object FullName : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.congregation_name_empty_error
                else -> null
            }
    }

    object Address : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_mark_empty_error
                else -> null
            }
    }

    object TotalArea : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNotEmpty() -> {
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                }
                else -> null
            }
    }

    object LivingSpace : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNotEmpty() -> {
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                }
                else -> null
            }
    }

    object HeatedVolume : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNotEmpty() ->
                    if ((inputs[0].replace(',', '.').toBigDecimalOrNull()
                            ?: BigDecimal.ZERO) <= BigDecimal.ZERO
                    ) com.oborodulin.home.common.R.string.number_negative_error else null
                else -> null
            }
    }

    object PaymentDay : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isEmpty() || (inputs[0].toIntOrNull()
                    ?: 0) <= 0 -> R.string.locality_empty_error
                else -> null
            }
    }

    object PersonsNum : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isEmpty() || (inputs[0].toIntOrNull()
                    ?: 0) <= 0 -> R.string.persons_num_empty_error
                else -> null
            }
    }
}
