package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryHouseReport
import java.util.UUID

class TerritoryHouseReportToTerritoryMemberReportEntityMapper :
    Mapper<TerritoryHouseReport, TerritoryMemberReportEntity>,
    NullableMapper<TerritoryHouseReport, TerritoryMemberReportEntity> {
    override fun map(input: TerritoryHouseReport) = TerritoryMemberReportEntity(
        territoryMemberReportId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        territoryReportMark = input.territoryReport.territoryReportMark,
        languageCode = input.territoryReport.languageCode,
        gender = input.territoryReport.gender,
        age = input.territoryReport.age,
        isProcessed = input.territoryReport.isProcessed,
        territoryReportDesc = input.territoryReport.territoryReportDesc,
        tmrHousesId = input.house.id,
        tmrTerritoryMembersId = input.territoryReport.territoryMemberId
    )

    override fun nullableMap(input: TerritoryHouseReport?) =
        input?.let { map(it) }
}