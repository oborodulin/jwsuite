package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoRegionDataSource {
    fun getRegionEntities(): Flow<List<GeoRegionEntity>>
    fun getRegionTlEntities(): Flow<List<GeoRegionTlEntity>>
    fun getRegions(): Flow<List<GeoRegionView>>
    fun getRegion(regionId: UUID): Flow<GeoRegionView>

    //fun getFavoriteCongregationRegion(): Flow<GeoRegionView>
    suspend fun insertRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity)
    suspend fun updateRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity)
    suspend fun deleteRegion(region: GeoRegionEntity)
    suspend fun deleteRegionById(regionId: UUID)
    suspend fun deleteRegions(regions: List<GeoRegionEntity>)
    suspend fun deleteAllRegions()
}