package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoLocalityDistrictCsv(
    @CsvBindByName val localityDistrictId: UUID,
    @CsvBindByName val locDistrictShortName: String,
    @CsvBindByName val ldLocalitiesId: UUID
) : Exportable
