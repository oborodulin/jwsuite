package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class CongregationTotalCsv(
    @CsvBindByName val congregationTotalId: UUID,
    @CsvBindByName val lastVisitDate: OffsetDateTime? = null,
    @CsvBindByName val totalMembers: Int,
    @CsvBindByName val totalFulltimeMembers: Int,
    @CsvBindByName val totalTerritories: Int,
    @CsvBindByName val totalTerritoryIssued: Int,
    @CsvBindByName val totalTerritoryProcessed: Int,
    @CsvBindByName val ctlCongregationsId: UUID
) : Exportable, Importable
