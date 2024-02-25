package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoLocalityDataSource {
    fun getAllLocalities(): Flow<List<LocalityView>>
    fun getRegionLocalities(regionId: UUID): Flow<List<LocalityView>>
    fun getRegionDistrictLocalities(regionDistrictId: UUID): Flow<List<LocalityView>>
    fun getLocality(localityId: UUID): Flow<GeoLocalityView>
    suspend fun insertLocality(locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity)
    suspend fun updateLocality(locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity)
    suspend fun deleteLocality(locality: GeoLocalityEntity)
    suspend fun deleteLocalityById(localityId: UUID)
    suspend fun deleteLocalities(localities: List<GeoLocalityEntity>)
    suspend fun deleteAllLocalities()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getLocalityEntities(): Flow<List<GeoLocalityEntity>>
    fun getLocalityTlEntities(): Flow<List<GeoLocalityTlEntity>>
    suspend fun loadLocalityEntities(localities: List<GeoLocalityEntity>)
    suspend fun loadLocalityTlEntities(localityTls: List<GeoLocalityTlEntity>)
}