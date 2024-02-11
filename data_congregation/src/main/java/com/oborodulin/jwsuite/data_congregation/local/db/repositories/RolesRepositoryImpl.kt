package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalRoleDataSource
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.repositories.RolesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class RolesRepositoryImpl @Inject constructor(
    private val localRoleDataSource: LocalRoleDataSource,
    private val domainMappers: RoleMappers,
    private val csvMappers: RoleCsvMappers
) : RolesRepository {
    override fun getAll() = localRoleDataSource.getAllRoles()
        .map(domainMappers.roleEntityListToRolesListMapper::map)

    override suspend fun deleteAll() = localRoleDataSource.deleteAllRoles()

    // Role Transfer Objects:
    override fun getTransferObjectsExceptRole(roleId: UUID) =
        localRoleDataSource.getTransferObjectsExceptRole(roleId)
            .map(domainMappers.transferObjectEntityListToTransferObjectsListMapper::map)

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = RoleEntity.TABLE_NAME)
    override fun extractRoles() = localRoleDataSource.getRoleEntities()
        .map(csvMappers.roleEntityListToRoleCsvListMapper::map)

    @CsvExtract(fileNamePrefix = RoleTransferObjectEntity.TABLE_NAME)
    override fun extractRoleTransferObjects() =
        localRoleDataSource.getRoleTransferObjectEntities()
            .map(csvMappers.roleTransferObjectEntityListToRoleTransferObjectCsvListMapper::map)

    @CsvLoad<RoleCsv>(fileNamePrefix = RoleEntity.TABLE_NAME, contentType = RoleCsv::class)
    override fun loadRoles(roles: List<RoleCsv>) = flow {
        localRoleDataSource.loadRoleEntities(
            csvMappers.roleCsvListToRoleEntityListMapper.map(roles)
        )
        emit(roles.size)
    }

    @CsvLoad<RoleTransferObjectCsv>(
        fileNamePrefix = RoleTransferObjectEntity.TABLE_NAME,
        contentType = RoleTransferObjectCsv::class
    )
    override fun loadRoleTransferObjects(roleTransferObjects: List<RoleTransferObjectCsv>) = flow {
        localRoleDataSource.loadRoleTransferObjectEntities(
            csvMappers.roleTransferObjectCsvListToRoleTransferObjectEntityListMapper.map(
                roleTransferObjects
            )
        )
        emit(roleTransferObjects.size)
    }
}