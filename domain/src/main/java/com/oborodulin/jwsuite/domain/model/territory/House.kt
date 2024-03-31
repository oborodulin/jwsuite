package com.oborodulin.jwsuite.domain.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.types.BuildingType

data class House(
    val ctx: Context? = null,
    val street: GeoStreet,
    val localityDistrict: GeoLocalityDistrict? = null,
    val microdistrict: GeoMicrodistrict? = null,
    val territory: Territory? = null,
    val zipCode: String? = null,
    val houseNum: Int,
    val houseLetter: String? = null,
    val buildingNum: Int? = null,
    val buildingType: BuildingType = BuildingType.APARTMENTS,
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
    val houseOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val entrances: List<Entrance> = emptyList(),
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
        if (listOf(BuildingType.APARTMENTS, BuildingType.HOUSE).contains(buildingType)
                .not()
        ) ctx?.let { it.resources.getStringArray(R.array.building_types)[buildingType.ordinal] } else null
    val calcRoomsInfo = if (calculatedRooms > 0) "$calculatedRooms ${
        ctx?.resources?.getString(R.string.room_expr).orEmpty()
    }" else null
    val territoryFullCardNum = territory?.let { "${it.fullCardNum}: " }
    val info = listOfNotNull(buildingTypeInfo, calcRoomsInfo, houseDesc)
    val houseInfo =
        houseFullNum.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")

    companion object {
        const val BUILDING_DELIMITERS = "-"
        const val LETTER_DELIMITERS = "-/"
        const val NUMBER_DELIMITERS = "$LETTER_DELIMITERS "
        fun houseNum(houseFullNum: String) =
            houseFullNum.substringBefore(BUILDING_DELIMITERS).filter { it.isDigit() }.toInt()

        fun houseLetter(houseFullNum: String) =
            houseFullNum.substringBefore(BUILDING_DELIMITERS).filter { it.isLetter() }
                .ifEmpty { null }

        fun buildingNum(houseFullNum: String) =
            houseFullNum.takeIf { it.contains(BUILDING_DELIMITERS) }
                ?.substringAfter(BUILDING_DELIMITERS)?.toIntOrNull()
    }
}
