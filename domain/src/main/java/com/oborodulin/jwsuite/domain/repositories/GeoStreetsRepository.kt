package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoStreetsRepository {
    fun getAll(): Flow<List<GeoStreet>>
    fun getAllByLocality(localityId: UUID, isPrivateSector: Boolean? = null): Flow<List<GeoStreet>>
    fun getAllByLocalityDistrict(localityDistrictId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreet>>

    fun getAllByMicrodistrict(microdistrictId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreet>>

    fun getAllForTerritory(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        excludes: List<UUID> = emptyList()
    ): Flow<List<GeoStreet>>

    fun get(streetId: UUID): Flow<GeoStreet>
    fun save(street: GeoStreet): Flow<GeoStreet>
    fun delete(street: GeoStreet): Flow<GeoStreet>
    fun deleteById(streetId: UUID): Flow<UUID>
    suspend fun deleteAll()
}