package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.FloorView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface FloorDao {
    // READS:
    @Query("SELECT * FROM ${FloorView.VIEW_NAME} ORDER BY houseNum, floorNum")
    fun findAll(): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE floorId = :floorId")
    fun findById(floorId: UUID): Flow<FloorView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE fHousesId = :houseId ORDER BY floorNum")
    fun findByHouseId(houseId: UUID): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(entranceId: UUID) = findByHouseId(entranceId).distinctUntilChanged()

    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE fEntrancesId = :entranceId ORDER BY entranceNum, floorNum")
    fun findByEntranceId(entranceId: UUID): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByEntranceId(entranceId: UUID) =
        findByEntranceId(entranceId).distinctUntilChanged()

    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE fTerritoriesId = :territoryId ORDER BY houseNum, floorNum")
    fun findByTerritoryId(territoryId: UUID): Flow<List<FloorView>>

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