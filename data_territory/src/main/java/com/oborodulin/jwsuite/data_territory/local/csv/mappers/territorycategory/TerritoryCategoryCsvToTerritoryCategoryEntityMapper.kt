package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCategoryCsv

class TerritoryCategoryCsvToTerritoryCategoryEntityMapper :
    Mapper<TerritoryCategoryCsv, TerritoryCategoryEntity> {
    override fun map(input: TerritoryCategoryCsv) = TerritoryCategoryEntity(
        territoryCategoryId = input.territoryCategoryId,
        territoryCategoryCode = input.territoryCategoryCode,
        territoryCategoryMark = input.territoryCategoryMark,
        territoryCategoryName = input.territoryCategoryName
    )
}