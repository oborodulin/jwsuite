package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R

data class Room(
    val ctx: Context? = null,
    val house: House,
    val entrance: Entrance?,
    val floor: Floor?,
    val territory: Territory?,
    val roomNum: Int,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val territoryDesc: String? = null
) : DomainModel() {
    var fullRoomNum = "${house.street.streetFullName}, ${house.houseExpr} ${house.houseFullNum}, ${
        ctx?.resources?.getString(R.string.room_expr).orEmpty()
    } $roomNum"
}
