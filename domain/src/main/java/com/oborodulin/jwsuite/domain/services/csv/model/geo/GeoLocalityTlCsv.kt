package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoLocalityTlCsv(
    @CsvBindByName val localityTlId: UUID,
    @CsvBindByName val localityLocCode: String,
    @CsvBindByName val localityShortName: String,
    @CsvBindByName val localityName: String,
    @CsvBindByName val localitiesId: UUID,
) : Exportable
