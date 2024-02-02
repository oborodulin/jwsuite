package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCategoryCsv

class TerritoryCategoryEntityToTerritoryCategoryCsvMapper :
    Mapper<TerritoryCategoryEntity, TerritoryCategoryCsv> {
    override fun map(input: TerritoryCategoryEntity) = TerritoryCategoryCsv(
        territoryCategoryId = input.territoryCategoryId,
        territoryCategoryCode = input.territoryCategoryCode,
        territoryCategoryMark = input.territoryCategoryMark,
        territoryCategoryName = input.territoryCategoryName
    )
}