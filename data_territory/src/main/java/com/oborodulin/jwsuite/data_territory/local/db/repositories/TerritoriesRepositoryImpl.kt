package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalEntranceDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalHouseDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalRoomDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryDataSource
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject

class TerritoriesRepositoryImpl @Inject constructor(
    private val localTerritoryDataSource: LocalTerritoryDataSource,
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

    override fun getCongregationTerritoryLocations(
        isPrivateSector: Boolean,
        congregationId: UUID?
    ) =
        localTerritoryDataSource.getCongregationTerritoryLocations(isPrivateSector, congregationId)
            .map(mappers.territoryLocationViewListToTerritoryLocationsListMapper::map)

    override fun getTerritories(
        territoryProcessType: TerritoryProcessType,
        territoryLocationType: TerritoryLocationType, locationId: UUID?,
        isPrivateSector: Boolean,
        congregationId: UUID?
    ) = when (territoryProcessType) {
        TerritoryProcessType.HAND_OUT -> localTerritoryDataSource.getHandOutTerritories(
            congregationId, isPrivateSector, territoryLocationType, locationId
        ).map(mappers.territoriesHandOutViewListToTerritoriesListMapper::map)

        TerritoryProcessType.AT_WORK -> localTerritoryDataSource.getAtWorkTerritories(
            congregationId, isPrivateSector, territoryLocationType, locationId
        ).map(mappers.territoriesAtWorkViewListToTerritoriesListMapper::map)

        TerritoryProcessType.IDLE -> localTerritoryDataSource.getIdleTerritories(
            congregationId, isPrivateSector, territoryLocationType, locationId
        ).map(mappers.territoriesIdleViewListToTerritoriesListMapper::map)

        TerritoryProcessType.ALL -> when (congregationId) {
            null -> localTerritoryDataSource.getFavoriteCongregationTerritories()
            else -> localTerritoryDataSource.getCongregationTerritories(congregationId)
        }.map(mappers.territoryViewListToTerritoriesListMapper::map)
    }

    override fun getNextNum(congregationId: UUID, territoryCategoryId: UUID) = flow {
        emit(localTerritoryDataSource.getNextTerritoryNum(congregationId, territoryCategoryId))
    }

    // Territory Streets:
    override fun getTerritoryStreet(territoryStreetId: UUID?) = flow {
        when (territoryStreetId) {
            null -> emit(null)
            else -> localTerritoryDataSource.getTerritoryStreet(territoryStreetId)
        }
    }.map(mappers.territoryStreetViewToTerritoryStreetMapper::nullableMap)

    override fun getTerritoryStreets(territoryId: UUID) =
        localTerritoryDataSource.getTerritoryStreets(territoryId)
            .map(mappers.territoryStreetViewListToTerritoryStreetsListMapper::map)

    override fun getTerritoryStreetHouses(territoryId: UUID) =
        localHouseDataSource.getTerritoryStreetHouses(territoryId)
            .map(mappers.territoryStreetHouseViewListToTerritoryStreetsListMapper::map)

    override fun getStreetsForTerritory(territoryId: UUID) =
        localTerritoryDataSource.getStreetsForTerritory(territoryId)
            .map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

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

    // Territory Houses:
    override fun getHouses(territoryId: UUID) = localHouseDataSource.getTerritoryHouses(territoryId)
        .map(mappers.houseViewListToHousesListMapper::map)

    override fun getEntrances(territoryId: UUID) =
        localEntranceDataSource.getTerritoryEntrances(territoryId)
            .map(mappers.entranceViewListToEntrancesListMapper::map)

    override fun getFloors(territoryId: UUID) = localFloorDataSource.getTerritoryFloors(territoryId)
        .map(mappers.floorViewListToFloorsListMapper::map)

    override fun getRooms(territoryId: UUID) = localRoomDataSource.getTerritoryRooms(territoryId)
        .map(mappers.roomViewListToRoomsListMapper::map)

    override fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID?) =
        localTerritoryDataSource.getTerritoryStreetNamesAndHouseNums(congregationId)
            .map(mappers.territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper::map)

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

    // API:
    override fun handOutTerritories(
        memberId: UUID, territoryIds: List<UUID>, receivingDate: OffsetDateTime
    ) = flow {
        val ids = mutableListOf<UUID>()
        territoryIds.forEach {
            localTerritoryDataSource.handOut(it, memberId, receivingDate)
            ids.add(it)
        }
        this.emit(ids)
    }
}