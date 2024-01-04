package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class GroupFields : Focusable {
    GROUP_ID,
    GROUP_CONGREGATION,
    GROUP_NUM;

    override fun key() = this.name
}