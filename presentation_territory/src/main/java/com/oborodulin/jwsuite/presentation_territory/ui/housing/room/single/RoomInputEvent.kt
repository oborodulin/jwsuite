package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

sealed class RoomInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : RoomInputEvent(input.headline)
    data class LocalityDistrict(val input: ListItemModel) : RoomInputEvent(input.headline)
    data class Microdistrict(val input: ListItemModel) : RoomInputEvent(input.headline)
    data class Street(val input: StreetsListItem) : RoomInputEvent(input.headline)
    data class House(val input: HousesListItem) : RoomInputEvent(input.headline)
    data class Entrance(val input: ListItemModel) : RoomInputEvent(input.headline)
    data class Floor(val input: ListItemModel) : RoomInputEvent(input.headline)
    data class Territory(val input: ListItemModel) : RoomInputEvent(input.headline)
    data class RoomNum(val input: Int?) : RoomInputEvent(input?.toString().orEmpty())
    data class IsIntercom(val input: Boolean?) : RoomInputEvent(input?.toString().orEmpty())
    data class IsResidential(val input: Boolean) : RoomInputEvent(input.toString())
    data class IsForeignLanguage(val input: Boolean) : RoomInputEvent(input.toString())
    data class RoomDesc(val input: String?) : RoomInputEvent(input.orEmpty())

    override fun value() = this.value
}
