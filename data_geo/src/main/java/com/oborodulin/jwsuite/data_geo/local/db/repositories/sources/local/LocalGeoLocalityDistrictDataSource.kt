package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoLocalityDistrictDataSource {
    fun getAllLocalityDistricts(): Flow<List<GeoLocalityDistrictView>>
    fun getLocalityDistricts(localityId: UUID): Flow<List<GeoLocalityDistrictView>>
    fun getLocalityDistrict(localityDistrictId: UUID): Flow<GeoLocalityDistrictView>
    suspend fun insertLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    )

    suspend fun updateLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    )

    suspend fun deleteLocalityDistrict(localityDistrict: GeoLocalityDistrictEntity)
    suspend fun deleteLocalityDistrictById(localityDistrictId: UUID)
    suspend fun deleteLocalityDistricts(localityDistricts: List<GeoLocalityDistrictEntity>)
    suspend fun deleteAllLocalityDistricts()

    // LD Streets:
    suspend fun insertLocalityDistrictStreet(
        localityDistrict: GeoLocalityDistrictEntity, street: GeoStreetEntity
    )

    suspend fun updateLocalityDistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteLocalityDistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteLocalityDistrictStreet(districtStreetId: UUID)
    suspend fun deleteLocalityDistrictStreets(localityDistrictId: UUID)
}