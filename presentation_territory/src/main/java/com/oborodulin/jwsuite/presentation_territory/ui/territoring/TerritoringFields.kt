package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoringFields : Focusable {
    TERRITORING_ID,
    TERRITORING_IS_PRIVATE_SECTOR,
    TERRITORY_LOCATION;

    override fun key(): String {
        return this.name
    }
}
