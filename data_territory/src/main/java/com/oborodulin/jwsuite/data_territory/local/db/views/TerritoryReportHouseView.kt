package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity

@DatabaseView(
    viewName = TerritoryReportHouseView.VIEW_NAME,
    value = """
SELECT hv.*, tmc.*, tmr.*, ts.*
FROM ${HouseView.VIEW_NAME} hv JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmc.tmcTerritoriesId = hv.hTerritoriesId AND tmc.deliveryDate IS NULL  
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId AND tmr.tmrHousesId = hv.houseId
    LEFT JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON ts.tsStreetsId = hv.hStreetsId AND ts.tsTerritoriesId = hv.hTerritoriesId
UNION ALL     
SELECT hv.*, tmc.*, tmr.*, ts.*
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON ts.tsTerritoriesId = t.territoryId
    JOIN ${HouseView.VIEW_NAME} hv ON hv.hStreetsId = ts.tsStreetsId AND hv.territoryId IS NULL
    JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmc.tmcTerritoriesId = t.territoryId AND tmc.deliveryDate IS NULL  
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId AND tmr.tmrHousesId = hv.houseId
"""
)
class TerritoryReportHouseView(
    @Embedded val house: HouseView,
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val territoryReport: TerritoryMemberReportEntity?,
    @Embedded val territoryStreet: TerritoryStreetEntity?
) {
    companion object {
        const val VIEW_NAME = "territory_report_houses_view"
    }
}