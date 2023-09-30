package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoLocalityDistrictDataSource {
    fun getAllLocalityDistricts(): Flow<List<GeoLocalityDistrictView>>
    fun getLocalityDistricts(localityId: UUID): Flow<List<GeoLocalityDistrictView>>
    fun getStreetLocalityDistricts(streetId: UUID): Flow<List<GeoLocalityDistrictView>>
    fun getLocalityDistrictsForStreet(streetId: UUID): Flow<List<GeoLocalityDistrictView>>
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
}