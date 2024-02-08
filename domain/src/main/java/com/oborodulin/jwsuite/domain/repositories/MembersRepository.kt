package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.model.congregation.MemberRoleTransferObject
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCongregationCrossRefCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MembersRepository : CsvTransferableRepo {
    // Members by Congregation:
    fun getAllByFavoriteCongregation(isService: Boolean = false): Flow<List<Member>>
    fun getAllByCongregation(
        congregationId: UUID, isService: Boolean = false
    ): Flow<List<Member>>

    // Members by Groups:
    fun getAllByFavoriteCongregationGroup(isService: Boolean = false): Flow<List<Member>>
    fun getAllByGroup(groupId: UUID, isService: Boolean = false): Flow<List<Member>>

    // Member Roles:
    fun getMemberRoles(memberId: UUID): Flow<List<MemberRole>>
    fun getMemberRoles(pseudonym: String): Flow<List<MemberRole>>

    // Roles:
    fun getAllRoles(): Flow<List<Role>>
    fun getRoles(pseudonym: String): Flow<List<Role>>
    fun getRolesForMember(memberId: UUID): Flow<List<Role>>

    // Transfer Objects:
    fun getMemberTransferObjects(pseudonym: String): Flow<List<RoleTransferObject>>
    fun getRoleTransferObjects(roleId: UUID): Flow<List<MemberRoleTransferObject>>
    fun getTransferObjectsForRole(roleId: UUID): Flow<List<TransferObject>>

    // Member:
    fun get(memberId: UUID): Flow<Member>
    fun save(member: Member): Flow<Member>
    fun delete(member: Member): Flow<Member>
    fun deleteById(memberId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // Member Role:
    fun getMemberRole(memberRoleId: UUID): Flow<MemberRole>
    fun saveMemberRole(role: MemberRole): Flow<MemberRole>
    fun deleteMemberRoleById(memberRoleId: UUID): Flow<UUID>

    // Movements:
    fun deleteMovementById(memberMovementId: UUID): Flow<UUID>

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractMembers(username: String? = null, byFavorite: Boolean = false): Flow<List<MemberCsv>>
    fun extractMemberCongregations(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberCongregationCrossRefCsv>>

    fun extractMemberMovements(byFavorite: Boolean = false): Flow<List<MemberMovementCsv>>
    fun extractRoles(): Flow<List<RoleCsv>>
    fun extractMemberRoles(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberRoleCsv>>

    fun extractTransferObjects(): Flow<List<TransferObjectCsv>>
    fun extractRoleTransferObjects(): Flow<List<RoleTransferObjectCsv>>

    fun loadMembers(members: List<MemberCsv>): Flow<Int>
    fun loadMemberCongregations(memberCongregations: List<MemberCongregationCrossRefCsv>): Flow<Int>
    fun loadMemberMovements(memberMovements: List<MemberMovementCsv>): Flow<Int>
    fun loadRoles(roles: List<RoleCsv>): Flow<Int>
    fun loadMemberRoles(memberRoles: List<MemberRoleCsv>): Flow<Int>
    fun loadTransferObjects(transferObjects: List<TransferObjectCsv>): Flow<Int>
    fun loadRoleTransferObjects(roleTransferObjects: List<RoleTransferObjectCsv>): Flow<Int>
}