package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoringFields : Focusable {
    TERRITORY_LOCATION,
    TERRITORING_IS_PRIVATE_SECTOR;

    override fun key(): String {
        return this.name
    }
}
