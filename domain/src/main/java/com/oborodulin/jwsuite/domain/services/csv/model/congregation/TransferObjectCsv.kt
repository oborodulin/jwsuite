package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class TransferObjectCsv(
    @CsvBindByName val transferObjectId: UUID,
    @CsvBindByName val transferObjectType: TransferObjectType,
    @CsvBindByName val transferObjectName: String
) : Exportable, Importable