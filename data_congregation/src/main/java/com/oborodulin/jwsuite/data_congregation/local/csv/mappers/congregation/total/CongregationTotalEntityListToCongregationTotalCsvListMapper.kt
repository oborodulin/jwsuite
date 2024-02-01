package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv

class CongregationTotalEntityListToCongregationTotalCsvListMapper(mapper: CongregationTotalEntityToCongregationTotalCsvMapper) :
    ListMapperImpl<CongregationTotalEntity, CongregationTotalCsv>(mapper)