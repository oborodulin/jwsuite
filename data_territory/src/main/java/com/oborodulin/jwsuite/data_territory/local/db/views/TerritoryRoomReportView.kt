package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    viewName = TerritoryRoomReportView.VIEW_NAME,
    value = """
SELECT rv.*, trv.*
FROM ${RoomView.VIEW_NAME} rv LEFT JOIN ${TerritoryReportView.VIEW_NAME} trv 
    ON trv.tmcTerritoriesId = rv.territoryId AND trv.deliveryDate IS NULL AND trv.tmrRoomsId = rv.roomId
"""
)
class TerritoryRoomReportView(
    @Embedded val room: RoomView,
    @Embedded val roomReport: TerritoryReportView?
) {
    companion object {
        const val VIEW_NAME = "territory_room_reports_view"
    }
}