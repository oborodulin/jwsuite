package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data.local.db.views.HouseView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface HouseDao {
    // READS:
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} ORDER BY hStreetsId, houseNum, buildingNum")
    fun findAll(): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE houseId = :houseId")
    fun findById(houseId: UUID): Flow<HouseView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE hStreetsId = :streetId")
    fun findByStreetId(streetId: UUID): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByStreetId(streetId: UUID) = findByStreetId(streetId).distinctUntilChanged()

    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE hTerritoriesId = :territoryId")
    fun findByTerritoryId(territoryId: UUID): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(house: HouseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg houses: HouseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(houses: List<HouseEntity>)

    // UPDATES:
    @Update
    suspend fun update(house: HouseEntity)

    @Update
    suspend fun update(vararg houses: HouseEntity)

    // DELETES:
    @Delete
    suspend fun delete(house: HouseEntity)

    @Delete
    suspend fun delete(vararg houses: HouseEntity)

    @Delete
    suspend fun delete(houses: List<HouseEntity>)

    @Query("DELETE FROM ${HouseEntity.TABLE_NAME} WHERE houseId = :houseId")
    suspend fun deleteById(houseId: UUID)

    @Query("DELETE FROM ${HouseEntity.TABLE_NAME}")
    suspend fun deleteAll()
}