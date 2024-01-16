package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoRegionTlCsv(
    @CsvBindByName val regionTlId: UUID,
    @CsvBindByName val regionLocCode: String,
    @CsvBindByName val regionTlCode: String? = null,
    @CsvBindByName val regionName: String,
    @CsvBindByName val regionsId: UUID
) : Exportable
