package com.oborodulin.jwsuite.data_geo.local.db.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoLocalityDistrictDataSource {
    fun getAllLocalityDistricts(): Flow<List<LocalityDistrictView>>
    fun getLocalityDistricts(localityId: UUID): Flow<List<LocalityDistrictView>>
    fun getStreetLocalityDistricts(streetId: UUID): Flow<List<LocalityDistrictView>>
    fun getLocalityDistrictsForStreet(streetId: UUID): Flow<List<LocalityDistrictView>>
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

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getLocalityDistrictEntities(): Flow<List<GeoLocalityDistrictEntity>>
    fun getLocalityDistrictTlEntities(): Flow<List<GeoLocalityDistrictTlEntity>>
    suspend fun loadLocalityDistrictEntities(localityDistricts: List<GeoLocalityDistrictEntity>)
    suspend fun loadLocalityDistrictTlEntities(localityDistrictTls: List<GeoLocalityDistrictTlEntity>)
}