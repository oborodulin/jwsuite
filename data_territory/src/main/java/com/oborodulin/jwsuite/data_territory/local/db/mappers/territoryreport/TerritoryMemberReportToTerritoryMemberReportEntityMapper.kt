package com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import java.util.UUID

class TerritoryMemberReportToTerritoryMemberReportEntityMapper :
    Mapper<TerritoryMemberReport, TerritoryMemberReportEntity>,
    NullableMapper<TerritoryMemberReport, TerritoryMemberReportEntity> {
    override fun map(input: TerritoryMemberReport) = TerritoryMemberReportEntity(
        territoryMemberReportId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        territoryReportMark = input.territoryReportMark,
        languageCode = input.languageCode,
        gender = input.gender,
        age = input.age,
        isReportProcessed = input.isProcessed,
        territoryReportDesc = input.territoryReportDesc,
        tmrTerritoryMembersId = input.territoryMemberId
    )

    override fun nullableMap(input: TerritoryMemberReport?) = input?.let { map(it) }
}