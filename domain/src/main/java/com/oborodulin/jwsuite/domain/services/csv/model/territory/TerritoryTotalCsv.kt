package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class TerritoryTotalCsv(
    @CsvBindByName val territoryTotalId: UUID,
    @CsvBindByName val lastVisitDate: OffsetDateTime? = null,
    @CsvBindByName val totalQty: Int,
    @CsvBindByName val totalIssued: Int,
    @CsvBindByName val totalProcessed: Int,
    @CsvBindByName val ttlCongregationsId: UUID
) : Exportable