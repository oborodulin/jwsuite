package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GroupCsv(
    @CsvBindByName val groupId: UUID,
    @CsvBindByName val groupNum: Int,
    @CsvBindByName val gCongregationsId: UUID
) : Exportable