package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_GROUP_CONGREGATION
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.domain.util.Constants.MR_TERRITORIES_VAL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface GroupDao {
    // READS:
    @Query(
        """
    SELECT g.groupId, g.groupNum, g.gCongregationsId FROM ${GroupEntity.TABLE_NAME} g JOIN ${CongregationEntity.TABLE_NAME} c 
            ON g.gCongregationsId = c.congregationId AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
        LEFT JOIN ${MemberEntity.TABLE_NAME} m ON m.pseudonym = :username AND g.groupId = m.mGroupsId 
    WHERE (:username IS NULL OR m.mGroupsId IS NOT NULL) 
    UNION ALL            
    SELECT g.groupId, g.groupNum, g.gCongregationsId FROM ${GroupEntity.TABLE_NAME} g
        JOIN (SELECT m.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${MemberRoleEntity.TABLE_NAME} mr ON m.memberId = mr.mrMembersId 
                JOIN ${RoleEntity.TABLE_NAME} r ON mr.mrRolesId = r.roleId AND r.roleType IN ($MR_TERRITORIES_VAL)
            ) mrt ON g.groupId = mrt.mGroupsId
        JOIN (SELECT mccr.* FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mccr JOIN 
                        (SELECT mcc.mcMembersId, MAX(strftime($DB_FRACT_SEC_TIME, mcc.activityDate)) AS maxActivityDate 
                        FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mcc JOIN ${MemberEntity.TABLE_NAME} m
                            ON mcc.mcMembersId = m.memberId AND m.pseudonym = :username
                        GROUP BY mcc.mcMembersId) mc ON mccr.mcMembersId = mc.mcMembersId AND strftime($DB_FRACT_SEC_TIME, mccr.activityDate) = mc.maxActivityDate 
                    ) mcg ON g.gCongregationsId = mcg.mcCongregationsId
    GROUP BY groupId, groupNum, gCongregationsId
    """
    )
    fun selectEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<GroupEntity>>

    //-----------------------------
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} ORDER BY ${PX_GROUP_CONGREGATION}congregationName, groupNum")
    fun findAll(): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE groupId = :groupId")
    fun findById(groupId: UUID): Flow<GroupView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE gCongregationsId = :congregationId ORDER BY groupNum")
    fun findByCongregationId(congregationId: UUID): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationId(congregationId: UUID) =
        findByCongregationId(congregationId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT g.* FROM ${GroupView.VIEW_NAME} g JOIN ${FavoriteCongregationView.VIEW_NAME} fc ON fc.congregationId = g.gCongregationsId ORDER BY groupNum")
    fun findByFavoriteCongregation(): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByFavoriteCongregation() = findByFavoriteCongregation().distinctUntilChanged()

    @Query(
        """
    SELECT EXISTS(SELECT groupId FROM ${GroupEntity.TABLE_NAME} 
                    WHERE gCongregationsId = :congregationId
                        AND groupNum = :groupNum 
                        AND groupId <> ifnull(:groupId, groupId) 
                LIMIT 1)
       """
    )
    fun existsWithGroupNum(
        congregationId: UUID, groupNum: Int, groupId: UUID? = null
    ): Flow<Boolean>

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

    @Query("UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalGroups = totalGroups + :diff WHERE ctlCongregationsId = :congregationId AND lastVisitDate IS NULL")
    suspend fun incTotalGroupsByCongregationId(congregationId: UUID, diff: Int = 1)

    @Query("UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalGroups = totalGroups + :diff WHERE ctlCongregationsId = (SELECT gCongregationsId FROM ${GroupEntity.TABLE_NAME} WHERE groupId = :groupId) AND lastVisitDate IS NULL")
    suspend fun decTotalGroupsByGroupId(groupId: UUID, diff: Int = -1)

    // C[R]UD:
    @Transaction
    suspend fun insertWithGroupNumAndTotals(group: GroupEntity) {
        // updateGroupNum(group.gCongregationsId, group.groupNum) // destructive behavior
        insert(group)
        incTotalGroupsByCongregationId(group.gCongregationsId)
    }

    @Transaction
    suspend fun updateWithGroupNum(group: GroupEntity) {
        /* // destructive behavior
        if (existsWithGroupNum(group.gCongregationsId, group.groupNum, group.groupId).first()) {
            updateGroupNum(group.gCongregationsId, group.groupNum)
        }*/
        update(group)
    }

    @Transaction
    suspend fun deleteByIdWithTotals(groupId: UUID) {
        decTotalGroupsByGroupId(groupId)
        deleteById(groupId)
    }
}