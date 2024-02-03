package com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse

class TerritoryReportHouseToTerritoryMemberReportEntityMapper(private val mapper: TerritoryMemberReportToTerritoryMemberReportEntityMapper) :
    Mapper<TerritoryReportHouse, TerritoryMemberReportEntity>,
    NullableMapper<TerritoryReportHouse, TerritoryMemberReportEntity> {
    override fun map(input: TerritoryReportHouse) =
        mapper.map(input.territoryMemberReport).copy(tmrHousesId = input.house.id)

    override fun nullableMap(input: TerritoryReportHouse?) = input?.let { map(it) }
}