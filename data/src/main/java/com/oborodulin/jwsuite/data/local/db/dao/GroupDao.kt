package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data.local.db.views.GroupView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GroupDao {
    // READS:
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} ORDER BY congregationName, groupNum")
    fun findAll(): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE groupId = :groupId")
    fun findById(groupId: UUID): Flow<GroupView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE gCongregationsId = :congregationId ORDER BY groupNum")
    fun findByCongregationId(congregationId: UUID): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationId(congregationId: UUID) =
        findByCongregationId(congregationId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(group: GroupEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg groups: GroupEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(groups: List<GroupEntity>)

    // UPDATES:
    @Update
    suspend fun update(group: GroupEntity)

    @Update
    suspend fun update(vararg groups: GroupEntity)

    // DELETES:
    @Delete
    suspend fun delete(group: GroupEntity)

    @Delete
    suspend fun delete(vararg groups: GroupEntity)

    @Delete
    suspend fun delete(groups: List<GroupEntity>)

    @Query("DELETE FROM ${GroupEntity.TABLE_NAME} WHERE groupId = :groupId")
    suspend fun deleteById(groupId: UUID)

    @Query("DELETE FROM ${GroupEntity.TABLE_NAME}")
    suspend fun deleteAll()
}