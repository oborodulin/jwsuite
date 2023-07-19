package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalEntranceDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalRoomDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryDataSource
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.util.TerritoryDistrictType
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoriesRepositoryImpl @Inject constructor(
    private val localTerritoryDataSource: LocalTerritoryDataSource,
    private val localGeoStreetDataSource: LocalGeoStreetDataSource,
    private val localHouseDataSource: LocalHouseDataSource,
    private val localEntranceDataSource: LocalEntranceDataSource,
    private val localFloorDataSource: LocalFloorDataSource,
    private val localRoomDataSource: LocalRoomDataSource,
    private val mappers: TerritoryMappers
) : TerritoriesRepository {
    override fun getCongregationTerritories(congregationId: UUID?) =
        when (congregationId) {
            null -> localTerritoryDataSource.getFavoriteCongregationTerritories()
            else -> localTerritoryDataSource.getCongregationTerritories(congregationId)
        }.map(mappers.territoryViewListToTerritoriesListMapper::map)

    override fun getCongregationTerritoryDistricts(isPrivateSector: Boolean, congregationId: UUID) =
        localTerritoryDataSource.getCongregationTerritoryDistricts(isPrivateSector, congregationId)
            .map(mappers.territoryDistrictViewListToTerritoryDistrictListMapper::map)

    override fun getDistrictTerritories(
        territoryDistrictType: TerritoryDistrictType, districtId: UUID, isPrivateSector: Boolean,
        congregationId: UUID?
    ) = when (territoryDistrictType) {
        TerritoryDistrictType.LOCALITY -> localTerritoryDataSource.getLocalityTerritories(
            districtId, isPrivateSector, congregationId
        )

        TerritoryDistrictType.LOCALITY_DISTRICT -> localTerritoryDataSource.getLocalityDistrictTerritories(
            districtId, isPrivateSector, congregationId
        )

        TerritoryDistrictType.MICRO_DISTRICT -> localTerritoryDataSource.getMicrodistrictTerritories(
            districtId, isPrivateSector, congregationId
        )
        TerritoryDistrictType.ALL -> when (congregationId) {
            null -> localTerritoryDataSource.getFavoriteCongregationTerritories(isPrivateSector)
            else -> localTerritoryDataSource.getCongregationTerritories(congregationId, isPrivateSector)
        }
    }.map(mappers.territoryViewListToTerritoriesListMapper::map)

    override fun getTerritoryStreets(territoryId: UUID) =
        localGeoStreetDataSource.getTerritoryStreets(territoryId)
            .map(mappers.territoryStreetViewListToTerritoryStreetsListMapper::map)

    override fun getHouses(territoryId: UUID) = localHouseDataSource.getTerritoryHouses(territoryId)
        .map(mappers.houseViewListToHousesListMapper::map)

    override fun getEntrances(territoryId: UUID) =
        localEntranceDataSource.getTerritoryEntrances(territoryId)
            .map(mappers.entranceEntityListToEntrancesListMapper::map)

    override fun getFloors(territoryId: UUID) = localFloorDataSource.getTerritoryFloors(territoryId)
        .map(mappers.floorEntityListToFloorsListMapper::map)

    override fun getRooms(territoryId: UUID) = localRoomDataSource.getTerritoryRooms(territoryId)
        .map(mappers.roomEntityListToRoomsListMapper::map)

    override fun get(territoryId: UUID) =
        localTerritoryDataSource.getTerritory(territoryId)
            .map(mappers.territoryViewToTerritoryMapper::map)

    override fun save(territory: Territory) = flow {
        if (territory.id == null) {
            localTerritoryDataSource.insertTerritory(
                mappers.territoryToTerritoryEntityMapper.map(territory)
            )
        } else {
            localTerritoryDataSource.updateTerritory(
                mappers.territoryToTerritoryEntityMapper.map(territory)
            )
        }
        emit(territory)
    }

    override fun delete(territory: Territory) = flow {
        localTerritoryDataSource.deleteTerritory(
            mappers.territoryToTerritoryEntityMapper.map(territory)
        )
        this.emit(territory)
    }

    override fun deleteById(territoryId: UUID) = flow {
        localTerritoryDataSource.deleteTerritoryById(territoryId)
        this.emit(territoryId)
    }

    override suspend fun deleteAll() = localTerritoryDataSource.deleteAllTerritories()

}