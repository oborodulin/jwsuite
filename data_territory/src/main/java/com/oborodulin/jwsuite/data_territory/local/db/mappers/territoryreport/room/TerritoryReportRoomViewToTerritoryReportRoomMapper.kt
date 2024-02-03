package com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomViewToRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportEntityToTerritoryMemberReportMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom

class TerritoryReportRoomViewToTerritoryReportRoomMapper(
    private val roomMapper: RoomViewToRoomMapper,
    private val territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
) : Mapper<TerritoryReportRoomView, TerritoryReportRoom>,
    NullableMapper<TerritoryReportRoomView, TerritoryReportRoom> {
    override fun map(input: TerritoryReportRoomView): TerritoryReportRoom {
        val room = roomMapper.map(input.room)
        val territoryMemberReport =
            territoryReportMapper.nullableMap(input.territoryReport) ?: TerritoryMemberReport(
                room = room,
                territoryId = input.territoryMember.tmcTerritoriesId,
                territoryMemberId = input.territoryMember.territoryMemberId
            )
        val territoryReportRoom = TerritoryReportRoom(
            room = room,
            territoryMemberReport = territoryMemberReport
        )
        territoryReportRoom.id = input.room.room.roomId
        return territoryReportRoom
    }

    override fun nullableMap(input: TerritoryReportRoomView?) = input?.let { map(it) }
}