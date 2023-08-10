package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.R
import com.oborodulin.home.common.domain.model.DomainModel
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
        "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[roadType.ordinal]} $streetName"
            .plus(
                when (isEven) {
                    null -> ""
                    else -> if (isEven) " (${ctx.resources.getString(R.string.even_unit)}"
                    else " (${ctx.resources.getString(R.string.odd_unit)}"
                }
            ).plus(
                when (housesQty) {
                    null -> if (isEven == null) "" else ")"
                    else -> (if (isEven == null) " (" else ", ").plus(
                        " $housesQty ${ctx.resources.getString(R.string.house_unit)})"
                    )
                }
            )
    val houseInfo = if (houses.isNotEmpty()) {
        houses.groupBy { it.isPrivateSector }.forEach { (isHousePrivateSector, houses) ->
            (if (isPrivateSector == null && isHousePrivateSector) "${ctx.resources.getString(R.string.private_sector_unit)}: "
            else "").plus(
                " ${ctx.resources.getString(R.string.house_unit)} ".plus(
                    houses.joinToString(", ") {
                        "${it.houseNum}".plus(
                            when (it.buildingNum) {
                                null -> ""
                                else -> "-${it.buildingNum}"
                            }
                        ).plus(
                            if (it.buildingType != BuildingType.HOUSE) " (${
                                ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.building_types)[it.buildingType.ordinal]
                            })" else ""
                        )
                    }
                )
            )
        }
    } else {
        null
    }
    //val roomInfo =
}
