package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity

@DatabaseView(
    viewName = TerritoryRoomReportView.VIEW_NAME,
    value = """
SELECT rv.*, tmc.*, tmr.*
FROM ${RoomView.VIEW_NAME} rv JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmc.tmcTerritoriesId = rv.territoryId AND tmc.deliveryDate IS NULL  
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId AND tmr.tmrRoomsId = rv.roomId
UNION ALL
SELECT rv.*, tmc.*, tmr.*
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${HouseEntity.TABLE_NAME} h ON h.hTerritoriesId = t.territoryId 
    JOIN ${RoomView.VIEW_NAME} rv ON rv.rHousesId = h.houseId AND rv.territoryId IS NULL
    JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmc.tmcTerritoriesId = t.territoryId AND tmc.deliveryDate IS NULL  
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId AND tmr.tmrRoomsId = rv.roomId
UNION ALL
SELECT rv.*, tmc.*, tmr.*
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${EntranceEntity.TABLE_NAME} e ON e.eTerritoriesId = t.territoryId 
    JOIN ${RoomView.VIEW_NAME} rv ON rv.rEntrancesId = e.entranceId AND rv.territoryId IS NULL
    JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmc.tmcTerritoriesId = t.territoryId AND tmc.deliveryDate IS NULL  
    LEFT JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId AND tmr.tmrRoomsId = rv.roomId
"""
)
class TerritoryRoomReportView(
    @Embedded val room: RoomView,
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val territoryReport: TerritoryMemberReportEntity?
) {
    companion object {
        const val VIEW_NAME = "territory_room_reports_view"
    }
}