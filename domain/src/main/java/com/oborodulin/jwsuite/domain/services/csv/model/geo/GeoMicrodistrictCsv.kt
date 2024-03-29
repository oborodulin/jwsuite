package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.MicrodistrictType
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal
import java.util.UUID

data class GeoMicrodistrictCsv(
    @CsvBindByName val microdistrictId: UUID,
    @CsvBindByName val microdistrictType: MicrodistrictType,
    @CsvBindByName val microdistrictShortName: String,
    @CsvBindByName val microdistrictOsmId: Long? = null,
    @CsvBindByName val latitude: BigDecimal? = null,
    @CsvBindByName val longitude: BigDecimal? = null,
    @CsvBindByName val mLocalityDistrictsId: UUID,
    @CsvBindByName val mLocalitiesId: UUID
) : Exportable, Importable
