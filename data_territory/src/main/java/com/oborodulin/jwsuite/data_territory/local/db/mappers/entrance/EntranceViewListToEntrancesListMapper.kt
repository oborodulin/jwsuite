package com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.EntranceView
import com.oborodulin.jwsuite.domain.model.territory.Entrance

class EntranceViewListToEntrancesListMapper(mapper: EntranceViewToEntranceMapper) :
    ListMapperImpl<EntranceView, Entrance>(mapper)