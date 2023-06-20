package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface RoomDao {
    // READS:
    @Query("SELECT * FROM ${RoomEntity.TABLE_NAME} ORDER BY housesId, roomNum")
    fun findAll(): Flow<List<RoomEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${RoomEntity.TABLE_NAME} WHERE roomId = :roomId")
    fun findById(roomId: UUID): Flow<RoomEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${RoomEntity.TABLE_NAME} WHERE housesId = :houseId")
    fun findByHouseId(houseId: UUID): Flow<List<RoomEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(houseId: UUID) = findByHouseId(houseId).distinctUntilChanged()

    @Query("SELECT * FROM ${RoomEntity.TABLE_NAME} WHERE entrancesId = :entranceId")
    fun findByEntranceId(entranceId: UUID): Flow<List<RoomEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByEntranceId(entranceId: UUID) =
        findByEntranceId(entranceId).distinctUntilChanged()

    @Query("SELECT * FROM ${RoomEntity.TABLE_NAME} WHERE floorsId = :floorId")
    fun findByFloorId(floorId: UUID): Flow<List<RoomEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByFloorId(floorId: UUID) = findByFloorId(floorId).distinctUntilChanged()

    @Query("SELECT * FROM ${RoomEntity.TABLE_NAME} WHERE territoriesId = :territoryId")
    fun findByTerritoryId(territoryId: UUID): Flow<List<RoomEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg rooms: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(rooms: List<RoomEntity>)

    // UPDATES:
    @Update
    suspend fun update(room: RoomEntity)

    @Update
    suspend fun update(vararg rooms: RoomEntity)

    // DELETES:
    @Delete
    suspend fun delete(room: RoomEntity)

    @Delete
    suspend fun delete(vararg rooms: RoomEntity)

    @Delete
    suspend fun delete(rooms: List<RoomEntity>)

    @Query("DELETE FROM ${RoomEntity.TABLE_NAME} WHERE roomId = :roomId")
    suspend fun deleteById(roomId: UUID)

    @Query("DELETE FROM ${RoomEntity.TABLE_NAME}")
    suspend fun deleteAll()
}