package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberReportCsv

class TerritoryMemberReportCsvListToTerritoryMemberReportEntityListMapper(mapper: TerritoryMemberReportCsvToTerritoryMemberReportEntityMapper) :
    ListMapperImpl<TerritoryMemberReportCsv, TerritoryMemberReportEntity>(mapper)