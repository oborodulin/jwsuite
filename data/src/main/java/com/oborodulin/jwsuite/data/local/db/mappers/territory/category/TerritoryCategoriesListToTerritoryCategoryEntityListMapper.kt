package com.oborodulin.jwsuite.data.local.db.mappers.territory.category

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.TerritoryCategory

class TerritoryCategoriesListToTerritoryCategoryEntityListMapper(mapper: TerritoryCategoryToTerritoryCategoryEntityMapper) :
    ListMapperImpl<TerritoryCategory, TerritoryCategoryEntity>(mapper)