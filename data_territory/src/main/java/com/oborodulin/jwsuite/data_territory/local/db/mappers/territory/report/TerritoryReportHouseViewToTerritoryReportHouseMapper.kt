package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse

class TerritoryReportHouseViewToTerritoryReportHouseMapper(
    private val houseMapper: HouseViewToHouseMapper,
    private val territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
) : Mapper<TerritoryReportHouseView, TerritoryReportHouse>,
    NullableMapper<TerritoryReportHouseView, TerritoryReportHouse> {
    override fun map(input: TerritoryReportHouseView): TerritoryReportHouse {
        val house = houseMapper.map(input.house)
        val territoryMemberReport =
            territoryReportMapper.nullableMap(input.territoryReport) ?: TerritoryMemberReport(
                house = house,
                territoryMemberId = input.territoryMember.territoryMemberId
            )
        val territoryReportHouse = TerritoryReportHouse(
            house = house,
            territoryMemberReport = territoryMemberReport
        )
        territoryReportHouse.id = input.territoryReport?.territoryMemberReportId
        return territoryReportHouse
    }

    override fun nullableMap(input: TerritoryReportHouseView?) = input?.let { map(it) }
}