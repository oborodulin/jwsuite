package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal
import java.util.UUID

data class GeoLocalityDistrictCsv(
    @CsvBindByName val localityDistrictId: UUID,
    @CsvBindByName val locDistrictShortName: String,
    @CsvBindByName val locDistrictOsmId: Long? = null,
    @CsvBindByName val latitude: BigDecimal? = null,
    @CsvBindByName val longitude: BigDecimal? = null,
    @CsvBindByName val ldLocalitiesId: UUID
) : Exportable, Importable
