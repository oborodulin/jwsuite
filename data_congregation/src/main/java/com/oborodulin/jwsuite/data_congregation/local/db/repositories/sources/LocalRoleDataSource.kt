package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalRoleDataSource {
    fun getAllRoles(): Flow<List<RoleEntity>>
    suspend fun deleteAllRoles()

    // Role Transfer Objects:
    fun getTransferObjectsExceptRole(roleId: UUID): Flow<List<TransferObjectEntity>>
    suspend fun insertRoleTransferObject(roleTransferObject: RoleTransferObjectEntity)
    suspend fun updateRoleTransferObject(roleTransferObject: RoleTransferObjectEntity)
    suspend fun deleteRoleTransferObjectById(roleTransferObjectId: UUID)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getRoleEntities(): Flow<List<RoleEntity>>
    fun getRoleTransferObjectEntities(): Flow<List<RoleTransferObjectEntity>>
    suspend fun loadRoleEntities(roles: List<RoleEntity>)
    suspend fun loadRoleTransferObjectEntities(roleTransferObjects: List<RoleTransferObjectEntity>)
}