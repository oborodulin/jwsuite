package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class CongregationCsv(
    @CsvBindByName val congregationId: UUID,
    @CsvBindByName val congregationNum: String,
    @CsvBindByName val congregationName: String,
    @CsvBindByName val territoryMark: String,
    @CsvBindByName val isFavorite: Boolean = false,
    @CsvBindByName val lastVisitDate: OffsetDateTime? = null,
    @CsvBindByName val cLocalitiesId: UUID
) : Exportable, Importable