package com.oborodulin.jwsuite.domain.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R

data class Entrance(
    val ctx: Context? = null,
    val house: House,
    val territory: Territory? = null,
    val entranceNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val floorsQty: Int? = null,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val entranceDesc: String? = null,
    val floors: List<Floor> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    val entranceFullNum =
        "${house.street.streetFullName}, ${house.houseExpr} ${house.houseFullNum}, ${
            ctx?.resources?.getString(R.string.entrance_expr).orEmpty()
        } $entranceNum"
    var calculatedRooms = when (estimatedRooms) {
        null -> (floorsQty ?: 0) * (roomsByFloor ?: 0)
        else -> estimatedRooms
    }
    val existingRooms: Int
        get() {
            return rooms.size
        }
    val calcRoomsInfo = if (calculatedRooms > 0) "$calculatedRooms ${
        ctx?.resources?.getString(R.string.room_expr).orEmpty()
    }" else null
    val territoryFullCardNum = territory?.let { "${it.fullCardNum}: " }
    val info = listOfNotNull(calcRoomsInfo, entranceDesc)
    val entranceInfo =
        entranceFullNum.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")
}