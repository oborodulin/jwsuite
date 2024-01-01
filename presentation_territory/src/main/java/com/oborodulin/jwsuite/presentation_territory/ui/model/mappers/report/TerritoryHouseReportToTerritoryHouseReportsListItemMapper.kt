package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHouseReportsListItem
import java.util.UUID

class TerritoryHouseReportToTerritoryHouseReportsListItemMapper :
    Mapper<TerritoryReportHouse, TerritoryHouseReportsListItem> {
    override fun map(input: TerritoryReportHouse) = TerritoryHouseReportsListItem(
        id = input.id ?: UUID.randomUUID(),
        houseNum = input.house.houseNum,
        houseFullNum = input.house.houseFullNum,
        streetFullName = input.house.street.streetFullName,
        territoryMemberId = input.territoryMemberReport.territoryMemberId,
        territoryShortMark = input.territoryMemberReport.territoryShortMark,
        languageCode = input.territoryMemberReport.languageCode,
        genderInfo = input.territoryMemberReport.genderInfo,
        ageInfo = input.territoryMemberReport.ageInfo,
        isProcessed = input.territoryMemberReport.isProcessed
    )
}