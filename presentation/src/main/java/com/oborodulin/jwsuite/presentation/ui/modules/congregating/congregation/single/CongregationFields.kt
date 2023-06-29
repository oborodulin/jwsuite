package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class CongregationFields : Focusable {
    CONGREGATION_ID,
    LOCALITY_ID,
    CONGREGATION_NUM,
    CONGREGATION_NAME,
    TERRITORY_MARK,
    IS_FAVORITE;

    override fun key(): String {
        return this.name
    }
}
