package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoStreetTlCsv(
    @CsvBindByName val streetTlId: UUID,
    @CsvBindByName val streetLocCode: String,
    @CsvBindByName val streetName: String,
    @CsvBindByName val streetsId: UUID
) : Exportable, Importable
