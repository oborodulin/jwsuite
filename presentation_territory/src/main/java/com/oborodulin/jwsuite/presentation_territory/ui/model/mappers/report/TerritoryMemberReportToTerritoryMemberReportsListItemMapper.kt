package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportsListItem
import java.util.UUID

class TerritoryMemberReportToTerritoryMemberReportsListItemMapper :
    Mapper<TerritoryMemberReport, TerritoryMemberReportsListItem> {
    override fun map(input: TerritoryMemberReport) = TerritoryMemberReportsListItem(
        id = input.id ?: UUID.randomUUID(),
        deliveryDate = input.deliveryDate,
        territoryShortMark = input.territoryShortMark,
        languageCode = input.languageCode,
        genderInfo = input.genderInfo,
        ageInfo = input.ageInfo
    )
}