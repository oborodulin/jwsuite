package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.MemberType
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class MemberMovementCsv(
    @CsvBindByName val memberMovementId: UUID,
    @CsvBindByName val memberType: MemberType,
    @CsvBindByName val movementDate: OffsetDateTime,
    @CsvBindByName val mMembersId: UUID
) : Exportable, Importable
