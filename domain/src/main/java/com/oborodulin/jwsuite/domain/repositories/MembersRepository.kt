package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.model.congregation.Role
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MembersRepository {
    fun getAllByFavoriteCongregation(): Flow<List<Member>>
    fun getAllByCongregation(congregationId: UUID): Flow<List<Member>>
    fun getAllByFavoriteCongregationGroup(): Flow<List<Member>>
    fun getAllByGroup(groupId: UUID): Flow<List<Member>>
    fun getMemberRoles(memberId: UUID): Flow<List<MemberRole>>
    fun getRolesForMember(memberId: UUID): Flow<List<Role>>
    fun get(memberId: UUID): Flow<Member>
    fun save(member: Member): Flow<Member>
    fun saveRole(role: MemberRole): Flow<MemberRole>
    fun delete(member: Member): Flow<Member>
    fun deleteById(memberId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun deleteRoleById(memberRoleId: UUID): Flow<UUID>
    fun deleteMovementById(memberMovementId: UUID): Flow<UUID>
}