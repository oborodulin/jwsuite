package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomViewToRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryRoomReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryRoomReport

class TerritoryRoomReportViewToTerritoryRoomReportMapper(
    private val roomMapper: RoomViewToRoomMapper,
    private val territoryReportMapper: TerritoryReportViewToTerritoryReportMapper
) :
    Mapper<TerritoryRoomReportView, TerritoryRoomReport>,
    NullableMapper<TerritoryRoomReportView, TerritoryRoomReport> {
    override fun map(input: TerritoryRoomReportView): TerritoryRoomReport {
        val territoryRoomReport = TerritoryRoomReport(
            room = roomMapper.map(input.room),
            territoryReport = territoryReportMapper.nullableMap(input.roomReport)
        )
        territoryRoomReport.id = input.roomReport?.territoryReport?.territoryMemberReportId
        return territoryRoomReport
    }

    override fun nullableMap(input: TerritoryRoomReportView?) =
        input?.let { map(it) }
}