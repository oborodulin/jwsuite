package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryHouseReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryHouseReport

class TerritoryHouseReportViewToTerritoryHouseReportMapper(
    private val houseMapper: HouseViewToHouseMapper,
    private val territoryReportMapper: TerritoryReportViewToTerritoryReportMapper
) :
    Mapper<TerritoryHouseReportView, TerritoryHouseReport>,
    NullableMapper<TerritoryHouseReportView, TerritoryHouseReport> {
    override fun map(input: TerritoryHouseReportView): TerritoryHouseReport {
        val territoryHouseReport = TerritoryHouseReport(
            house = houseMapper.map(input.house),
            territoryReport = territoryReportMapper.nullableMap(input.houseReport)
        )
        territoryHouseReport.id = input.houseReport?.territoryReport?.territoryMemberReportId
        return territoryHouseReport
    }

    override fun nullableMap(input: TerritoryHouseReportView?) =
        input?.let { map(it) }
}