package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.RegionDistrictType
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal
import java.util.UUID

data class GeoRegionDistrictCsv(
    @CsvBindByName val regionDistrictId: UUID,
    @CsvBindByName val regDistrictShortName: String,
    @CsvBindByName val regDistrictType: RegionDistrictType,
    @CsvBindByName val regDistrictOsmId: Long? = null,
    @CsvBindByName val latitude: BigDecimal? = null,
    @CsvBindByName val longitude: BigDecimal? = null,
    @CsvBindByName val rRegionsId: UUID
) : Exportable, Importable
