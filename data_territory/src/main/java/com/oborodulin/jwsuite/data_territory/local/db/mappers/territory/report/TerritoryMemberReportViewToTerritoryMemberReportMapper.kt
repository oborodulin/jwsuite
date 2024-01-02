package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomViewToRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport

class TerritoryMemberReportViewToTerritoryMemberReportMapper(
    private val territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
    private val houseMapper: HouseViewToHouseMapper,
    private val roomMapper: RoomViewToRoomMapper,
    private val territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
) : Mapper<TerritoryMemberReportView, TerritoryMemberReport>,
    NullableMapper<TerritoryMemberReportView, TerritoryMemberReport> {
    override fun map(input: TerritoryMemberReportView) =
        territoryReportMapper.map(input.territoryReport).copy(
            deliveryDate = input.territoryMember.deliveryDate,
            territoryStreet = territoryStreetMapper.nullableMap(input.territoryStreet),
            house = houseMapper.nullableMap(input.house),
            room = roomMapper.nullableMap(input.room)
        )

    override fun nullableMap(input: TerritoryMemberReportView?) = input?.let { map(it) }
}