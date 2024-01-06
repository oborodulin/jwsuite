package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportStreetView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportStreet

class TerritoryReportStreetViewToTerritoryReportStreetMapper(
    private val territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
    private val territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
) : Mapper<TerritoryReportStreetView, TerritoryReportStreet>,
    NullableMapper<TerritoryReportStreetView, TerritoryReportStreet> {
    override fun map(input: TerritoryReportStreetView): TerritoryReportStreet {
        val territoryStreet = territoryStreetMapper.map(input.territoryStreet)
        val territoryMemberReport =
            territoryReportMapper.nullableMap(input.territoryReport) ?: TerritoryMemberReport(
                territoryStreet = territoryStreet,
                territoryId = input.territoryMember.tmcTerritoriesId,
                territoryMemberId = input.territoryMember.territoryMemberId
            )
        val territoryReportStreet = TerritoryReportStreet(
            territoryStreet = territoryStreet,
            territoryMemberReport = territoryMemberReport
        )
        territoryReportStreet.id = input.territoryStreet.territoryStreet.territoryStreetId
        return territoryReportStreet
    }

    override fun nullableMap(input: TerritoryReportStreetView?) = input?.let { map(it) }
}