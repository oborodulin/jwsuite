package com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory

class TerritoryCategoryEntityListToTerritoryCategoriesListMapper(mapper: TerritoryCategoryEntityToTerritoryCategoryMapper) :
    ListMapperImpl<TerritoryCategoryEntity, TerritoryCategory>(mapper)