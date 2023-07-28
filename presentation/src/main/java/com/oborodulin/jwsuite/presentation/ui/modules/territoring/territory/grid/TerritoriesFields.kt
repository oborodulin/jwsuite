package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoriesFields : Focusable {
    TERRITORY_MEMBER;

    override fun key(): String {
        return this.name
    }
}
