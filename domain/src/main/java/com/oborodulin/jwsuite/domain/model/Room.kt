package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R

data class Room(
    val ctx: Context? = null,
    val locality: GeoLocality,
    val localityDistrict: GeoLocalityDistrict? = null,
    val microdistrict: GeoMicrodistrict? = null,
    val street: GeoStreet,
    val house: House,
    val entrance: Entrance?,
    val floor: Floor?,
    val territory: Territory?,
    val roomNum: Int,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val roomDesc: String? = null
) : DomainModel() {
    val roomFullNum = "${house.street.streetFullName}, ${house.houseExpr} ${house.houseFullNum}, ${
        ctx?.resources?.getString(R.string.room_expr).orEmpty()
    } $roomNum"
    val territoryFullCardNum = territory?.let { "${it.fullCardNum}: " }
    val info = listOfNotNull(roomDesc)
    val roomInfo =
        roomFullNum.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")
}
