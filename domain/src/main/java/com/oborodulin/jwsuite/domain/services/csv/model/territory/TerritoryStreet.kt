package com.oborodulin.jwsuite.domain.services.csv.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv
import java.util.UUID

data class TerritoryStreet(
    val ctx: Context? = null,
    val territoryId: UUID,
    val street: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv,
    val isEvenSide: Boolean? = null,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null,
    var houses: List<House> = emptyList(),
    val isNeedAddEstHouses: Boolean = false,
    val isExistsHouses: Boolean = false
) : DomainModel() {
    val streetFullName = street.streetFullName
    val isEvenSideInfo = isEvenSide?.let {
        when (isEvenSide) {
            true -> ctx?.resources?.getString(R.string.even_expr).orEmpty()
            else -> ctx?.resources?.getString(R.string.odd_expr).orEmpty()
        }
    }
    val isPrivateSectorInfo = isPrivateSector?.let {
        when (isPrivateSector) {
            true -> ctx?.resources?.getString(R.string.private_sector_expr).orEmpty()
            else -> null
        }
    }
    val estHousesInfo = estimatedHouses?.let {
        "$it ${ctx?.resources?.getString(R.string.house_expr).orEmpty()}"
    }
    val info = listOfNotNull(isPrivateSectorInfo, isEvenSideInfo, estHousesInfo)
    val streetInfo =
        streetFullName.plus(if (info.isNotEmpty()) " (${info.joinToString(", ")})" else "")
}