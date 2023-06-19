package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.TerritoryCategory

class TerritoryCategoryEntityListToTerritoryCategoryListMapper(mapper: TerritoryCategoryEntityToTerritoryCategoryMapper) :
    ListMapperImpl<TerritoryCategoryEntity, TerritoryCategory>(mapper)