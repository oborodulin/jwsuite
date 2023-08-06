package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class HousesRepositoryImpl @Inject constructor(
    private val localHouseDataSource: LocalHouseDataSource,
    private val mappers: HouseMappers
) : HousesRepository {
    override fun getAll() = localHouseDataSource.getAllHouses()
        .map(mappers.houseViewListToHousesListMapper::map)

    override fun getAllByStreet(streetId: UUID) = localHouseDataSource.getStreetHouses(streetId)
        .map(mappers.houseViewListToHousesListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localHouseDataSource.getTerritoryHouses(territoryId)
            .map(mappers.houseViewListToHousesListMapper::map)

    override fun get(houseId: UUID) = localHouseDataSource.getHouse(houseId)
        .map(mappers.houseViewToHouseMapper::map)

    override fun save(house: House) = flow {
        if (house.id == null) {
            localHouseDataSource.insertHouse(mappers.houseToHouseEntityMapper.map(house))
        } else {
            localHouseDataSource.updateHouse(mappers.houseToHouseEntityMapper.map(house))
        }
        emit(house)
    }

    override fun delete(house: House) = flow {
        localHouseDataSource.deleteHouse(
            mappers.houseToHouseEntityMapper.map(house)
        )
        this.emit(house)
    }

    override fun deleteById(houseId: UUID) = flow {
        localHouseDataSource.deleteHouseById(houseId)
        this.emit(houseId)
    }

    override suspend fun deleteAll() = localHouseDataSource.deleteAllHouses()
}