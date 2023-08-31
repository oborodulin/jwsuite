package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.model.TerritoryStreet

class TerritoryStreetsListToTerritoryStreetEntityListMapper(mapper: TerritoryStreetToTerritoryStreetEntityMapper) :
    ListMapperImpl<TerritoryStreet, TerritoryStreetEntity>(mapper)