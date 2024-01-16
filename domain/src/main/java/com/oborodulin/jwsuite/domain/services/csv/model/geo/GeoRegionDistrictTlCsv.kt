package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoRegionDistrictTlCsv(
    @CsvBindByName val regionDistrictTlId: UUID,
    @CsvBindByName val regDistrictLocCode: String,
    @CsvBindByName val regDistrictTlShortName: String? = null,
    @CsvBindByName val regDistrictName: String,
    @CsvBindByName val regionDistrictsId: UUID
) : Exportable
