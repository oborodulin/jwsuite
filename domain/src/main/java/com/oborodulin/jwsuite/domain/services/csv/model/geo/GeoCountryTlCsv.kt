package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoCountryTlCsv(
    @CsvBindByName val countryTlId: UUID,
    @CsvBindByName val countryLocCode: String,
    @CsvBindByName val countryTlCode: String?,
    @CsvBindByName val countryName: String,
    @CsvBindByName val countriesId: UUID
) : Exportable, Importable
