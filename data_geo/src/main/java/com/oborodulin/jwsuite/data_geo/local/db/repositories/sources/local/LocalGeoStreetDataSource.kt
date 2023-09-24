package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoStreetDataSource {
    fun getAllStreets(): Flow<List<GeoStreetView>>
    fun getLocalityStreets(localityId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreetView>>

    fun getLocalityDistrictStreets(localityDistrictId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreetView>>

    fun getMicrodistrictStreets(microdistrictId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreetView>>

    fun getStreetsForTerritory(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        excludes: List<UUID> = emptyList()
    ): Flow<List<GeoStreetView>>

    fun getStreet(streetId: UUID): Flow<GeoStreetView>
    suspend fun insertStreet(street: GeoStreetEntity, textContent: GeoStreetTlEntity)
    suspend fun updateStreet(street: GeoStreetEntity, textContent: GeoStreetTlEntity)
    suspend fun deleteStreet(street: GeoStreetEntity)
    suspend fun deleteStreetById(streetId: UUID)
    suspend fun deleteStreets(streets: List<GeoStreetEntity>)
    suspend fun deleteAllStreets()

    // Districts:
    suspend fun updateStreetDistrict(streetDistrict: GeoStreetDistrictEntity)
    suspend fun deleteStreetDistrict(streetDistrict: GeoStreetDistrictEntity)
    suspend fun deleteStreetDistrict(streetDistrictId: UUID)

    // Locality Districts:
    suspend fun insertStreetLocalityDistrict(
        street: GeoStreetEntity, localityDistrict: GeoLocalityDistrictEntity
    )

    suspend fun insertStreetLocalityDistrict(streetId: UUID, localityDistrictId: UUID)
    suspend fun deleteStreetLocalityDistrict(streetId: UUID, localityDistrictId: UUID)

    // Microdistricts:
    suspend fun insertStreetMicrodistrict(
        street: GeoStreetEntity, microdistrict: GeoMicrodistrictEntity
    )

    suspend fun insertStreetMicrodistrict(
        streetId: UUID, localityDistrictId: UUID, microdistrictId: UUID
    )

    suspend fun deleteStreetMicrodistrict(streetId: UUID, microdistrictId: UUID)
}