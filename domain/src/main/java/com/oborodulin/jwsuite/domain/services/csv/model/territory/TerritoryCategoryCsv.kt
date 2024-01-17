package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.types.TerritoryCategoryType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class TerritoryCategoryCsv(
    @CsvBindByName val territoryCategoryId: UUID,
    @CsvBindByName val territoryCategoryCode: TerritoryCategoryType,
    @CsvBindByName val territoryCategoryMark: String,
    @CsvBindByName val territoryCategoryName: String
) : Exportable
