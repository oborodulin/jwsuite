package com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv

class CongregationCsvListToCongregationEntityListMapper(mapper: CongregationCsvToCongregationEntityMapper) :
    ListMapperImpl<CongregationCsv, CongregationEntity>(mapper)