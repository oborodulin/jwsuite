package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface TransferDao {
    // READS:
    @Query("SELECT * FROM ${MemberRoleTransferObjectView.VIEW_NAME} ORDER BY transferObjectName")
    fun findAll(): Flow<List<MemberRoleTransferObjectView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberRoleTransferObjectView.VIEW_NAME} WHERE transferObjectId = :transferObjectId")
    fun findById(transferObjectId: UUID): Flow<MemberRoleTransferObjectView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberRoleTransferObjectView.VIEW_NAME} WHERE memberId = :memberId ORDER BY transferObjectName")
    fun findByMemberId(memberId: UUID): Flow<List<MemberRoleTransferObjectView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByMemberId(memberId: UUID) =
        findByMemberId(memberId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberRoleTransferObjectView.VIEW_NAME} WHERE pseudonym = :pseudonym ORDER BY transferObjectName")
    fun findByMemberPseudonym(pseudonym: String): Flow<List<MemberRoleTransferObjectView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByMemberPseudonym(pseudonym: String) =
        findByMemberPseudonym(pseudonym).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${MemberRoleTransferObjectView.VIEW_NAME} WHERE rtoRolesId = :roleId ORDER BY transferObjectName")
    fun findByRoleId(roleId: UUID): Flow<List<MemberRoleTransferObjectView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRoleId(roleId: UUID) =
        findByRoleId(roleId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TransferObjectEntity.TABLE_NAME} WHERE transferObjectId NOT IN (SELECT rtoTransferObjectsId FROM ${RoleTransferObjectEntity.TABLE_NAME} WHERE rtoRolesId = :roleId) ORDER BY transferObjectName")
    fun findTransferObjectsForRoleByRoleId(roleId: UUID): Flow<List<TransferObjectEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctTransferObjectsForRoleByRoleId(roleId: UUID) =
        findTransferObjectsForRoleByRoleId(roleId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transferObject: TransferObjectEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg transferObjects: TransferObjectEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transferObjects: List<TransferObjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg roleTransferObjects: RoleTransferObjectEntity)

    // UPDATES:
    @Update
    suspend fun update(transferObject: TransferObjectEntity)

    @Update
    suspend fun update(vararg transferObjects: TransferObjectEntity)

    @Update
    suspend fun update(vararg roleTransferObjects: RoleTransferObjectEntity)

    // DELETES:
    @Delete
    suspend fun delete(transferObject: TransferObjectEntity)

    @Delete
    suspend fun delete(vararg transferObjects: TransferObjectEntity)

    @Delete
    suspend fun delete(transferObjects: List<TransferObjectEntity>)

    @Query("DELETE FROM ${TransferObjectEntity.TABLE_NAME} WHERE transferObjectId = :transferObjectId")
    suspend fun deleteById(transferObjectId: UUID)

    @Query("DELETE FROM ${TransferObjectEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // Role Transfer Object:
    @Delete
    suspend fun deleteRoleTransferObject(vararg roleTransferObjects: RoleTransferObjectEntity)

    @Query("DELETE FROM ${RoleTransferObjectEntity.TABLE_NAME} WHERE roleTransferObjectId = :roleTransferObjectId")
    suspend fun deleteRoleTransferObjectById(roleTransferObjectId: UUID)

    @Query("DELETE FROM ${RoleTransferObjectEntity.TABLE_NAME} WHERE rtoRolesId = :roleId")
    suspend fun deleteRoleTransferObjectsByRoleId(roleId: UUID)
}