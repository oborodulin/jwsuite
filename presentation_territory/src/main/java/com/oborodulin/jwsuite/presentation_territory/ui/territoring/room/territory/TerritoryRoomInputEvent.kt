package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoryRoomInputEvent(val value: String) : Inputable {
    data class Territory(val input: ListItemModel) : TerritoryRoomInputEvent(input.headline)
    data class Room(val input: ListItemModel) : TerritoryRoomInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
