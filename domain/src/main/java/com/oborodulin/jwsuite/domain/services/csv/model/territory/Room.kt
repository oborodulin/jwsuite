package com.oborodulin.jwsuite.domain.services.csv.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv

data class Room(
    val ctx: Context? = null,
    val locality: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv,
    val localityDistrict: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv? = null,
    val microdistrict: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv? = null,
    val street: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv,
    val house: House,
    val entrance: com.oborodulin.jwsuite.domain.services.csv.model.territory.Entrance?,
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
