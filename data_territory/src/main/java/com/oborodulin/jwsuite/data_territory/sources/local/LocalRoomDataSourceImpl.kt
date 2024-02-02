package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_territory.local.db.dao.RoomDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalRoomDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalRoomDataSourceImpl @Inject constructor(
    private val roomDao: RoomDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalRoomDataSource {
    override fun getAllRooms() = roomDao.findAll()
    override fun getHouseRooms(houseId: UUID) = roomDao.findByHouseId(houseId)
    override fun getEntranceRooms(entranceId: UUID) = roomDao.findByEntranceId(entranceId)
    override fun getFloorRooms(floorId: UUID) = roomDao.findByFloorId(floorId)

    override fun getTerritoryRooms(territoryId: UUID) = roomDao.findByTerritoryId(territoryId)

    override fun getRoomsForTerritory(territoryId: UUID) =
        roomDao.findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
            territoryId
        )

    override fun getNextRoom(roomId: UUID) = roomDao.findNextById(roomId)
    override fun getRoom(roomId: UUID) = roomDao.findDistinctById(roomId)
    override fun isHouseExistsRooms(houseId: UUID) = roomDao.existsByHouseId(houseId)
    override fun isEntranceExistsRooms(entranceId: UUID) = roomDao.existsByEntranceId(entranceId)
    override fun isFloorExistsRooms(floorId: UUID) = roomDao.existsByFloorId(floorId)
    override suspend fun insertRoom(room: RoomEntity) = withContext(dispatcher) {
        roomDao.insert(room)
    }

    override suspend fun updateRoom(room: RoomEntity) = withContext(dispatcher) {
        roomDao.update(room)
    }

    override suspend fun deleteRoom(room: RoomEntity) = withContext(dispatcher) {
        roomDao.delete(room)
    }

    override suspend fun deleteRoomById(roomId: UUID) = withContext(dispatcher) {
        roomDao.deleteById(roomId)
    }

    override suspend fun deleteRooms(rooms: List<RoomEntity>) = withContext(dispatcher) {
        roomDao.delete(rooms)
    }

    override suspend fun deleteAllRooms() = withContext(dispatcher) {
        roomDao.deleteAll()
    }

    override fun getNextRoomNum(houseId: UUID) = roomDao.getNextRoomNum(houseId)
    override suspend fun clearTerritoryById(roomId: UUID) = withContext(dispatcher) {
        roomDao.clearTerritoryById(roomId)
    }

    override suspend fun setTerritoryById(roomId: UUID, territoryId: UUID) =
        withContext(dispatcher) {
            roomDao.updateTerritoryIdById(roomId, territoryId)
        }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getRoomEntities() = roomDao.selectEntities()
    override suspend fun loadRoomEntities(rooms: List<RoomEntity>) = withContext(dispatcher) {
        roomDao.insert(rooms)
    }
}
