package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryStreetDataSource
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoryStreetsRepositoryImpl @Inject constructor(
    private val localTerritoryDataSource: LocalTerritoryStreetDataSource,
    private val mappers: TerritoryStreetMappers
) : TerritoryStreetsRepository {
    override fun getTerritoryStreet(territoryStreetId: UUID) =
        localTerritoryDataSource.getTerritoryStreet(territoryStreetId)
            .map(mappers.territoryStreetViewToTerritoryStreetMapper::map)

    override fun getTerritoryStreets(territoryId: UUID) =
        localTerritoryDataSource.getTerritoryStreets(territoryId)
            .map(mappers.territoryStreetViewListToTerritoryStreetsListMapper::map)

    override fun getStreetsForTerritory(territoryId: UUID) =
        localTerritoryDataSource.getStreetsForTerritory(territoryId)
            .map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

    override fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID?) =
        localTerritoryDataSource.getTerritoryStreetNamesAndHouseNums(congregationId)
            .map(mappers.territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper::map)

    override fun saveTerritoryStreet(territoryStreet: TerritoryStreet) = flow {
        if (territoryStreet.id == null) {
            localTerritoryDataSource.insertStreet(
                mappers.territoryStreetToTerritoryStreetEntityMapper.map(territoryStreet),
            )
        } else {
            localTerritoryDataSource.updateStreet(
                mappers.territoryStreetToTerritoryStreetEntityMapper.map(territoryStreet),
            )
        }
        emit(territoryStreet)
    }

    override fun deleteTerritoryStreetById(territoryStreetId: UUID) = flow {
        localTerritoryDataSource.deleteStreet(territoryStreetId)
        this.emit(territoryStreetId)
    }
}