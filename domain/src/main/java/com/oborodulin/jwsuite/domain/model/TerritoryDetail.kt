package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import java.util.UUID

data class TerritoryDetail(
    val ctx: Context,
    val territoryStreetId: UUID? = null,
    val street: GeoStreet,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null,
    val isEvenSide: Boolean? = null,
    val houses: List<House> = emptyList(),
    val entrances: List<Entrance> = emptyList(),
    val floors: List<Floor> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    val isPrivateSectorInfo = isPrivateSector?.let {
        when (isPrivateSector) {
            true -> ctx.resources.getString(R.string.private_sector_expr)
            else -> null
        }
    }
    val isEvenSideInfo = isEvenSide?.let {
        if (it) ctx.resources.getString(R.string.even_expr) else ctx.resources.getString(
            R.string.odd_expr
        )
    }
    val estHousesInfo =
        estimatedHouses?.let { "$it ${ctx.resources.getString(R.string.house_expr)}" }
    val info = listOfNotNull(isEvenSideInfo, estHousesInfo)
    val streetInfo =
        street.streetFullName.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")

    val housesInfo = if (houses.isNotEmpty()) {
        var info = when (isPrivateSector) {
            true -> ", ${ctx.resources.getString(R.string.including_expr)} "
            else -> ""
        }
        houses.groupBy { it.isPrivateSector }.forEach { (isHousePrivateSector, houses) ->
            info = info.plus(
                (if (isPrivateSector == null && isHousePrivateSector) "${ctx.resources.getString(R.string.private_sector_expr)}: " else ""
                        ).plus(
                        "${ctx.resources.getString(R.string.house_expr)} ".plus(
                            houses.joinToString(
                                ", "
                            ) { it.houseInfo })
                    )
            ).plus("; ")
        }
        info.substringBeforeLast(";")
    } else {
        null
    }
    val entrancesInfo = ""
    val roomsInfo = ""
}
