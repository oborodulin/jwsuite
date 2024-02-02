package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class CongregationTerritoryCrossRefCsv(
    @CsvBindByName val congregationTerritoryId: UUID,
    @CsvBindByName val startUsingDate: OffsetDateTime,
    @CsvBindByName val endUsingDate: OffsetDateTime?,
    @CsvBindByName val ctTerritoriesId: UUID,
    @CsvBindByName val ctCongregationsId: UUID
) : Exportable, Importable
