package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoryDistrictView
import com.oborodulin.jwsuite.domain.model.TerritoryDistrict

class TerritoryDistrictViewListToTerritoryDistrictListMapper(mapper: TerritoryDistrictViewToTerritoryDistrictMapper) :
    ListMapperImpl<TerritoryDistrictView, TerritoryDistrict>(mapper)