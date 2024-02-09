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
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberActualRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberServiceRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.domain.types.MemberType
import com.oborodulin.jwsuite.domain.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.domain.util.Constants.MT_SERVICE_VAL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import java.time.OffsetDateTime
import java.util.UUID

@Dao
interface MemberDao {
    // EXTRACTS:
    @Query(
        """
    SELECT m.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} mc ON m.memberId = mc.mcMembersId 
        JOIN ${CongregationEntity.TABLE_NAME} c 
            ON mc.mcCongregationsId = c.congregationId AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
        LEFT JOIN ${MemberCongregationView.VIEW_NAME} mcv ON mc.memberCongregationId = mcv.memberCongregationId 
                                                            AND mcv.pseudonym = :username AND m.pseudonym = mcv.pseudonym
    WHERE (:username IS NULL OR mcv.memberCongregationId IS NOT NULL)
    UNION ALL            
    SELECT m.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${MemberServiceRoleView.VIEW_NAME} msrv ON m.memberId = msrv.mrMembersId 
        JOIN ${MemberCongregationView.VIEW_NAME} mcm ON m.memberId = mcm.mcMembersId
        JOIN ${MemberCongregationView.VIEW_NAME} mcg ON mcm.mcCongregationsId = mcg.mcCongregationsId AND mcg.pseudonym = :username
    """
    )
    fun selectEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberEntity>>

    @Query(
        """
    SELECT mccr.* FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mccr JOIN ${CongregationEntity.TABLE_NAME} c 
            ON mccr.mcCongregationsId = c.congregationId AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
        LEFT JOIN ${MemberCongregationView.VIEW_NAME} mcv ON mccr.memberCongregationId = mcv.memberCongregationId AND mcv.pseudonym = :username 
    WHERE (:username IS NULL OR mcv.memberCongregationId IS NOT NULL)
     """
    )
    fun selectMemberCongregationEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberCongregationCrossRefEntity>>

    @Query("""
    SELECT mm.* FROM ${MemberMovementEntity.TABLE_NAME} mm JOIN ${MemberEntity.TABLE_NAME} m ON mm.mMembersId = m.memberId
        JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} mc ON m.memberId = mc.mcMembersId 
        JOIN ${CongregationEntity.TABLE_NAME} c 
            ON mc.mcCongregationsId = c.congregationId AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
    """)
    fun selectMemberMovementEntities(byFavorite: Boolean = false): Flow<List<MemberMovementEntity>>

    @Query("SELECT * FROM ${RoleEntity.TABLE_NAME}")
    fun selectRoleEntities(): Flow<List<RoleEntity>>

    @Query(
        """
    SELECT mr.* FROM ${MemberRoleEntity.TABLE_NAME} mr JOIN ${MemberEntity.TABLE_NAME} m ON mr.mrMembersId = m.memberId 
        JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} mc ON mc.mcMembersId = m.memberId 
        JOIN ${CongregationEntity.TABLE_NAME} c ON mc.mcCongregationsId = c.congregationId
                                                    AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
        LEFT JOIN ${MemberCongregationView.VIEW_NAME} mcv ON mc.memberCongregationId = mcv.memberCongregationId 
                                                            AND mcv.pseudonym = :username AND m.pseudonym = mcv.pseudonym
        LEFT JOIN ${MemberActualRoleView.VIEW_NAME} marv ON mr.memberRoleId = marv.memberRoleId
    WHERE (:username IS NULL OR (mcv.memberCongregationId IS NOT NULL AND marv.memberRoleId IS NOT NULL))
        AND (:byFavorite = $DB_FALSE OR marv.memberRoleId IS NOT NULL)
    UNION ALL            
    SELECT mr.* FROM ${MemberRoleEntity.TABLE_NAME} mr JOIN ${MemberServiceRoleView.VIEW_NAME} msrv ON mr.mrMembersId = msrv.mrMembersId 
        JOIN ${MemberCongregationView.VIEW_NAME} mcm ON msrv.mrMembersId = mcm.mcMembersId
        JOIN ${MemberCongregationView.VIEW_NAME} mcg ON mcm.mcCongregationsId = mcg.mcCongregationsId AND mcg.pseudonym = :username
    """
    )
    fun selectMemberRoleEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberRoleEntity>>

