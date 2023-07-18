package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Territoring.RegionInputValidator"

sealed class TerritoryCategoryInputValidator : Validatable {
    object TerritoryCategoryCode : TerritoryCategoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_code_empty_error
                //etc..
                else -> null
            }
    }

    object TerritoryCategoryMark : TerritoryCategoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_category_mark_empty_error
                //etc..
                else -> null
            }
    }

    object TerritoryCategoryName : TerritoryCategoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_name_empty_error
                else -> null
            }
    }
}
