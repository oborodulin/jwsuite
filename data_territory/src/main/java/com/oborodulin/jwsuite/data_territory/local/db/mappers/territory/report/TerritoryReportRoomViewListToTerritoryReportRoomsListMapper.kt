package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom

class TerritoryReportRoomViewListToTerritoryReportRoomsListMapper(mapper: TerritoryReportRoomViewToTerritoryReportRoomMapper) :
    ListMapperImpl<TerritoryReportRoomView, TerritoryReportRoom>(mapper)