package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoryStreetFields : Focusable {
    LOCALITY_ID,
    LOCALITY_REGION,
    LOCALITY_REGION_DISTRICT,
    LOCALITY_CODE,
    LOCALITY_SHORT_NAME,
    LOCALITY_TYPE,
    LOCALITY_NAME;

    override fun key(): String {
        return this.name
    }
}
