package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.util.RoadType

data class GeoStreet(
    val ctx: Context? = null,
    val locality: GeoLocality,
    //val localityDistrict: GeoLocalityDistrict?,
    //val microdistrict: GeoMicrodistrict?,
    val streetHashCode: Int,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val estimatedHouses: Int? = null,
    val streetName: String,
    val houses: List<House> = emptyList()
) : DomainModel() {
    val streetFullName = "${
        ctx?.let { it.resources.getStringArray(R.array.road_types)[roadType.ordinal] }.orEmpty()
    } $streetName".trim()
    val isPrivateSectorInfo = when (isPrivateSector) {
        true -> ctx?.resources?.getString(R.string.private_sector_expr).orEmpty()
        else -> null
    }
    val estHousesInfo = estimatedHouses?.let {
        "$it ${ctx?.resources?.getString(R.string.house_expr).orEmpty()}"
    }
    val info = listOfNotNull(isPrivateSectorInfo, estHousesInfo)
    val streetInfo =
        ("$streetFullName ".plus(if (info.isNotEmpty()) "(${info.joinToString(", ")})" else "")).trim()
}
