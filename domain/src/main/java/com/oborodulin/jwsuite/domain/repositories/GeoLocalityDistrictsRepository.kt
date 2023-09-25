package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoLocalityDistrictsRepository {
    fun getAll(): Flow<List<GeoLocalityDistrict>>
    fun getAllByLocality(localityId: UUID): Flow<List<GeoLocalityDistrict>>
    fun getAllByStreet(streetId: UUID): Flow<List<GeoLocalityDistrict>>
    fun getAllForStreet(streetId: UUID): Flow<List<GeoLocalityDistrict>>
    fun get(localityDistrictId: UUID): Flow<GeoLocalityDistrict>
    fun save(localityDistrict: GeoLocalityDistrict): Flow<GeoLocalityDistrict>
    fun delete(localityDistrict: GeoLocalityDistrict): Flow<GeoLocalityDistrict>
    fun deleteById(localityDistrictId: UUID): Flow<UUID>
    suspend fun deleteAll()
}