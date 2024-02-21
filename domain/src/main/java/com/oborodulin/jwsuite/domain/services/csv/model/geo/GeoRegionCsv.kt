package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal
import java.util.UUID

data class GeoRegionCsv(
    @CsvBindByName val regionId: UUID,
    @CsvBindByName val regionCode: String,
    @CsvBindByName val regionOsmId: Long? = null,
    @CsvBindByName val latitude: BigDecimal? = null,
    @CsvBindByName val longitude: BigDecimal? = null
) : Exportable, Importable
