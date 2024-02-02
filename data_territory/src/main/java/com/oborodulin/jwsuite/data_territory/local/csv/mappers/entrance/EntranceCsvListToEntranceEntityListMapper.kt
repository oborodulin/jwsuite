package com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.EntranceCsv

class EntranceCsvListToEntranceEntityListMapper(mapper: EntranceCsvToEntranceEntityMapper) :
    ListMapperImpl<EntranceCsv, EntranceEntity>(mapper)