package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_GROUP_CONGREGATION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface GroupDao {
    // READS:
    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GroupEntity>>

    @Query("SELECT * FROM ${GroupView.VIEW_NAME} ORDER BY ${PX_GROUP_CONGREGATION}congregationName, groupNum")
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

    @Query("SELECT g.* FROM ${GroupView.VIEW_NAME} g JOIN ${FavoriteCongregationView.VIEW_NAME} fc ON fc.congregationId = g.gCongregationsId ORDER BY groupNum")
    fun findByFavoriteCongregation(): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByFavoriteCongregation() = findByFavoriteCongregation().distinctUntilChanged()

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

    // API:
    @Query("SELECT ifnull(MAX(groupNum), 0) FROM ${GroupEntity.TABLE_NAME} WHERE gCongregationsId = :congregationId")
    fun maxGroupNum(congregationId: UUID): Int

    @Query("SELECT ifnull(MAX(groupNum), 0) + 1 FROM ${GroupEntity.TABLE_NAME} WHERE gCongregationsId = :congregationId")
    fun nextGroupNum(congregationId: UUID): Int

    @Query("UPDATE ${GroupEntity.TABLE_NAME} SET groupNum = groupNum + 1 WHERE groupNum >= :groupNum AND gCongregationsId = :congregationId")
    suspend fun updateGroupNum(congregationId: UUID, groupNum: Int)

    @Transaction
    suspend fun insertWithGroupNum(group: GroupEntity) {
        updateGroupNum(group.gCongregationsId, group.groupNum)
        insert(group)
    }

    @Transaction
    suspend fun updateWithGroupNum(group: GroupEntity) {
        updateGroupNum(group.gCongregationsId, group.groupNum)
        update(group)
    }

    @Query("UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalGroups = totalGroups + :diff WHERE ctlCongregationsId = :congregationId AND lastVisitDate IS NULL")
    suspend fun incTotalGroupsByCongregationId(congregationId: UUID, diff: Int = 1)

    @Query("UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalGroups = totalGroups + :diff WHERE ctlCongregationsId = (SELECT gCongregationsId FROM ${GroupEntity.TABLE_NAME} WHERE groupId = :groupId) AND lastVisitDate IS NULL")
    suspend fun decTotalGroupsByGroupId(groupId: UUID, diff: Int = -1)

    @Transaction
    suspend fun insertWithTotals(group: GroupEntity) {
        insert(group)
        incTotalGroupsByCongregationId(group.gCongregationsId)
    }

    @Transaction
    suspend fun deleteByIdWithTotals(groupId: UUID) {
        decTotalGroupsByGroupId(groupId)
        deleteById(groupId)
    }
}