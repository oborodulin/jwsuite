package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoMicrodistrictDataSource {
    fun getAllMicrodistricts(): Flow<List<GeoMicrodistrictView>>
    fun getLocalityMicrodistricts(localityId: UUID): Flow<List<GeoMicrodistrictView>>
    fun getLocalityDistrictMicrodistricts(localityDistrictId: UUID): Flow<List<GeoMicrodistrictView>>
    fun getMicrodistrict(microdistrictId: UUID): Flow<GeoMicrodistrictView>
    suspend fun insertMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    )

    suspend fun updateMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    )

    suspend fun deleteMicrodistrict(microdistrict: GeoMicrodistrictEntity)
    suspend fun deleteMicrodistrictById(microdistrictId: UUID)
    suspend fun deleteMicrodistricts(microdistricts: List<GeoMicrodistrictEntity>)
    suspend fun deleteAllMicrodistricts()

    // MD Streets:
    suspend fun insertMicrodistrictStreet(
        microdistrict: GeoMicrodistrictEntity, street: GeoStreetEntity
    )

    suspend fun updateMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteMicrodistrictStreet(districtStreetId: UUID)
    suspend fun deleteMicrodistrictStreets(microdistrictId: UUID)
}