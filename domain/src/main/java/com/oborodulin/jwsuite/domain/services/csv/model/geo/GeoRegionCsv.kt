package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoRegionCsv(
    @CsvBindByName val regionId: UUID,
    @CsvBindByName val regionCode: String
) : Exportable, Importable
