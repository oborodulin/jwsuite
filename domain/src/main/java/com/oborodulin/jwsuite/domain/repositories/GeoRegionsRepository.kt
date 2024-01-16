package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoRegionsRepository {
    fun getAllEntities(): Flow<List<GeoRegionCsv>>
    fun getAllTlEntities(): Flow<List<GeoRegionTlCsv>>
    fun getAll(): Flow<List<GeoRegion>>
    fun get(regionId: UUID): Flow<GeoRegion>
    fun save(region: GeoRegion): Flow<GeoRegion>
    fun delete(region: GeoRegion): Flow<GeoRegion>
    fun deleteById(regionId: UUID): Flow<UUID>
    suspend fun deleteAll()
}