package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory

class TerritoryCategoriesListToTerritoryCategoryEntityListMapper(mapper: TerritoryCategoryToTerritoryCategoryEntityMapper) :
    ListMapperImpl<TerritoryCategory, TerritoryCategoryEntity>(mapper)