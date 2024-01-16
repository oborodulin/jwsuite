package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class MemberCongregationCrossRefCsv(
    @CsvBindByName val memberCongregationId: UUID,
    @CsvBindByName val activityDate: OffsetDateTime,
    @CsvBindByName val mcMembersId: UUID,
    @CsvBindByName val mcCongregationsId: UUID
) : Exportable
