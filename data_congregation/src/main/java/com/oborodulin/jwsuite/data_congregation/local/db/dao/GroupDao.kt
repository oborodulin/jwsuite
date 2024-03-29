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
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberLastCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberServiceRoleView
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GroupDao {
    // EXTRACTS:
    @Query(
        """
    SELECT g.groupId, g.groupNum, g.gCongregationsId FROM ${GroupEntity.TABLE_NAME} g JOIN ${CongregationEntity.TABLE_NAME} c 
            ON g.gCongregationsId = c.congregationId AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
        LEFT JOIN ${MemberEntity.TABLE_NAME} m ON m.pseudonym = :username AND g.groupId = m.mGroupsId 
    WHERE (:username IS NULL OR m.mGroupsId IS NOT NULL) 
    UNION ALL            
    SELECT g.groupId, g.groupNum, g.gCongregationsId FROM ${GroupEntity.TABLE_NAME} g
        JOIN ${MemberServiceRoleView.VIEW_NAME} msrv ON g.groupId = msrv.mGroupsId
        JOIN ${MemberLastCongregationView.VIEW_NAME} mlcv ON g.gCongregationsId = mlcv.mcCongregationsId AND mlcv.memberPseudonym = :username
    GROUP BY groupId, groupNum, gCongregationsId
    """
    )
    fun findEntitiesByUsernameAndFavoriteMark(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<GroupEntity>>

    // READS:
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE ${GroupEntity.PX_LOCALITY}localityLocCode = :locale ORDER BY ${GroupEntity.PX_CONGREGATION}congregationName, groupNum")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE groupId = :groupId AND ${GroupEntity.PX_LOCALITY}localityLocCode = :locale")
    fun findById(groupId: UUID, locale: String? = Locale.getDefault().language): Flow<GroupView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GroupView.VIEW_NAME} WHERE gCongregationsId = :congregationId AND ${GroupEntity.PX_LOCALITY}localityLocCode = :locale ORDER BY groupNum")
    fun findByCongregationId(congregationId: UUID, locale: String? = Locale.getDefault().language): Flow<List<GroupView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationId(congregationId: UUID) =
        findByCongregationId(congregationId).distinctUntilChanged()

    //-----------------------------
    @Query("""
    SELECT g.* FROM ${GroupView.VIEW_NAME} g JOIN ${FavoriteCongregationView.VIEW_NAME} fc
        ON fc.congregationId = g.gCongregationsId AND g.${GroupEntity.PX_LOCALITY}localityLocCode = fc.${CongregationEntity.PX_LOCALITY}localityLocCode
            AND g.${GroupEntity.PX_LOCALITY}localityLocCode = :locale
    ORDER BY groupNum
    """)
    fun findByFavoriteCongregation(locale: String? = Locale.getDefault().language): Flow<List<GroupView>>

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

    // UPDATES TOTALS:
    // totalGroups:
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