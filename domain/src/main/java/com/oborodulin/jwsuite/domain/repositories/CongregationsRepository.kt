package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Congregation
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CongregationsRepository {
    fun getAll(): Flow<List<Congregation>>
    fun get(congregationId: UUID): Flow<Congregation>
    fun getFavorite(): Flow<Congregation?>
    fun save(congregation: Congregation): Flow<Congregation>
    fun delete(congregation: Congregation): Flow<Congregation>
    fun deleteById(congregationId: UUID): Flow<UUID>
    fun makeFavoriteById(congregationId: UUID): Flow<UUID>
    suspend fun deleteAll()
}