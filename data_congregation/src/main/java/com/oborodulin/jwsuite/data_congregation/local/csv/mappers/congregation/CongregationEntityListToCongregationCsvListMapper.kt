package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv

class CongregationEntityListToCongregationCsvListMapper(mapper: CongregationEntityToCongregationCsvMapper) :
    ListMapperImpl<CongregationEntity, CongregationCsv>(mapper)