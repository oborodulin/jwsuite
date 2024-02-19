package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomUiToRoomMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetUiToTerritoryStreetMapper

class TerritoryMemberReportUiToTerritoryMemberReportMapper(
    private val territoryStreetUiMapper: TerritoryStreetUiToTerritoryStreetMapper,
    private val houseUiMapper: HouseUiToHouseMapper,
    private val roomUiMapper: RoomUiToRoomMapper
) : Mapper<TerritoryMemberReportUi, TerritoryMemberReport>,
    NullableMapper<TerritoryMemberReportUi, TerritoryMemberReport> {
    override fun map(input: TerritoryMemberReportUi) = TerritoryMemberReport(
        territoryStreet = territoryStreetUiMapper.nullableMap(input.territoryStreet),
        house = houseUiMapper.nullableMap(input.house),
        room = roomUiMapper.nullableMap(input.room),
        territoryMemberId = input.territoryMemberId,
        territoryReportMark = input.territoryReportMark,
        languageCode = input.languageCode,
        gender = input.gender,
        age = input.age,
        isProcessed = input.isProcessed,
        territoryReportDesc = input.territoryReportDesc
    ).also { it.id = input.id }

    override fun nullableMap(input: TerritoryMemberReportUi?) = input?.let { map(it) }
}