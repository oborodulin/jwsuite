package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReport

class TerritoryReportViewToTerritoryReportMapper(private val ctx: Context) :
    Mapper<TerritoryReportView, TerritoryReport>,
    NullableMapper<TerritoryReportView, TerritoryReport> {
    override fun map(input: TerritoryReportView): TerritoryReport {
        val territoryReport = TerritoryReport(
            ctx = ctx,
            territoryId = input.territoryMember.tmcTerritoriesId,
            territoryMemberId = input.territoryMember.territoryMemberId,
            territoryMemberMark = input.territoryReport?.territoryMemberMark,
            languageCode = input.territoryReport?.languageCode,
            gender = input.territoryReport?.gender,
            age = input.territoryReport?.age,
            isProcessed = input.territoryReport?.isProcessed ?: false,
            territoryReportDesc = input.territoryReport?.territoryReportDesc
        )
        territoryReport.id = input.territoryReport?.territoryMemberReportId
        return territoryReport
    }

    override fun nullableMap(input: TerritoryReportView?) =
        input?.let { map(it) }
}