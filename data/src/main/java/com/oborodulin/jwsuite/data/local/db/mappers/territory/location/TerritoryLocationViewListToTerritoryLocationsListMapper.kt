package com.oborodulin.jwsuite.data.local.db.mappers.territory.location

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.domain.model.TerritoryLocation

class TerritoryLocationViewListToTerritoryLocationsListMapper(mapper: TerritoryLocationViewToTerritoryLocationMapper) :
    ListMapperImpl<TerritoryLocationView, TerritoryLocation>(mapper)