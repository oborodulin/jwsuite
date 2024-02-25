package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoMicrodistrictDataSource {
    fun getAllMicrodistricts(): Flow<List<MicrodistrictView>>
    fun getLocalityMicrodistricts(localityId: UUID): Flow<List<MicrodistrictView>>
    fun getLocalityDistrictMicrodistricts(localityDistrictId: UUID): Flow<List<MicrodistrictView>>
    fun getStreetMicrodistricts(streetId: UUID): Flow<List<MicrodistrictView>>
    fun getMicrodistrictsForStreet(streetId: UUID): Flow<List<MicrodistrictView>>
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

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getMicrodistrictEntities(): Flow<List<GeoMicrodistrictEntity>>
    fun getMicrodistrictTlEntities(): Flow<List<GeoMicrodistrictTlEntity>>
    suspend fun loadMicrodistrictEntities(microdistricts: List<GeoMicrodistrictEntity>)
    suspend fun loadMicrodistrictTlEntities(microdistrictTls: List<GeoMicrodistrictTlEntity>)
}