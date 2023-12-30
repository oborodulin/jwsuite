package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryRoomReport
import java.util.UUID

class TerritoryRoomReportToTerritoryMemberReportEntityMapper :
    Mapper<TerritoryRoomReport, TerritoryMemberReportEntity>,
    NullableMapper<TerritoryRoomReport, TerritoryMemberReportEntity> {
    override fun map(input: TerritoryRoomReport) = TerritoryMemberReportEntity(
        territoryMemberReportId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        territoryMemberMark = input.territoryReport.territoryMemberMark,
        languageCode = input.territoryReport.languageCode,
        gender = input.territoryReport.gender,
        age = input.territoryReport.age,
        isProcessed = input.territoryReport.isProcessed,
        territoryReportDesc = input.territoryReport.territoryReportDesc,
        tmrRoomsId = input.room.id,
        tmrTerritoryMembersId = input.territoryReport.territoryMemberId
    )

    override fun nullableMap(input: TerritoryRoomReport?) =
        input?.let { map(it) }
}