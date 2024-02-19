package com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport

class TerritoryMemberReportEntityToTerritoryMemberReportMapper(private val ctx: Context) :
    Mapper<TerritoryMemberReportEntity, TerritoryMemberReport>,
    NullableMapper<TerritoryMemberReportEntity, TerritoryMemberReport> {
    override fun map(input: TerritoryMemberReportEntity) = TerritoryMemberReport(
        ctx = ctx,
        territoryMemberId = input.tmrTerritoryMembersId,
        territoryReportMark = input.territoryReportMark,
        languageCode = input.languageCode,
        gender = input.gender,
        age = input.age,
        isProcessed = input.isReportProcessed,
        territoryReportDesc = input.territoryReportDesc
    ).also { it.id = input.territoryMemberReportId }

    override fun nullableMap(input: TerritoryMemberReportEntity?) = input?.let { map(it) }
}