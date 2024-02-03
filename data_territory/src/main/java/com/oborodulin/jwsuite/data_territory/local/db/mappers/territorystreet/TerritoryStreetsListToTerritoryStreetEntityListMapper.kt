package com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet

class TerritoryStreetsListToTerritoryStreetEntityListMapper(mapper: TerritoryStreetToTerritoryStreetEntityMapper) :
    ListMapperImpl<TerritoryStreet, TerritoryStreetEntity>(mapper)