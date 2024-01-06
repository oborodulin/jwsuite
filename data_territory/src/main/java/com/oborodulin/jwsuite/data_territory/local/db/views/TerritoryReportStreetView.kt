package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity

@DatabaseView(
    viewName = TerritoryReportStreetView.VIEW_NAME,
    value = """
SELECT tsv.*, tmc.*, tmr.*
FROM ${TerritoryStreetView.VIEW_NAME} tsv JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmc.tmcTerritoriesId = tsv.tsTerritoriesId AND tmc.deliveryDate IS NULL  
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId AND tmr.tmrTerritoryStreetsId = tsv.territoryStreetId
"""
)
class TerritoryReportStreetView(
    @Embedded val territoryStreet: TerritoryStreetView,
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val territoryReport: TerritoryMemberReportEntity?,
) {
    companion object {
        const val VIEW_NAME = "territory_report_streets_view"
    }
}