package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReport

class TerritoryMemberReportEntityToTerritoryReportMapper(private val ctx: Context) :
    Mapper<TerritoryMemberReportEntity, TerritoryReport>,
    NullableMapper<TerritoryMemberReportEntity, TerritoryReport> {
    override fun map(input: TerritoryMemberReportEntity): TerritoryReport {
        val territoryReport = TerritoryReport(
            ctx = ctx,
            territoryMemberId = input.tmrTerritoryMembersId,
            territoryMemberMark = input.territoryMemberMark,
            languageCode = input.languageCode,
            gender = input.gender,
            age = input.age,
            isProcessed = input.isProcessed,
            territoryReportDesc = input.territoryReportDesc
        )
        territoryReport.id = input.territoryMemberReportId
        return territoryReport
    }

    override fun nullableMap(input: TerritoryMemberReportEntity?) =
        input?.let { map(it) }
}