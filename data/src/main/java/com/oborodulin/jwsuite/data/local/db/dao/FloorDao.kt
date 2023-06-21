package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface FloorDao {
    // READS:
    @Query("SELECT * FROM ${FloorEntity.TABLE_NAME}")
    fun findAll(): Flow<List<FloorEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${FloorEntity.TABLE_NAME} WHERE floorId = :floorId")
    fun findById(floorId: UUID): Flow<FloorEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${FloorEntity.TABLE_NAME} WHERE fEntrancesId = :entranceId")
    fun findByEntranceId(entranceId: UUID): Flow<List<FloorEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByEntranceId(entranceId: UUID) =
        findByEntranceId(entranceId).distinctUntilChanged()

    @Query("SELECT * FROM ${FloorEntity.TABLE_NAME} WHERE fTerritoriesId = :territoryId")
    fun findByTerritoryId(territoryId: UUID): Flow<List<FloorEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(floor: FloorEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg floors: FloorEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(floors: List<FloorEntity>)

    // UPDATES:
    @Update
    suspend fun update(floor: FloorEntity)

    @Update
    suspend fun update(vararg floors: FloorEntity)

    // DELETES:
    @Delete
    suspend fun delete(floor: FloorEntity)

    @Delete
    suspend fun delete(vararg floors: FloorEntity)

    @Delete
    suspend fun delete(floors: List<FloorEntity>)

    @Query("DELETE FROM ${FloorEntity.TABLE_NAME} WHERE floorId = :floorId")
    suspend fun deleteById(floorId: UUID)

    @Query("DELETE FROM ${FloorEntity.TABLE_NAME}")
    suspend fun deleteAll()
}