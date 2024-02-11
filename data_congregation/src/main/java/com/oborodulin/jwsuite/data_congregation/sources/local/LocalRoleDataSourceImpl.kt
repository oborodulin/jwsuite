package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.RoleDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalRoleDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class LocalRoleDataSourceImpl @Inject constructor(
    private val roleDao: RoleDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalRoleDataSource {
    override fun getAllRoles() = roleDao.findAllRoles()

    override suspend fun deleteAllRoles() = withContext(dispatcher) {
        roleDao.deleteAll()
    }

    // Role Transfer Objects:
    override fun getTransferObjectsExceptRole(roleId: UUID) =
        roleDao.findTransferObjectsExceptRoleId(roleId)

    override suspend fun insertRoleTransferObject(roleTransferObject: RoleTransferObjectEntity) =
        withContext(dispatcher) {
            roleDao.insert(roleTransferObject)
        }

    override suspend fun updateRoleTransferObject(roleTransferObject: RoleTransferObjectEntity) =
        withContext(dispatcher) {
            roleDao.update(roleTransferObject)
        }

    override suspend fun deleteRoleTransferObjectById(roleTransferObjectId: UUID) =
        withContext(dispatcher) {
            roleDao.deleteRoleTransferObjectById(roleTransferObjectId)
        }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getRoleEntities() = roleDao.findAllEntities()
    override fun getRoleTransferObjectEntities() = roleDao.findAllRoleTransferObjectEntities()
    override suspend fun loadRoleEntities(roles: List<RoleEntity>) = withContext(dispatcher) {
        roleDao.insertRoles(roles)
    }

    override suspend fun loadRoleTransferObjectEntities(roleTransferObjects: List<RoleTransferObjectEntity>) =
        withContext(dispatcher) {
            roleDao.insertRoleTransferObjects(roleTransferObjects)
        }
}