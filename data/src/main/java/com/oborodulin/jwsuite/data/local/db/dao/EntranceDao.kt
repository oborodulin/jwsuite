package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface EntranceDao {
    // READS:
    @Query("SELECT * FROM ${EntranceEntity.TABLE_NAME}")
    fun findAll(): Flow<List<EntranceEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${EntranceEntity.TABLE_NAME} WHERE entranceId = :entranceId")
    fun findById(entranceId: UUID): Flow<EntranceEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${EntranceEntity.TABLE_NAME} WHERE eHousesId = :houseId")
    fun findByHouseId(houseId: UUID): Flow<List<EntranceEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(houseId: UUID) = findByHouseId(houseId).distinctUntilChanged()

    @Query("SELECT * FROM ${EntranceEntity.TABLE_NAME} WHERE eTerritoriesId = :territoryId")
    fun findByTerritoryId(territoryId: UUID): Flow<List<EntranceEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entrance: EntranceEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg entrances: EntranceEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entrances: List<EntranceEntity>)

    // UPDATES:
    @Update
    suspend fun update(entrance: EntranceEntity)

    @Update
    suspend fun update(vararg entrances: EntranceEntity)

    // DELETES:
    @Delete
    suspend fun delete(entrance: EntranceEntity)

    @Delete
    suspend fun delete(vararg entrances: EntranceEntity)

    @Delete
    suspend fun delete(entrances: List<EntranceEntity>)

    @Query("DELETE FROM ${EntranceEntity.TABLE_NAME} WHERE entranceId = :entranceId")
    suspend fun deleteById(entranceId: UUID)

    @Query("DELETE FROM ${EntranceEntity.TABLE_NAME}")
    suspend fun deleteAll()
}