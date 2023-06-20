package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.RoomDao
import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalRoomDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalRoomDataSourceImpl @Inject constructor(
    private val roomDao: RoomDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalRoomDataSource {
    override fun getHouseRooms(houseId: UUID) = roomDao.findByHouseId(houseId)
    override fun getEntranceRooms(entranceId: UUID) = roomDao.findByEntranceId(entranceId)
    override fun getFloorRooms(floorId: UUID) = roomDao.findByFloorId(floorId)

    override fun getTerritoryRooms(territoryId: UUID) = roomDao.findByTerritoryId(territoryId)

    override fun getRoom(roomId: UUID) = roomDao.findDistinctById(roomId)

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

    override suspend fun deleteRooms(rooms: List<RoomEntity>) =
        withContext(dispatcher) {
            roomDao.delete(rooms)
        }

    override suspend fun deleteAllRooms() = withContext(dispatcher) {
        roomDao.deleteAll()
    }

}
