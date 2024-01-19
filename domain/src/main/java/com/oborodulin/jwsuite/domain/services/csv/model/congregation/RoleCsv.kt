package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class RoleCsv(
    @CsvBindByName val roleId: UUID,
    @CsvBindByName val roleType: MemberRoleType,
    @CsvBindByName val roleName: String
) : Exportable, Importable
