package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.GeoLocality
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoLocalitiesRepository {
    fun getAllByRegion(regionId: UUID): Flow<List<GeoLocality>>
    fun getAllByRegionDistrict(regionDistrictId: UUID): Flow<List<GeoLocality>>
    fun get(localityId: UUID): Flow<GeoLocality>
    fun save(locality: GeoLocality): Flow<GeoLocality>
    fun delete(locality: GeoLocality): Flow<GeoLocality>
    fun deleteById(localityId: UUID): Flow<UUID>
    suspend fun deleteAll()
}