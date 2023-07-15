package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class GroupFields : Focusable {
    REGION_ID,
    REGION_CODE,
    REGION_NAME;

    override fun key(): String {
        return this.name
    }
}
