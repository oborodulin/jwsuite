package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalRoomDataSource
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.RoomCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val localRoomDataSource: LocalRoomDataSource,
    private val domainMappers: RoomMappers,
    private val csvMappers: RoomCsvMappers
) : RoomsRepository {
    override fun getAll() = localRoomDataSource.getAllRooms()
        .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun getAllByHouse(houseId: UUID) = localRoomDataSource.getHouseRooms(houseId)
        .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun getAllByEntrance(entranceId: UUID) =
        localRoomDataSource.getEntranceRooms(entranceId)
            .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun getAllByFloor(floorId: UUID) = localRoomDataSource.getFloorRooms(floorId)
        .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localRoomDataSource.getTerritoryRooms(territoryId)
            .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun getAllForTerritory(territoryId: UUID) =
        localRoomDataSource.getTerritoryRooms(territoryId)
            .map(domainMappers.roomViewListToRoomsListMapper::map)

    override fun getNext(roomId: UUID) = localRoomDataSource.getNextRoom(roomId)
        .map(domainMappers.roomViewToRoomMapper::nullableMap)

    override fun get(roomId: UUID) = localRoomDataSource.getRoom(roomId)
        .map(domainMappers.roomViewToRoomMapper::map)

    override fun isExistsInHouse(houseId: UUID) = localRoomDataSource.isHouseExistsRooms(houseId)
    override fun isExistsInEntrance(entranceId: UUID) =
        localRoomDataSource.isEntranceExistsRooms(entranceId)

    override fun isExistsInFloor(floorId: UUID) = localRoomDataSource.isFloorExistsRooms(floorId)
    override fun save(room: Room) = flow {
        if (room.id == null) {
            localRoomDataSource.insertRoom(domainMappers.roomToRoomEntityMapper.map(room))
        } else {
            localRoomDataSource.updateRoom(domainMappers.roomToRoomEntityMapper.map(room))
        }
        emit(room)
    }

    override fun delete(room: Room) = flow {
        localRoomDataSource.deleteRoom(domainMappers.roomToRoomEntityMapper.map(room))
        this.emit(room)
    }

    override fun delete(roomId: UUID) = flow {
        localRoomDataSource.deleteRoomById(roomId)
        this.emit(roomId)
    }

    override suspend fun deleteAll() = localRoomDataSource.deleteAllRooms()

    override fun getNextNum(houseId: UUID) = flow {
        emit(localRoomDataSource.getNextRoomNum(houseId))
    }

    override fun clearTerritory(roomId: UUID) = flow {
        localRoomDataSource.clearTerritoryById(roomId)
        this.emit(roomId)
    }

    override fun setTerritory(roomIds: List<UUID>, territoryId: UUID) = flow {
        roomIds.forEach { localRoomDataSource.setTerritoryById(it, territoryId) }
        //localRoomDataSource.getRoom(roomIds).map(mappers.roomViewToRoomMapper::map).collect {
        this.emit(roomIds)
        //}
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = RoomEntity.TABLE_NAME)
    override fun extractRooms(username: String?, byFavorite: Boolean) =
        localRoomDataSource.getRoomEntities(username, byFavorite)
            .map(csvMappers.roomEntityListToRoomCsvListMapper::map)

    @CsvLoad<RoomCsv>(fileNamePrefix = RoomEntity.TABLE_NAME, contentType = RoomCsv::class)
    override fun loadRooms(rooms: List<RoomCsv>) = flow {
        localRoomDataSource.loadRoomEntities(
            csvMappers.roomCsvListToRoomEntityListMapper.map(rooms)
        )
        emit(rooms.size)
    }
}