package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.EntranceView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface EntranceDao {
    // READS:
    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} ORDER BY streetName, houseNum, houseLetter, entranceNum")
    fun findAll(): Flow<List<EntranceView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} WHERE entranceId = :entranceId")
    fun findById(entranceId: UUID): Flow<EntranceView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} WHERE eHousesId = :houseId ORDER BY entranceNum")
    fun findByHouseId(houseId: UUID): Flow<List<EntranceView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(houseId: UUID) = findByHouseId(houseId).distinctUntilChanged()

    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} WHERE eTerritoriesId = :territoryId ORDER BY streetName, houseNum, houseLetter, entranceNum")
    fun findByTerritoryId(territoryId: UUID): Flow<List<EntranceView>>

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