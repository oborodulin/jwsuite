package com.oborodulin.jwsuite.presentation_dashboard.ui.database

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class DatabaseInputEvent(val value: String) : Inputable {
    data class ReceiverMember(val input: ListItemModel) : DatabaseInputEvent(input.headline)

    override fun value() = this.value
}