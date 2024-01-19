package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoStreetDistrictCsv(
    @CsvBindByName val streetDistrictId: UUID,
    @CsvBindByName val dsStreetsId: UUID,
    @CsvBindByName val dsLocalityDistrictsId: UUID,
    @CsvBindByName val dsMicrodistrictsId: UUID? = null
) : Exportable, Importable
