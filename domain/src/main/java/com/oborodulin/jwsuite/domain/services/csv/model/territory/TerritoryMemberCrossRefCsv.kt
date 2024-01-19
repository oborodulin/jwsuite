package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class TerritoryMemberCrossRefCsv(
    @CsvBindByName val territoryMemberId: UUID,
    @CsvBindByName val receivingDate: OffsetDateTime,
    @CsvBindByName val deliveryDate: OffsetDateTime? = null,
    @CsvBindByName val tmcTerritoriesId: UUID,
    @CsvBindByName val tmcMembersId: UUID
) : Exportable, Importable
