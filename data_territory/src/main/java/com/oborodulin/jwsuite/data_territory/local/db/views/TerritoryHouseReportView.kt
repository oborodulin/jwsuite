package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    viewName = TerritoryHouseReportView.VIEW_NAME,
    value = """
SELECT hv.*, trv.*
FROM ${HouseView.VIEW_NAME} hv LEFT JOIN ${TerritoryReportView.VIEW_NAME} trv 
    ON trv.tmcTerritoriesId = hv.territoryId AND trv.deliveryDate IS NULL AND trv.tmrHousesId = hv.houseId
"""
)
class TerritoryHouseReportView(
    @Embedded val house: HouseView,
    @Embedded val houseReport: TerritoryReportView?
) {
    companion object {
        const val VIEW_NAME = "territory_house_reports_view"
    }
}