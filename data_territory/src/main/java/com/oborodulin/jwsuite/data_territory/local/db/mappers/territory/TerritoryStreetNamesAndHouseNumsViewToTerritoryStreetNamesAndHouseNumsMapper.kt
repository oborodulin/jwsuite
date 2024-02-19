package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreetNamesAndHouseNums

class TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper :
    Mapper<TerritoryStreetNamesAndHouseNumsView, TerritoryStreetNamesAndHouseNums> {
    override fun map(input: TerritoryStreetNamesAndHouseNumsView) =
        TerritoryStreetNamesAndHouseNums(
            territoryId = input.territoryId,
            streetNames = input.streetNames,
            houseFullNums = input.houseFullNums
        ).also { it.id = input.territoryId }
}