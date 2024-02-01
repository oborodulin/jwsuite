package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface TerritoryCategoryDao {
    // READS:
    @Query("SELECT * FROM ${TerritoryCategoryEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<TerritoryCategoryEntity>>

    @Query("SELECT * FROM ${TerritoryCategoryEntity.TABLE_NAME} ORDER BY territoryCategoryName")
    fun findAll(): Flow<List<TerritoryCategoryEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${TerritoryCategoryEntity.TABLE_NAME} WHERE territoryCategoryId = :territoryCategoryId")
    fun findById(territoryCategoryId: UUID): Flow<TerritoryCategoryEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territoryCategory: TerritoryCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg territoryCategories: TerritoryCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territoryCategories: List<TerritoryCategoryEntity>)

    // UPDATES:
    @Update
    suspend fun update(territoryCategory: TerritoryCategoryEntity)

    @Update
    suspend fun update(vararg territoryCategories: TerritoryCategoryEntity)

    // DELETES:
    @Delete
    suspend fun delete(territoryCategory: TerritoryCategoryEntity)

    @Delete
    suspend fun delete(vararg territoryCategories: TerritoryCategoryEntity)

    @Delete
    suspend fun delete(territoryCategories: List<TerritoryCategoryEntity>)

    @Query("DELETE FROM ${TerritoryCategoryEntity.TABLE_NAME} WHERE territoryCategoryId = :territoryCategoryId")
    suspend fun deleteById(territoryCategoryId: UUID)

    @Query("DELETE FROM ${TerritoryCategoryEntity.TABLE_NAME}")
    suspend fun deleteAll()
}