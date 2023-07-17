package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class GroupInputEvent(val value: String) : Inputable {
    data class Congregation(val input: ListItemModel) : GroupInputEvent(input.headline)
    data class GroupNum(val input: String) : GroupInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
