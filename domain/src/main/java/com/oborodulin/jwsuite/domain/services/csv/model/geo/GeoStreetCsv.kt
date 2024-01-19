package com.oborodulin.jwsuite.domain.services.csv.model.geo

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.oborodulin.jwsuite.domain.types.RoadType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class GeoStreetCsv(
    @CsvBindByName val streetId: UUID,
    @CsvBindByName val streetHashCode: Int,
    @CsvBindByName val roadType: RoadType,
    @CsvBindByName val isStreetPrivateSector: Boolean,
    @CsvBindByName val estStreetHouses: Int? = null,
    @CsvBindByName val sLocalitiesId: UUID
) : Exportable, Importable
