package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class CongregationFields : Focusable {
    CONGREGATION_ID,
    CONGREGATION_LOCALITY,
    CONGREGATION_NUM,
    CONGREGATION_NAME,
    TERRITORY_MARK,
    IS_FAVORITE;

    override fun key() = this.name
}
