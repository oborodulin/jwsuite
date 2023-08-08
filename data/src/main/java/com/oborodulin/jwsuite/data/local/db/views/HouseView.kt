package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity

@DatabaseView(
    viewName = HouseView.VIEW_NAME,
    value = """
SELECT s.*, h.* FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetView.VIEW_NAME} s ON s.streetId = h.hStreetsId
ORDER BY s.streetName, h.houseNum, h.buildingNum
"""
)
class HouseView(
    @Embedded val street: GeoStreetView,
    @Embedded val house: HouseEntity
) {
    companion object {
        const val VIEW_NAME = "houses_view"
    }
}