    // READS:
    @Query("SELECT * FROM ${MemberView.VIEW_NAME} ORDER BY groupNum, surname, memberName, patronymic, pseudonym")
    fun findAll(): Flow<List<MemberView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberView.VIEW_NAME} WHERE memberId = :memberId")
    fun findById(memberId: UUID): Flow<MemberView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //----------------------------- Members by Congregation:
    @Query(
        """
    SELECT mv.* FROM ${MemberView.VIEW_NAME} mv JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} cm ON cm.mcMembersId = mv.memberId 
    WHERE cm.mcCongregationsId = :congregationId
        AND (:isService = $DB_FALSE AND mv.memberType NOT IN ($MT_SERVICE_VAL) OR :isService = $DB_TRUE AND mv.memberType IN ($MT_SERVICE_VAL))
    ORDER BY groupNum, surname, memberName, patronymic, pseudonym
        """
    )
    fun findByCongregationId(
        congregationId: UUID, isService: Boolean = false
    ): Flow<List<MemberView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationId(congregationId: UUID, isService: Boolean = false) =
        findByCongregationId(congregationId, isService).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT mv.* FROM ${MemberView.VIEW_NAME} mv JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} cm ON cm.mcMembersId = mv.memberId 
        JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = cm.mcCongregationsId
    WHERE (:isService = $DB_FALSE AND mv.memberType NOT IN ($MT_SERVICE_VAL) OR :isService = $DB_TRUE AND mv.memberType IN ($MT_SERVICE_VAL))
    ORDER BY groupNum, surname, memberName, patronymic, pseudonym
    """
    )
    fun findByFavoriteCongregation(isService: Boolean = false): Flow<List<MemberView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByFavoriteCongregation(isService: Boolean = false) =
        findByFavoriteCongregation(isService).distinctUntilChanged()

    //----------------------------- Members by Groups:
    @Query(
        """
    SELECT mv.* FROM ${MemberView.VIEW_NAME} mv JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} cm ON cm.mcMembersId = mv.memberId 
        JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = cm.mcCongregationsId
        JOIN (SELECT g.gCongregationsId, MIN(g.groupNum) minGroupNum FROM ${GroupEntity.TABLE_NAME} g GROUP BY g.gCongregationsId) mg 
            ON mg.gCongregationsId = fcv.congregationId AND mg.minGroupNum = mv.groupNum
    WHERE (:isService = $DB_FALSE AND mv.memberType NOT IN ($MT_SERVICE_VAL) OR :isService = $DB_TRUE AND mv.memberType IN ($MT_SERVICE_VAL))
    ORDER BY surname, memberName, patronymic, pseudonym
    """
    )
    fun findByFavoriteCongregationGroup(isService: Boolean = false): Flow<List<MemberView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByFavoriteCongregationGroup(isService: Boolean = false) =
        findByFavoriteCongregationGroup(isService).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${MemberView.VIEW_NAME}
    WHERE mGroupsId = :groupId
        AND (:isService = $DB_FALSE AND memberType NOT IN ($MT_SERVICE_VAL) OR :isService = $DB_TRUE AND memberType IN ($MT_SERVICE_VAL))
    ORDER BY surname, memberName, patronymic, pseudonym 
    """
    )
    fun findByGroupId(groupId: UUID, isService: Boolean = false): Flow<List<MemberView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByGroupId(groupId: UUID, isService: Boolean = false) =
        findByGroupId(groupId, isService).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT mv.* FROM ${MemberView.VIEW_NAME} mv JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} cm ON cm.mcMembersId = mv.memberId 
        LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = cm.mcCongregationsId
    WHERE cm.mcCongregationsId = ifnull(:congregationId, cm.mcCongregationsId) AND mv.mGroupsId IS NULL
        AND (:isService = $DB_FALSE AND mv.memberType NOT IN ($MT_SERVICE_VAL) OR :isService = $DB_TRUE AND mv.memberType IN ($MT_SERVICE_VAL))
    ORDER BY surname, memberName, patronymic, pseudonym
    """
    )
    fun findByCongregationIdAndGroupIdIsNull(
        congregationId: UUID? = null, isService: Boolean = false
    ): Flow<List<MemberView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationIdAndGroupIdIsNull(
        congregationId: UUID? = null, isService: Boolean = false
    ) = findByCongregationIdAndGroupIdIsNull(congregationId, isService).distinctUntilChanged()

    //----------------------------- Member Roles:
    @Query("SELECT * FROM ${MemberRoleView.VIEW_NAME} WHERE memberRoleId = :memberRoleId")
    fun findMemberRoleById(memberRoleId: UUID): Flow<MemberRoleView>

    @ExperimentalCoroutinesApi
    fun findDistinctMemberRoleById(memberRoleId: UUID) =
        findMemberRoleById(memberRoleId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberRoleView.VIEW_NAME} WHERE mrMembersId = :memberId ORDER BY roleName")
    fun findMemberRolesByMemberId(memberId: UUID): Flow<List<MemberRoleView>>

    @ExperimentalCoroutinesApi
    fun findDistinctMemberRolesByMemberId(memberId: UUID) =
        findMemberRolesByMemberId(memberId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT mrv.* FROM ${MemberRoleView.VIEW_NAME} mrv WHERE mrv.pseudonym = :pseudonym")
    fun findMemberRolesByPseudonym(pseudonym: String): Flow<List<MemberRoleView>>

    @ExperimentalCoroutinesApi
    fun findDistinctMemberRolesByPseudonym(pseudonym: String) =
        findMemberRolesByPseudonym(pseudonym).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT r.* FROM ${RoleEntity.TABLE_NAME} r ORDER BY roleName")
    fun findAllRoles(): Flow<List<RoleEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAllRoles() = findAllRoles().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT mrv.roleId, mrv.roleType, mrv.roleName FROM ${MemberRoleView.VIEW_NAME} mrv WHERE mrv.pseudonym = :pseudonym")
    fun findRolesByPseudonym(pseudonym: String): Flow<List<RoleEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctRolesByPseudonym(pseudonym: String) =
        findRolesByPseudonym(pseudonym).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${RoleEntity.TABLE_NAME} WHERE roleId NOT IN (SELECT mrRolesId FROM ${MemberRoleEntity.TABLE_NAME} WHERE mrMembersId = :memberId) ORDER BY roleName")
    fun findRolesForMemberByMemberId(memberId: UUID): Flow<List<RoleEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctRolesForMemberByMemberId(memberId: UUID) =
        findRolesForMemberByMemberId(memberId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberView.VIEW_NAME} WHERE mGroupsId = :groupId AND (surname || ' ' || memberName || ' ' || patronymic LIKE '%' || :fullName || '%')")
    fun findByFullName(groupId: UUID, fullName: String): Flow<List<MemberView>>

    @Query("SELECT * FROM ${MemberView.VIEW_NAME} WHERE mGroupsId = :groupId AND pseudonym LIKE '%' || :pseudonym || '%'")
    fun findByPseudonym(groupId: UUID, pseudonym: String): Flow<List<MemberView>>

    //-----------------------------
    @Query("SELECT * FROM ${MemberMovementEntity.TABLE_NAME} WHERE mMembersId = :memberId ORDER BY strftime($DB_FRACT_SEC_TIME, movementDate)")
    fun findMovementsByMemberId(memberId: UUID): Flow<List<MemberMovementEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctMovementsByMemberId(memberId: UUID) =
        findMovementsByMemberId(memberId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT mm.* FROM ${MemberMovementEntity.TABLE_NAME} mm  
        JOIN (SELECT mMembersId, MAX(strftime($DB_FRACT_SEC_TIME, movementDate)) AS maxMovementDate FROM ${MemberMovementEntity.TABLE_NAME} GROUP BY mMembersId) gmm 
            ON gmm.mMembersId = :memberId AND mm.mMembersId = :memberId AND strftime($DB_FRACT_SEC_TIME, mm.movementDate) = gmm.maxMovementDate
    """
    )
    fun findMovementByMemberId(memberId: UUID): Flow<MemberMovementEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctMovementByMemberId(memberId: UUID) =
        findMovementByMemberId(memberId).distinctUntilChanged()

    // UPDATES TOTALS:
    // totalMembers:
    @Query("UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalMembers = totalMembers + :diff WHERE ctlCongregationsId = :congregationId AND lastVisitDate IS NULL")
    suspend fun incTotalMembersByCongregationId(congregationId: UUID, diff: Int = 1)

    @Query(
        """
    UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalMembers = totalMembers + :diff
    WHERE lastVisitDate IS NULL
        AND ctlCongregationsId = (SELECT mc.mcCongregationsId FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mc
                                        JOIN (SELECT mcMembersId, MAX(strftime($DB_FRACT_SEC_TIME, activityDate)) AS maxActivityDate 
                                                FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} GROUP BY mcMembersId ) gmc
                                            ON gmc.mcMembersId = :memberId AND mc.mcMembersId = :memberId
                                                AND strftime($DB_FRACT_SEC_TIME, mc.activityDate) = gmc.maxActivityDate)
    """
    )
    suspend fun decTotalMembersByMemberId(memberId: UUID, diff: Int = -1)

    // totalFulltimeMembers:
    @Query("UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalFulltimeMembers = totalFulltimeMembers + :diff WHERE ctlCongregationsId = :congregationId AND lastVisitDate IS NULL")
    suspend fun updateTotalFulltimeMembersByCongregationId(congregationId: UUID, diff: Int)

    @Query(
        """
    UPDATE ${CongregationTotalEntity.TABLE_NAME} SET totalFulltimeMembers = totalFulltimeMembers + :diff
    WHERE lastVisitDate IS NULL
        AND ctlCongregationsId = (SELECT mc.mcCongregationsId FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mc
                                        JOIN (SELECT mcMembersId, MAX(strftime($DB_FRACT_SEC_TIME, activityDate)) AS maxActivityDate 
                                                FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} GROUP BY mcMembersId ) gmc
                                            ON gmc.mcMembersId = :memberId AND mc.mcMembersId = :memberId
                                                AND strftime($DB_FRACT_SEC_TIME, mc.activityDate) = gmc.maxActivityDate)
    """
    )
    suspend fun decTotalFulltimeMembersByMemberId(memberId: UUID, diff: Int = -1)

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(member: MemberEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg members: MemberEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(members: List<MemberEntity>)

    //----------------------------- Congregations:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg memberCongregations: MemberCongregationCrossRefEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCongregations(memberCongregations: List<MemberCongregationCrossRefEntity>)

    //----------------------------- Roles:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg roles: RoleEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoles(roles: List<RoleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg memberRoles: MemberRoleEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMemberRoles(memberRoles: List<MemberRoleEntity>)

    //----------------------------- Movements:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg memberMovements: MemberMovementEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovements(memberMovements: List<MemberMovementEntity>)

    //----------------------------- Member:
    suspend fun insert(
        member: MemberEntity, memberType: MemberType = MemberType.PREACHER,
        movementDate: OffsetDateTime = OffsetDateTime.now()
    ) = insert(
        MemberMovementEntity(
            memberType = memberType, movementDate = movementDate, mMembersId = member.memberId
        )
    )

    @Transaction
    suspend fun insert(
        member: MemberEntity,
        memberCongregation: MemberCongregationCrossRefEntity,
        memberMovement: MemberMovementEntity
    ) {
        insert(member)
        insert(memberCongregation)
        incTotalMembersByCongregationId(memberCongregation.mcCongregationsId)
        insert(memberMovement)
        if (memberMovement.memberType == MemberType.FULL_TIME) {
            updateTotalFulltimeMembersByCongregationId(memberCongregation.mcCongregationsId, 1)
        }
    }

    // UPDATES:
    @Update
    suspend fun update(member: MemberEntity)

    @Update
    suspend fun update(vararg members: MemberEntity)

    //----------------------------- Congregations:
    @Update
    suspend fun update(vararg memberCongregations: MemberCongregationCrossRefEntity)

    //----------------------------- Roles:
    @Update
    suspend fun update(vararg memberRoles: MemberRoleEntity)

    //----------------------------- Movements:
    @Update
    suspend fun update(vararg memberMovements: MemberMovementEntity)

    @Transaction
    suspend fun update(
        member: MemberEntity,
        memberCongregation: MemberCongregationCrossRefEntity,
        memberMovement: MemberMovementEntity
    ) {
        update(member)
        insert(memberCongregation) // OnConflictStrategy.REPLACE
        val lastMovement = findMovementByMemberId(member.memberId).first()
        if (lastMovement.memberType != memberMovement.memberType) {
            insert(memberMovement)  // OnConflictStrategy.REPLACE
            if (memberMovement.memberType == MemberType.FULL_TIME) {
                updateTotalFulltimeMembersByCongregationId(memberCongregation.mcCongregationsId, 1)
            }
            if (lastMovement.memberType == MemberType.FULL_TIME) {
                updateTotalFulltimeMembersByCongregationId(memberCongregation.mcCongregationsId, -1)
            }
        } else {
            update(memberMovement)
        }
    }

    // DELETES:
    @Delete
    suspend fun delete(member: MemberEntity)

    @Delete
    suspend fun delete(vararg members: MemberEntity)

    @Delete
    suspend fun delete(members: List<MemberEntity>)

    @Query("DELETE FROM ${MemberEntity.TABLE_NAME} WHERE memberId = :memberId")
    suspend fun deleteById(memberId: UUID)

    @Query("DELETE FROM ${MemberEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // Congregations:
    @Delete
    suspend fun deleteCongregation(vararg memberCongregations: MemberCongregationCrossRefEntity)

    @Query("DELETE FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} WHERE memberCongregationId = :memberCongregationId")
    suspend fun deleteCongregationById(memberCongregationId: UUID)

    @Query("DELETE FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} WHERE mcMembersId = :memberId")
    suspend fun deleteCongregationsByMemberId(memberId: UUID)

    // Roles:
    @Delete
    suspend fun deleteRole(vararg memberRoles: MemberRoleEntity)

    @Query("DELETE FROM ${MemberRoleEntity.TABLE_NAME} WHERE memberRoleId = :memberRoleId")
    suspend fun deleteRoleById(memberRoleId: UUID)

    @Query("DELETE FROM ${MemberRoleEntity.TABLE_NAME} WHERE mrMembersId = :memberId")
    suspend fun deleteRolesByMemberId(memberId: UUID)

    @Delete
    suspend fun deleteMovement(vararg memberMovements: MemberMovementEntity)

    @Query("DELETE FROM ${MemberMovementEntity.TABLE_NAME} WHERE memberMovementId = :memberMovementId")
    suspend fun deleteMovementById(memberMovementId: UUID)

    @Query("DELETE FROM ${MemberMovementEntity.TABLE_NAME} WHERE mMembersId = :memberId")
    suspend fun deleteMovementsByMemberId(memberId: UUID)

    // API:
    @Transaction
    suspend fun deleteByIdWithTotals(memberId: UUID) {
        decTotalMembersByMemberId(memberId)
        val lastMovement = findMovementByMemberId(memberId).first()
        if (lastMovement.memberType == MemberType.FULL_TIME) {
            decTotalFulltimeMembersByMemberId(memberId)
        }
        deleteById(memberId)
    }
}