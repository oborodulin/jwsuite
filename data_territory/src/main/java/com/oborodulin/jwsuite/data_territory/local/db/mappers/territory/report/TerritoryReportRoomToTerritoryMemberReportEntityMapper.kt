package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom

class TerritoryReportRoomToTerritoryMemberReportEntityMapper(private val mapper: TerritoryMemberReportToTerritoryMemberReportEntityMapper) :
    Mapper<TerritoryReportRoom, TerritoryMemberReportEntity>,
    NullableMapper<TerritoryReportRoom, TerritoryMemberReportEntity> {
    override fun map(input: TerritoryReportRoom) =
        mapper.map(input.territoryMemberReport).copy(tmrRoomsId = input.room.id)

    override fun nullableMap(input: TerritoryReportRoom?) = input?.let { map(it) }
}