package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_territory.R

private const val TAG = "Territoring.TerritoryCategoryInputValidator"

sealed class TerritoryCategoryInputValidator : Validatable {
    data object TerritoryCategoryCode : TerritoryCategoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_category_code_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryCategoryMark : TerritoryCategoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_category_mark_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryCategoryName : TerritoryCategoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_category_name_empty_error
                else -> null
            }
    }
}
