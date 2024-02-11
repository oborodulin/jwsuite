package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface RoleDao {
    // EXTRACTS:
    @Query("SELECT * FROM ${RoleEntity.TABLE_NAME}")
    fun findAllEntities(): Flow<List<RoleEntity>>

    @Query("SELECT * FROM ${RoleTransferObjectEntity.TABLE_NAME}")
    fun findAllRoleTransferObjectEntities(): Flow<List<RoleTransferObjectEntity>>

    // READS:
    @Query("SELECT r.* FROM ${RoleEntity.TABLE_NAME} r ORDER BY roleName")
    fun findAllRoles(): Flow<List<RoleEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAllRoles() = findAllRoles().distinctUntilChanged()

    // Role Transfer Objects:
    @Query("SELECT * FROM ${TransferObjectEntity.TABLE_NAME} WHERE transferObjectId NOT IN (SELECT rtoTransferObjectsId FROM ${RoleTransferObjectEntity.TABLE_NAME} WHERE rtoRolesId = :roleId) ORDER BY transferObjectName")
    fun findTransferObjectsExceptRoleId(roleId: UUID): Flow<List<TransferObjectEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctTransferObjectsExceptRoleId(roleId: UUID) =
        findTransferObjectsExceptRoleId(roleId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(role: RoleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg roles: RoleEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoles(roles: List<RoleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg roleTransferObjects: RoleTransferObjectEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoleTransferObjects(roleTransferObjects: List<RoleTransferObjectEntity>)

    // UPDATES:
    @Update
    suspend fun update(role: RoleEntity)

    @Update
    suspend fun update(vararg roleTransferObjects: RoleTransferObjectEntity)

    // DELETES:
    @Delete
    suspend fun delete(role: RoleEntity)

    @Delete
    suspend fun delete(vararg roles: RoleEntity)

    @Delete
    suspend fun delete(roles: List<RoleEntity>)

    @Query("DELETE FROM ${RoleEntity.TABLE_NAME} WHERE roleId = :roleId")
    suspend fun deleteById(roleId: UUID)

    @Query("DELETE FROM ${RoleEntity.TABLE_NAME}")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteRoleTransferObject(vararg roleTransferObjects: RoleTransferObjectEntity)

    @Query("DELETE FROM ${RoleTransferObjectEntity.TABLE_NAME} WHERE roleTransferObjectId = :roleTransferObjectId")
    suspend fun deleteRoleTransferObjectById(roleTransferObjectId: UUID)

    @Query("DELETE FROM ${RoleTransferObjectEntity.TABLE_NAME} WHERE rtoRolesId = :roleId")
    suspend fun deleteRoleTransferObjectsByRoleId(roleId: UUID)
}