package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class MemberRoleCsv(
    @CsvBindByName val memberRoleId: UUID,
    @CsvBindByName val roleExpiredDate: OffsetDateTime? = null,
    @CsvBindByName val mrMembersId: UUID,
    @CsvBindByName val mrRolesId: UUID
) : Exportable, Importable
