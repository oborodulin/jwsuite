package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportRoomsListItem

class TerritoryReportRoomsListToTerritoryReportRoomsListItemMapper(mapper: TerritoryReportRoomToTerritoryReportRoomsListItemMapper) :
    ListMapperImpl<TerritoryReportRoom, TerritoryReportRoomsListItem>(mapper)