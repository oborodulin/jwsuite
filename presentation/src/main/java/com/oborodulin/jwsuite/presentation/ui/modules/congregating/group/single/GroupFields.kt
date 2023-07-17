package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class GroupFields : Focusable {
    GROUP_ID,
    GROUP_CONGREGATION,
    GROUP_NUM;

    override fun key(): String {
        return this.name
    }
}
