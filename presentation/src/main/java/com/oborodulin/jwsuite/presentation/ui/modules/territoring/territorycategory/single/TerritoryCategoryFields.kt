package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoryCategoryFields : Focusable {
    TERRITORY_CATEGORY_ID,
    TERRITORY_CATEGORY_CODE,
    TERRITORY_CATEGORY_MARK,
    TERRITORY_CATEGORY_NAME;

    override fun key(): String {
        return this.name
    }
}
