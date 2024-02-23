package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal
import java.util.UUID

data class GeoCountryCsv(
    @CsvBindByName val countryId: UUID,
    @CsvBindByName val countryCode: String,
    @CsvBindByName val countryGeocode: String? = null,
    @CsvBindByName val countryOsmId: Long? = null,
    @CsvBindByName val latitude: BigDecimal? = null,
    @CsvBindByName val longitude: BigDecimal? = null
) : Exportable, Importable
