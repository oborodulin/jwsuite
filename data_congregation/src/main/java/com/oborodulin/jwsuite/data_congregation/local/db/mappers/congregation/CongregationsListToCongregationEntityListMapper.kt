package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.domain.model.Congregation

class CongregationsListToCongregationEntityListMapper(mapper: CongregationToCongregationEntityMapper) :
    ListMapperImpl<Congregation, CongregationEntity>(mapper)