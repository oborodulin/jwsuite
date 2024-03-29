package com.oborodulin.jwsuite.data_territory.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalEntranceDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalHouseDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalRoomDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryDataSource
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.CongregationTerritoryCrossRefCsv
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCsv
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberCrossRefCsv
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
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
    private val domainMappers: TerritoryMappers,
    private val csvMappers: TerritoryCsvMappers
) : TerritoriesRepository {
    override fun getCongregationTerritories(congregationId: UUID?) =
        when (congregationId) {
            null -> localTerritoryDataSource.getFavoriteCongregationTerritories()
            else -> localTerritoryDataSource.getCongregationTerritories(congregationId)
        }.map(domainMappers.territoryViewListToTerritoriesListMapper::map)

    override fun getCongregationTerritoryLocations(
        isPrivateSector: Boolean,
        congregationId: UUID?
    ) =
        localTerritoryDataSource.getCongregationTerritoryLocations(isPrivateSector, congregationId)
            .map(domainMappers.territoryLocationViewListToTerritoryLocationsListMapper::map)

    override fun getTerritories(
        territoryProcessType: TerritoryProcessType,
        territoryLocationType: TerritoryLocationType, locationId: UUID?,
        isPrivateSector: Boolean,
        congregationId: UUID?
    ) = when (territoryProcessType) {
        TerritoryProcessType.HAND_OUT -> localTerritoryDataSource.getHandOutTerritories(
            congregationId, isPrivateSector, territoryLocationType, locationId
        ).map(domainMappers.territoriesHandOutViewListToTerritoriesListMapper::map)

        TerritoryProcessType.AT_WORK -> localTerritoryDataSource.getAtWorkTerritories(
            congregationId, isPrivateSector, territoryLocationType, locationId
        ).map(domainMappers.territoriesAtWorkViewListToTerritoriesListMapper::map)

        TerritoryProcessType.IDLE -> localTerritoryDataSource.getIdleTerritories(
            congregationId, isPrivateSector, territoryLocationType, locationId
        ).map(domainMappers.territoriesIdleViewListToTerritoriesListMapper::map)

        TerritoryProcessType.ALL -> when (congregationId) {
            null -> localTerritoryDataSource.getFavoriteCongregationTerritories()
            else -> localTerritoryDataSource.getCongregationTerritories(congregationId)
        }.map(domainMappers.territoryViewListToTerritoriesListMapper::map)
    }

    override fun getAllByGeo(
        localityId: UUID, localityDistrictId: UUID?, microdistrictId: UUID?
    ) = localTerritoryDataSource.getTerritoriesByGeo(
        localityId, localityDistrictId, microdistrictId
    ).map(domainMappers.territoryViewListToTerritoriesListMapper::map)

    override fun getAllForHouse(houseId: UUID) =
        localTerritoryDataSource.getTerritoriesForHouse(houseId)
            .map(domainMappers.territoryViewListToTerritoriesListMapper::map)

    override fun getNextNum(congregationId: UUID, territoryCategoryId: UUID) = flow {
        emit(localTerritoryDataSource.getNextTerritoryNum(congregationId, territoryCategoryId))
    }

    // Territory Streets:
    override fun getTerritoryStreetHouses(territoryId: UUID) =
        localHouseDataSource.getTerritoryStreetHouses(territoryId)
            .map(domainMappers.territoryStreetHouseViewListToTerritoryStreetsListMapper::map)

    // Territory Houses:
    override fun getHouses(territoryId: UUID) = localHouseDataSource.getTerritoryHouses(territoryId)
        .map(domainMappers.houseViewListToHousesListMapper::map)

    override fun getEntrances(territoryId: UUID) =
        localEntranceDataSource.getTerritoryEntrances(territoryId)
            .map(domainMappers.entranceViewListToEntrancesListMapper::map)

    override fun getFloors(territoryId: UUID) = localFloorDataSource.getTerritoryFloors(territoryId)
        .map(domainMappers.floorViewListToFloorsListMapper::map)

    override fun getRooms(territoryId: UUID) = localRoomDataSource.getTerritoryRooms(territoryId)
        .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun get(territoryId: UUID) =
        localTerritoryDataSource.getTerritory(territoryId)
            .map(domainMappers.territoryViewToTerritoryMapper::map)

    override fun getFavoriteTotals() = localTerritoryDataSource.getFavoriteTerritoryTotals()
        .map(domainMappers.territoryTotalViewToTerritoryTotalsMapper::nullableMap)

    override fun save(territory: Territory) = flow {
        if (territory.id == null) {
            localTerritoryDataSource.insertTerritory(
                domainMappers.territoryToTerritoryEntityMapper.map(territory)
            )
        } else {
            localTerritoryDataSource.updateTerritory(
                domainMappers.territoryToTerritoryEntityMapper.map(territory)
            )
        }
        emit(territory)
    }

    override fun delete(territory: Territory) = flow {
        localTerritoryDataSource.deleteTerritory(
            domainMappers.territoryToTerritoryEntityMapper.map(territory)
        )
        this.emit(territory)
    }

    override fun delete(territoryId: UUID) = flow {
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

    override fun processTerritories(territoryIds: List<UUID>, deliveryDate: OffsetDateTime) = flow {
        val ids = mutableListOf<UUID>()
        territoryIds.forEach {
            localTerritoryDataSource.process(it, deliveryDate)
            ids.add(it)
        }
        this.emit(ids)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = TerritoryEntity.TABLE_NAME)
    override fun extractTerritories(username: String?, byFavorite: Boolean) =
        localTerritoryDataSource.getTerritoryEntities(username, byFavorite)
            .map(csvMappers.territoryEntityListToTerritoryCsvListMapper::map)

    @CsvExtract(fileNamePrefix = CongregationTerritoryCrossRefEntity.TABLE_NAME)
    override fun extractCongregationTerritories(username: String?, byFavorite: Boolean) =
        localTerritoryDataSource.getCongregationTerritoryEntities(username, byFavorite)
            .map(csvMappers.congregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper::map)

    @CsvExtract(fileNamePrefix = TerritoryMemberCrossRefEntity.TABLE_NAME)
    override fun extractTerritoryMembers(username: String?, byFavorite: Boolean) =
        localTerritoryDataSource.getTerritoryMemberEntities(username, byFavorite)
            .map(csvMappers.territoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper::map)

    @CsvLoad<TerritoryCsv>(
        fileNamePrefix = TerritoryEntity.TABLE_NAME,
        contentType = TerritoryCsv::class
    )
    override fun loadTerritories(territories: List<TerritoryCsv>) = flow {
        localTerritoryDataSource.loadTerritoryEntities(
            csvMappers.territoryCsvListToTerritoryEntityListMapper.map(territories)
        )
        emit(territories.size)
    }

    @CsvLoad<CongregationTerritoryCrossRefCsv>(
        fileNamePrefix = CongregationTerritoryCrossRefEntity.TABLE_NAME,
        contentType = CongregationTerritoryCrossRefCsv::class
    )
    override fun loadCongregationTerritories(congregationTerritories: List<CongregationTerritoryCrossRefCsv>) =
        flow {
            localTerritoryDataSource.loadCongregationTerritoryEntities(
                csvMappers.congregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper.map(
                    congregationTerritories
                )
            )
            emit(congregationTerritories.size)
        }

    @CsvLoad<TerritoryMemberCrossRefCsv>(
        fileNamePrefix = TerritoryMemberCrossRefEntity.TABLE_NAME,
        contentType = TerritoryMemberCrossRefCsv::class
    )
    override fun loadTerritoryMembers(territoryMembers: List<TerritoryMemberCrossRefCsv>) = flow {
        localTerritoryDataSource.loadTerritoryMemberEntities(
            csvMappers.territoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper.map(
                territoryMembers
            )
        )
        emit(territoryMembers.size)
    }
}