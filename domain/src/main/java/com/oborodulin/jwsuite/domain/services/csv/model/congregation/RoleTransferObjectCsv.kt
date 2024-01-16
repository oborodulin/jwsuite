package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class RoleTransferObjectCsv(
    @CsvBindByName val roleTransferObjectId: UUID,
    @CsvBindByName val isPersonalData: Boolean,
    @CsvBindByName val rtoRolesId: UUID,
    @CsvBindByName val rtoTransferObjectsId: UUID
) : Exportable
