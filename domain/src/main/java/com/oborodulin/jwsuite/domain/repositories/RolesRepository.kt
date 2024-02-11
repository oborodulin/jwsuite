package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RolesRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<Role>>
    suspend fun deleteAll()

    // Role Transfer Objects:
    fun getTransferObjectsExceptRole(roleId: UUID): Flow<List<TransferObject>>

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractRoles(): Flow<List<RoleCsv>>
    fun extractRoleTransferObjects(): Flow<List<RoleTransferObjectCsv>>
    fun loadRoles(roles: List<RoleCsv>): Flow<Int>
    fun loadRoleTransferObjects(roleTransferObjects: List<RoleTransferObjectCsv>): Flow<Int>
}