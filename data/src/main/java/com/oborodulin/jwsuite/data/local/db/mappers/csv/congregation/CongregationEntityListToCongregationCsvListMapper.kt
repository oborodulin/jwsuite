package com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity

class CongregationEntityListToCongregationCsvListMapper(mapper: CongregationEntityToCongregationCsvMapper) :
    ListMapperImpl<CongregationEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv>(mapper)