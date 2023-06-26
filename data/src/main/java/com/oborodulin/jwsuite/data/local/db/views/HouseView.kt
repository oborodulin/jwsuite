package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity

@DatabaseView(
    viewName = HouseView.VIEW_NAME,
    value = """
SELECT h.*, s.* FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetView.VIEW_NAME} s ON s.streetId = h.hStreetsId
ORDER BY s.streetName, h.houseNum, h.buildingNum
"""
)
class HouseView(
    @Embedded
    val house: HouseEntity,
    @Embedded
    val street: GeoStreetView,
) {
    companion object {
        const val VIEW_NAME = "houses_view"
    }
}