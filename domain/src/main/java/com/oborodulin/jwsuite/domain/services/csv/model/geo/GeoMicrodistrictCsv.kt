package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.types.VillageType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoMicrodistrictCsv(
    @CsvBindByName val microdistrictId: UUID,
    @CsvBindByName val microdistrictType: VillageType,
    @CsvBindByName val microdistrictShortName: String,
    @CsvBindByName val mLocalityDistrictsId: UUID,
    @CsvBindByName val mLocalitiesId: UUID
) : Exportable
