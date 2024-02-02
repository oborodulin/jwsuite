package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalHouseDataSource
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.HouseCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class HousesRepositoryImpl @Inject constructor(
    private val localHouseDataSource: LocalHouseDataSource,
    private val domainMappers: HouseMappers,
    private val csvMappers: HouseCsvMappers
) : HousesRepository {
    override fun getAll() = localHouseDataSource.getAllHouses()
        .map(domainMappers.houseViewListToHousesListMapper::map)

    override fun getAllByStreet(streetId: UUID) = localHouseDataSource.getStreetHouses(streetId)
        .map(domainMappers.houseViewListToHousesListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localHouseDataSource.getTerritoryHouses(territoryId)
            .map(domainMappers.houseViewListToHousesListMapper::map)

    override fun getAllForTerritory(territoryId: UUID) =
        localHouseDataSource.getHousesForTerritory(territoryId)
            .map(domainMappers.houseViewListToHousesListMapper::map)

    override fun getNext(houseId: UUID) = localHouseDataSource.getNextHouse(houseId)
        .map(domainMappers.houseViewToHouseMapper::nullableMap)

    override fun get(houseId: UUID) = localHouseDataSource.getHouse(houseId)
        .map(domainMappers.houseViewToHouseMapper::map)

    override fun isExistsInTerritoryStreet(territoryStreetId: UUID) =
        localHouseDataSource.isTerritoryStreetExistsHouses(territoryStreetId)

    override fun save(house: House) = flow {
        if (house.id == null) {
            localHouseDataSource.insertHouse(domainMappers.houseToHouseEntityMapper.map(house))
        } else {
            localHouseDataSource.updateHouse(domainMappers.houseToHouseEntityMapper.map(house))
        }
        emit(house)
    }

    override fun delete(house: House) = flow {
        localHouseDataSource.deleteHouse(
            domainMappers.houseToHouseEntityMapper.map(house)
        )
        this.emit(house)
    }

    override fun deleteById(houseId: UUID) = flow {
        localHouseDataSource.deleteHouseById(houseId)
        this.emit(houseId)
    }

    override suspend fun deleteAll() = localHouseDataSource.deleteAllHouses()

    override fun getNextNum(streetId: UUID) = flow {
        emit(localHouseDataSource.getNextHouseNum(streetId))
    }

    override fun clearTerritory(houseId: UUID) = flow {
        localHouseDataSource.clearTerritoryById(houseId)
        this.emit(houseId)
    }

    override fun setTerritory(houseIds: List<UUID>, territoryId: UUID) = flow {
        houseIds.forEach { localHouseDataSource.setTerritoryById(it, territoryId) }
        this.emit(houseIds)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = HouseEntity.TABLE_NAME)
    override fun extractHouses() = localHouseDataSource.getHouseEntities()
        .map(csvMappers.houseEntityListToHouseCsvListMapper::map)

    @CsvLoad<HouseCsv>(fileNamePrefix = HouseEntity.TABLE_NAME, contentType = HouseCsv::class)
    override fun loadHouses(houses: List<HouseCsv>) = flow {
        localHouseDataSource.loadHouseEntities(
            csvMappers.houseCsvListToHouseEntityListMapper.map(houses)
        )
        emit(houses.size)
    }
}