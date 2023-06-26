package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.GeoRegion
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoRegionsRepository {
    fun getAll(): Flow<List<GeoRegion>>
    fun get(regionId: UUID): Flow<GeoRegion>
    fun save(region: GeoRegion): Flow<GeoRegion>
    fun delete(region: GeoRegion): Flow<GeoRegion>
    fun deleteById(regionId: UUID): Flow<UUID>
    suspend fun deleteAll()
}