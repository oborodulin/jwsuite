package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoMicrodistrictTlCsv(
    @CsvBindByName val microdistrictTlId: UUID,
    @CsvBindByName val microdistrictLocCode: String,
    @CsvBindByName val microdistrictName: String,
    @CsvBindByName val microdistrictsId: UUID
) : Exportable, Importable
