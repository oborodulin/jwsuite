package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface TerritoryCategoryDao {
    // READS:
    @Query("SELECT * FROM ${TerritoryCategoryEntity.TABLE_NAME}")
    fun findAll(): Flow<List<TerritoryCategoryEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${TerritoryCategoryEntity.TABLE_NAME} WHERE territoryCategoryId = :territoryCategoryId")
    fun findById(territoryCategoryId: UUID): Flow<TerritoryCategoryEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(floor: TerritoryCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg floors: TerritoryCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(floors: List<TerritoryCategoryEntity>)

    // UPDATES:
    @Update
    suspend fun update(floor: TerritoryCategoryEntity)

    @Update
    suspend fun update(vararg floors: TerritoryCategoryEntity)

    // DELETES:
    @Delete
    suspend fun delete(floor: TerritoryCategoryEntity)

    @Delete
    suspend fun delete(vararg floors: TerritoryCategoryEntity)

    @Delete
    suspend fun delete(floors: List<TerritoryCategoryEntity>)

    @Query("DELETE FROM ${TerritoryCategoryEntity.TABLE_NAME} WHERE territoryCategoryId = :territoryCategoryId")
    suspend fun deleteById(territoryCategoryId: UUID)

    @Query("DELETE FROM ${TerritoryCategoryEntity.TABLE_NAME}")
    suspend fun deleteAll()
}