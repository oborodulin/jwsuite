package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoriesListItem

class TerritoryCategoriesListToTerritoryCategoriesListItemMapper(mapper: TerritoryCategoryToTerritoryCategoriesListItemMapper) :
    ListMapperImpl<TerritoryCategory, TerritoryCategoriesListItem>(mapper)