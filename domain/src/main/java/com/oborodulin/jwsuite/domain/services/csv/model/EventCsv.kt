package com.oborodulin.jwsuite.domain.services.csv.model

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.EventType
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class EventCsv(
    @CsvBindByName val eventId: UUID,
    @CsvBindByName val eventType: EventType,
    @CsvBindByName val eventTime: OffsetDateTime,
    @CsvBindByName val isManual: Boolean,
    @CsvBindByName val isSuccess: Boolean
) : Exportable, Importable
