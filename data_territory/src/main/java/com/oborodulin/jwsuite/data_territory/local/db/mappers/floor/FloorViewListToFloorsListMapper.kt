package com.oborodulin.jwsuite.data_territory.local.db.mappers.floor

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.FloorView
import com.oborodulin.jwsuite.domain.model.territory.Floor

class FloorViewListToFloorsListMapper(mapper: FloorViewToFloorMapper) :
    ListMapperImpl<FloorView, Floor>(mapper)