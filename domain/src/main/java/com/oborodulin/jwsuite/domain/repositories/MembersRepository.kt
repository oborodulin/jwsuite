package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Member
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MembersRepository {
    fun getAllByFavoriteCongregation(): Flow<List<Member>>
    fun getAllByCongregation(congregationId: UUID): Flow<List<Member>>
    fun getAllByFavoriteCongregationGroup(): Flow<List<Member>>
    fun getAllByGroup(groupId: UUID): Flow<List<Member>>
    fun get(memberId: UUID): Flow<Member>
    fun save(member: Member): Flow<Member>
    fun delete(member: Member): Flow<Member>
    fun deleteById(memberId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // Movements:
    fun deleteMovementById(memberMovementId: UUID): Flow<UUID>
}