package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberReportCsv

class TerritoryMemberReportEntityToTerritoryMemberReportCsvMapper :
    Mapper<TerritoryMemberReportEntity, TerritoryMemberReportCsv> {
    override fun map(input: TerritoryMemberReportEntity) = TerritoryMemberReportCsv(
        territoryMemberReportId = input.territoryMemberReportId,
        territoryReportMark = input.territoryReportMark,
        languageCode = input.languageCode,
        gender = input.gender,
        age = input.age,
        isReportProcessed = input.isReportProcessed,
        territoryReportDesc = input.territoryReportDesc,
        tmrRoomsId = input.tmrRoomsId,
        tmrHousesId = input.tmrHousesId,
        tmrTerritoryStreetsId = input.tmrTerritoryStreetsId,
        tmrTerritoryMembersId = input.tmrTerritoryMembersId
    )
}