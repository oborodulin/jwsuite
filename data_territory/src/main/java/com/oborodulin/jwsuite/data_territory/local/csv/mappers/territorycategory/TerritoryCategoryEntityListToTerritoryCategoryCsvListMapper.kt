package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCategoryCsv

class TerritoryCategoryEntityListToTerritoryCategoryCsvListMapper(mapper: TerritoryCategoryEntityToTerritoryCategoryCsvMapper) :
    ListMapperImpl<TerritoryCategoryEntity, TerritoryCategoryCsv>(mapper)