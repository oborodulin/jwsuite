package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoStreetsRepository: CsvTransferableRepo {
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

    // Districts:
    suspend fun deleteStreetDistrict(streetDistrictId: UUID)

    // Locality Districts:
    fun insertStreetLocalityDistricts(streetId: UUID, localityDistrictIds: List<UUID>):
            Flow<List<UUID>>

    fun deleteLocalityDistrict(streetId: UUID, localityDistrictId: UUID): Flow<UUID>

    // Microdistricts:
    fun insertStreetMicrodistricts(streetId: UUID, districtIds: Map<UUID, List<UUID>> = mapOf()):
            Flow<List<UUID>>

    fun deleteMicrodistrict(streetId: UUID, microdistrictId: UUID): Flow<UUID>
}