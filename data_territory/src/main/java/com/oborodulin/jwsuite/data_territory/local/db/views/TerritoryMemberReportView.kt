package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity

@DatabaseView(
    viewName = TerritoryMemberReportView.VIEW_NAME,
    value = """
SELECT tmc.*, tmr.*, tsv.*, hv.*, rv.*
FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr 
        ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId
    LEFT JOIN ${TerritoryStreetView.VIEW_NAME} tsv ON tsv.territoryStreetId = tmr.tmrTerritoryStreetsId
    LEFT JOIN ${HouseView.VIEW_NAME} hv ON hv.houseId = tmr.tmrHousesId
    LEFT JOIN ${RoomView.VIEW_NAME} rv ON rv.roomId = tmr.tmrRoomsId
"""
)
class TerritoryMemberReportView(
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val territoryReport: TerritoryMemberReportEntity,
    @Embedded val territoryStreet: TerritoryStreetView?,
    @Embedded val house: HouseView?,
    @Embedded val room: RoomView?
) {
    companion object {
        const val VIEW_NAME = "territory_member_reports_view"
    }
}