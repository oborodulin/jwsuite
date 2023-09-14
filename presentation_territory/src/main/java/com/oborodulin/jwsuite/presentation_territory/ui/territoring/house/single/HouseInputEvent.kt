package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem

sealed class HouseInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : HouseInputEvent(input.headline)
    data class Street(val input: StreetsListItem) : HouseInputEvent(input.headline)
    data class LocalityDistrict(val input: ListItemModel) : HouseInputEvent(input.headline)
    data class Microdistrict(val input: ListItemModel) : HouseInputEvent(input.headline)
    data class Territory(val input: ListItemModel) : HouseInputEvent(input.headline)
    data class ZipCode(val input: String) : HouseInputEvent(input)
    data class HouseNum(val input: Int?) : HouseInputEvent(input?.toString().orEmpty())
    data class HouseLetter(val input: String) : HouseInputEvent(input)
    data class BuildingNum(val input: Int?) : HouseInputEvent(input?.toString().orEmpty())
    data class BuildingType(val input: com.oborodulin.jwsuite.domain.util.BuildingType) :
        HouseInputEvent(input.name)

    data class IsBusiness(val input: Boolean) : HouseInputEvent(input.toString())
    data class IsSecurity(val input: Boolean) : HouseInputEvent(input.toString())
    data class IsIntercom(val input: Boolean?) : HouseInputEvent(input?.toString().orEmpty())
    data class IsResidential(val input: Boolean) : HouseInputEvent(input.toString())
    data class HouseEntrancesQty(val input: Int?) : HouseInputEvent(input?.toString().orEmpty())
    data class FloorsByEntrance(val input: Int?) : HouseInputEvent(input?.toString().orEmpty())
    data class RoomsByHouseFloor(val input: Int?) : HouseInputEvent(input?.toString().orEmpty())
    data class EstimatedRooms(val input: Int?) : HouseInputEvent(input?.toString().orEmpty())
    data class IsForeignLanguage(val input: Boolean) : HouseInputEvent(input.toString())
    data class IsPrivateSector(val input: Boolean) : HouseInputEvent(input.toString())
    data class HouseDesc(val input: String?) : HouseInputEvent(input.orEmpty())

    override fun value(): String {
        return this.value
    }
}
