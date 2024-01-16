package com.oborodulin.jwsuite.data.local.db.mappers.csv

import com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation.CongregationCsvListToCongregationEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation.CongregationCsvToCongregationEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation.CongregationTotalViewToCongregationTotalsMapper

data class CsvMappers(
    val congregationCsvListToCongregationEntityListMapper: CongregationCsvListToCongregationEntityListMapper,
    val congregationCsvToCongregationEntityMapper: CongregationCsvToCongregationEntityMapper,
    val congregationTotalViewToCongregationTotalsMapper: CongregationTotalViewToCongregationTotalsMapper
)
