package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoLocalityDistrictTlCsv(
    @CsvBindByName val localityDistrictTlId: UUID,
    @CsvBindByName val locDistrictLocCode: String,
    @CsvBindByName val locDistrictName: String,
    @CsvBindByName val localityDistrictsId: UUID
) : Exportable, Importable
