package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity

@DatabaseView(
    viewName = TerritoryMemberReportView.VIEW_NAME,
    value = """
SELECT tmc.*, tmr.*, tsv.*, hv.*, rv.*, 
    LTRIM(RTRIM((CASE WHEN m.surname IS NOT NULL THEN m.surname || ' ' ELSE '' END) ||
    (CASE WHEN m.memberName IS NOT NULL THEN SUBSTRING(m.memberName, 1, 1) || '.' ELSE '' END) ||
    (CASE WHEN m.patronymic IS NOT NULL THEN SUBSTRING(m.patronymic, 1, 1) || '.' ELSE '' END)) ||
    ' [' || m.pseudonym || ']') AS memberShortName 
FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc JOIN ${TerritoryMemberReportEntity.TABLE_NAME} tmr 
        ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId
    JOIN ${MemberEntity.TABLE_NAME} m ON m.memberId = tmc.tmcMembersId
    LEFT JOIN ${TerritoryStreetView.VIEW_NAME} tsv ON tsv.territoryStreetId = tmr.tmrTerritoryStreetsId
    LEFT JOIN ${HouseView.VIEW_NAME} hv ON hv.houseId = tmr.tmrHousesId
    LEFT JOIN ${RoomView.VIEW_NAME} rv ON rv.roomId = tmr.tmrRoomsId
"""
)
class TerritoryMemberReportView(
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val territoryReport: TerritoryMemberReportEntity,
    @Embedded val territoryStreet: TerritoryStreetView?,
    @Embedded val house: HouseView?,
    @Embedded val room: RoomView?,
    val memberShortName: String
) {
    companion object {
        const val VIEW_NAME = "territory_member_reports_view"
    }
}