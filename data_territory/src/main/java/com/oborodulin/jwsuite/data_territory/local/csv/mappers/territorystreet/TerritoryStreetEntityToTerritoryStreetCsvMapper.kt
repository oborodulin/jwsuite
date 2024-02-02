package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryStreetCsv

class TerritoryStreetEntityToTerritoryStreetCsvMapper :
    Mapper<TerritoryStreetEntity, TerritoryStreetCsv> {
    override fun map(input: TerritoryStreetEntity) = TerritoryStreetCsv(
        territoryStreetId = input.territoryStreetId,
        isEvenSide = input.isEvenSide,
        isTerStreetPrivateSector = input.isTerStreetPrivateSector,
        estTerStreetHouses = input.estTerStreetHouses,
        tsStreetsId = input.tsStreetsId,
        tsTerritoriesId = input.tsTerritoriesId
    )
}