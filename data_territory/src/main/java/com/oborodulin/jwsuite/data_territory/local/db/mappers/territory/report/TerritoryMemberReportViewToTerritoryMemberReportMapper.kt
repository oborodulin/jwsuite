package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport

class TerritoryMemberReportViewToTerritoryMemberReportMapper(
    private val territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
) : Mapper<TerritoryMemberReportView, TerritoryMemberReport>,
    NullableMapper<TerritoryMemberReportView, TerritoryMemberReport> {
    override fun map(input: TerritoryMemberReportView) =
        territoryReportMapper.map(input.territoryReport).copy(
            deliveryDate = input.territoryMember.deliveryDate,
            memberShortName = input.memberShortName,
            territoryId = input.territoryMember.tmcTerritoriesId,
        )

    override fun nullableMap(input: TerritoryMemberReportView?) = input?.let { map(it) }
}