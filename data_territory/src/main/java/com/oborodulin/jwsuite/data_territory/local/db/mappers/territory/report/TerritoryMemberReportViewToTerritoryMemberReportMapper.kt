package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport

class TerritoryMemberReportViewToTerritoryMemberReportMapper(
    private val mapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
) : Mapper<TerritoryMemberReportView, TerritoryMemberReport>,
    NullableMapper<TerritoryMemberReportView, TerritoryMemberReport> {
    override fun map(input: TerritoryMemberReportView) =
        mapper.map(input.territoryReport).copy(deliveryDate = input.territoryMember.deliveryDate)

    override fun nullableMap(input: TerritoryMemberReportView?) =
        input?.let { map(it) }
}