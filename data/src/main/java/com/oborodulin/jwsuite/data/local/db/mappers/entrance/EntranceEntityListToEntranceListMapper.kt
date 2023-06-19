package com.oborodulin.jwsuite.data.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.Entrance

class EntranceEntityListToEntranceListMapper(mapper: EntranceEntityToEntranceMapper) :
    ListMapperImpl<EntranceEntity, Entrance>(mapper)