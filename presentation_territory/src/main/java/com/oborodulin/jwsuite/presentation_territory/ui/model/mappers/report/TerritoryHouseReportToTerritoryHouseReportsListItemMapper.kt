package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryHouseReport
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHouseReportsListItem
import java.util.UUID

class TerritoryHouseReportToTerritoryHouseReportsListItemMapper :
    Mapper<TerritoryHouseReport, TerritoryHouseReportsListItem> {
    override fun map(input: TerritoryHouseReport) = TerritoryHouseReportsListItem(
        id = input.id ?: UUID.randomUUID(),
        houseNum = input.house.houseNum,
        houseFullNum = input.house.houseFullNum,
        streetFullName = input.house.street.streetFullName,
        territoryMemberId = input.territoryReport.territoryMemberId,
        territoryShortMark = input.territoryReport.territoryShortMark,
        languageCode = input.territoryReport.languageCode,
        genderInfo = input.territoryReport.genderInfo,
        ageInfo = input.territoryReport.ageInfo,
        isProcessed = input.territoryReport.isProcessed
    )
}