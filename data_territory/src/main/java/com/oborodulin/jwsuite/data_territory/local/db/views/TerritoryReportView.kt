package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity

@DatabaseView(
    viewName = TerritoryReportView.VIEW_NAME,
    value = """
SELECT tmc.*, tmr.* FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc 
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId
"""
)
class TerritoryReportView(
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val territoryReport: TerritoryMemberReportEntity?
) {
    companion object {
        const val VIEW_NAME = "territory_reports_view"
    }
}