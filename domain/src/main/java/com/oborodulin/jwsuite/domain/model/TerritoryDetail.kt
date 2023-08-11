package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.util.BuildingType
import com.oborodulin.jwsuite.domain.util.RoadType
import java.util.UUID

data class TerritoryDetail(
    val ctx: Context,
    val territoryStreetId: UUID? = null,
    val streetId: UUID,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean? = false,
    val housesQty: Int? = null,
    val streetName: String,
    val isEven: Boolean? = null,
    val houses: List<House> = emptyList(),
    val entrances: List<Entrance> = emptyList(),
    val floors: List<Floor> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    val streetInfo =
        "${ctx.resources.getStringArray(R.array.road_types)[roadType.ordinal]} $streetName"
            .plus(
                when (isEven) {
                    null -> ""
                    else -> if (isEven) " (${ctx.resources.getString(R.string.even_expr)}"
                    else " (${ctx.resources.getString(R.string.odd_expr)}"
                }
            ).plus(
                when (housesQty) {
                    null -> if (isEven == null) "" else ")"
                    else -> (if (isEven == null) " (" else ", ").plus(
                        " $housesQty ${ctx.resources.getString(R.string.house_expr)})"
                    )
                }
            )
    val housesInfo = if (houses.isNotEmpty()) {
        var info = when (isPrivateSector) {
            true -> ", ${ctx.resources.getString(R.string.including_expr)}"
            else -> ""
        }
        houses.groupBy { it.isPrivateSector }.forEach { (isHousePrivateSector, houses) ->
            info = info.plus(
                (if (isPrivateSector == null && isHousePrivateSector) "${ctx.resources.getString(R.string.private_sector_expr)}: " else ""
                        ).plus(
                        " ${ctx.resources.getString(R.string.house_expr)} ".plus(
                            houses.joinToString(", ") {
                                "${it.houseNum}".plus(
                                    when (it.buildingNum) {
                                        null -> ""
                                        else -> "-${it.buildingNum}"
                                    }
                                ).plus(
                                    if (it.buildingType != BuildingType.HOUSE) " (${
                                        ctx.resources.getStringArray(R.array.building_types)[it.buildingType.ordinal]
                                    })" else ""
                                )
                            }
                        )
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
