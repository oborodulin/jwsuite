package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.types.LocalityType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoLocalityCsv(
    @CsvBindByName val localityId: UUID,
    @CsvBindByName val localityCode: String,
    @CsvBindByName val localityType: LocalityType,
    @CsvBindByName val lRegionDistrictsId: UUID? = null,
    @CsvBindByName val lRegionsId: UUID
) : Exportable