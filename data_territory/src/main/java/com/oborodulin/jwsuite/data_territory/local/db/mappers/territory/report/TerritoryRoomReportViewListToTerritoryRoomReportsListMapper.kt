package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryRoomReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryRoomReport

class TerritoryRoomReportViewListToTerritoryRoomReportsListMapper(mapper: TerritoryRoomReportViewToTerritoryRoomReportMapper) :
    ListMapperImpl<TerritoryRoomReportView, TerritoryRoomReport>(mapper)