package com.oborodulin.jwsuite.domain.services.csv.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv
import com.oborodulin.jwsuite.domain.types.BuildingType

data class House(
    val ctx: Context? = null,
    val street: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv,
    val localityDistrict: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv? = null,
    val microdistrict: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv? = null,
    val territory: Territory? = null,
    val zipCode: String? = null,
    val houseNum: Int,
    val houseLetter: String? = null,
    val buildingNum: Int? = null,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val houseEntrancesQty: Int? = null,
    val floorsByEntrance: Int? = null,
    val roomsByHouseFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val houseDesc: String? = null,
    val entrances: List<com.oborodulin.jwsuite.domain.services.csv.model.territory.Entrance> = emptyList(),
    val rooms: List<Room> = emptyList()
) : DomainModel() {
    var calculatedRooms = when (estimatedRooms) {
        null -> (houseEntrancesQty ?: 0) * (floorsByEntrance ?: 0) * (roomsByHouseFloor ?: 0)
        else -> estimatedRooms
    }
    val houseExpr = ctx?.resources?.getString(R.string.house_expr)
    val houseFullNum =
        "$houseNum${houseLetter?.uppercase().orEmpty()}${buildingNum?.let { "-$it" }.orEmpty()}"
    val buildingTypeInfo =
        if (buildingType != BuildingType.HOUSE) ctx?.let { it.resources.getStringArray(R.array.building_types)[buildingType.ordinal] } else null
    val calcRoomsInfo = if (calculatedRooms > 0) "$calculatedRooms ${
        ctx?.resources?.getString(R.string.room_expr).orEmpty()
    }" else null
    val territoryFullCardNum = territory?.let { "${it.fullCardNum}: " }
    val info = listOfNotNull(buildingTypeInfo, calcRoomsInfo, houseDesc)
    val houseInfo =
        houseFullNum.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")
}