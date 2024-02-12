package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface EventDao {
    // EXTRACTS:
    @Query("SELECT * FROM ${EventEntity.TABLE_NAME}")
    fun findAllEntities(): Flow<List<EventEntity>>

    // READS:
    @Query("SELECT * FROM ${EventEntity.TABLE_NAME} ORDER BY strftime($DB_FRACT_SEC_TIME, eventTime) DESC")
    fun findAll(): Flow<List<EventEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${EventEntity.TABLE_NAME} WHERE eventId = :eventId")
    fun findById(eventId: UUID): Flow<EventEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg events: EventEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(events: List<EventEntity>)

    // UPDATES:
    @Update
    suspend fun update(event: EventEntity)

    @Update
    suspend fun update(vararg events: EventEntity)

    // DELETES:
    @Delete
    suspend fun delete(event: EventEntity)

    @Delete
    suspend fun delete(vararg events: EventEntity)

    @Delete
    suspend fun delete(events: List<EventEntity>)

    @Query("DELETE FROM ${EventEntity.TABLE_NAME} WHERE eventId = :eventId")
    suspend fun deleteById(eventId: UUID)

    @Query("DELETE FROM ${EventEntity.TABLE_NAME}")
    suspend fun deleteAll()
}