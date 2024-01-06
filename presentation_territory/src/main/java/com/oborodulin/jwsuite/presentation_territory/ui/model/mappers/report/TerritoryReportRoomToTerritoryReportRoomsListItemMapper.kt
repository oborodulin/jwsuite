package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportRoomsListItem
import java.util.UUID

class TerritoryReportRoomToTerritoryReportRoomsListItemMapper :
    Mapper<TerritoryReportRoom, TerritoryReportRoomsListItem> {
    override fun map(input: TerritoryReportRoom) = TerritoryReportRoomsListItem(
        id = input.room.id ?: UUID.randomUUID(),
        roomNum = input.room.roomNum,
        houseFullNum = input.room.house.houseFullNum,
        streetFullName = input.room.house.street.streetFullName,
        territoryMemberReportId = input.territoryMemberReport.id,
        territoryMemberId = input.territoryMemberReport.territoryMemberId,
        territoryShortMark = input.territoryMemberReport.territoryShortMark,
        languageInfo = input.territoryMemberReport.languageInfo,
        personInfo = input.territoryMemberReport.personInfo,
        isProcessed = input.territoryMemberReport.isProcessed
    )
}