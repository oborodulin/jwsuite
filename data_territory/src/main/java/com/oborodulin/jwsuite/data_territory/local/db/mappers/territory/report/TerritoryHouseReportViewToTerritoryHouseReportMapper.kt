package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryHouseReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryHouseReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReport

class TerritoryHouseReportViewToTerritoryHouseReportMapper(
    private val houseMapper: HouseViewToHouseMapper,
    private val territoryReportMapper: TerritoryMemberReportEntityToTerritoryReportMapper
) :
    Mapper<TerritoryHouseReportView, TerritoryHouseReport>,
    NullableMapper<TerritoryHouseReportView, TerritoryHouseReport> {
    override fun map(input: TerritoryHouseReportView): TerritoryHouseReport {
        val territoryReport =
            territoryReportMapper.nullableMap(input.territoryReport) ?: TerritoryReport(
                territoryMemberId = input.territoryMember.territoryMemberId
            )
        val territoryHouseReport = TerritoryHouseReport(
            house = houseMapper.map(input.house),
            territoryReport = territoryReport
        )
        territoryHouseReport.id = input.territoryReport?.territoryMemberReportId
        return territoryHouseReport
    }

    override fun nullableMap(input: TerritoryHouseReportView?) =
        input?.let { map(it) }
}