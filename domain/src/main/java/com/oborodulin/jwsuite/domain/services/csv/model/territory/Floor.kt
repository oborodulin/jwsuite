package com.oborodulin.jwsuite.domain.services.csv.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R

data class Floor(
    val ctx: Context? = null,
    val house: House,
    val entrance: com.oborodulin.jwsuite.domain.services.csv.model.territory.Entrance?,
    val territory: Territory?,
    val floorNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val floorDesc: String? = null,
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    val floorFullNum =
        "${house.street.streetFullName}, ${house.houseExpr} ${house.houseFullNum}, ".plus(
            entrance?.let {
                "${ctx?.resources?.getString(R.string.entrance_expr).orEmpty()} ${it.entranceNum}, "
            }.orEmpty()
        ).plus("${ctx?.resources?.getString(R.string.floor_expr).orEmpty()} $floorNum")

    var calculatedRooms = when (estimatedRooms) {
        null -> roomsByFloor ?: 0
        else -> estimatedRooms
    }
    val existingRooms: Int
        get() = rooms.size
    val calcRoomsInfo = if (calculatedRooms > 0) "$calculatedRooms ${
        ctx?.resources?.getString(R.string.room_expr).orEmpty()
    }" else null
    val territoryFullCardNum = territory?.let { "${it.fullCardNum}: " }
    val info = listOfNotNull(calcRoomsInfo, floorDesc)
    val floorInfo =
        floorFullNum.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")
}
