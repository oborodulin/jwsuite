package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetToTerritoryStreetUiMapper

class TerritoryMemberReportToTerritoryMemberReportUiMapper(
    private val territoryStreetMapper: TerritoryStreetToTerritoryStreetUiMapper,
    private val houseMapper: HouseToHouseUiMapper,
    private val roomMapper: RoomToRoomUiMapper
) : Mapper<TerritoryMemberReport, TerritoryMemberReportUi>,
    NullableMapper<TerritoryMemberReport, TerritoryMemberReportUi> {
    override fun map(input: TerritoryMemberReport) = TerritoryMemberReportUi(
        territoryStreet = territoryStreetMapper.nullableMap(input.territoryStreet),
        house = houseMapper.nullableMap(input.house),
        room = roomMapper.nullableMap(input.room),
        territoryId = input.territoryId,
        territoryMemberId = input.territoryMemberId,
        territoryReportMark = input.territoryReportMark,
        languageCode = input.languageCode,
        gender = input.gender,
        age = input.age,
        isProcessed = input.isProcessed,
        territoryReportDesc = input.territoryReportDesc
    ).also { it.id = input.id }

    override fun nullableMap(input: TerritoryMemberReport?) = input?.let { map(it) }
}