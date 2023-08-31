package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.domain.model.TerritoryStreetNamesAndHouseNums

class TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper :
    Mapper<TerritoryStreetNamesAndHouseNumsView, TerritoryStreetNamesAndHouseNums> {
    override fun map(input: TerritoryStreetNamesAndHouseNumsView): TerritoryStreetNamesAndHouseNums {
        val streetNamesAndHouseNums = TerritoryStreetNamesAndHouseNums(
            territoryId = input.territoryId,
            streetNames = input.streetNames,
            houseFullNums = input.houseFullNums
        )
        streetNamesAndHouseNums.id = input.territoryId
        return streetNamesAndHouseNums
    }
}