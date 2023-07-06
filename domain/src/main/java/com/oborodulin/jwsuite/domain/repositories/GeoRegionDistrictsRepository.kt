package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoRegionDistrictsRepository {
    fun getAllByRegion(regionId: UUID): Flow<List<GeoRegionDistrict>>
    fun get(regionDistrictId: UUID): Flow<GeoRegionDistrict>
    fun save(regionDistrict: GeoRegionDistrict): Flow<GeoRegionDistrict>
    fun delete(regionDistrict: GeoRegionDistrict): Flow<GeoRegionDistrict>
    fun deleteById(regionDistrictId: UUID): Flow<UUID>
    suspend fun deleteAll()
}