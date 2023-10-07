package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoRegionsRepositoryImpl @Inject constructor(
    private val localRegionDataSource: LocalGeoRegionDataSource,
    private val mappers: GeoRegionMappers
) : GeoRegionsRepository {
    override fun getAll() = localRegionDataSource.getRegions()
        .map(mappers.geoRegionViewListToGeoRegionsListMapper::map)

    override fun get(regionId: UUID) =
        localRegionDataSource.getRegion(regionId)
            .map(mappers.geoRegionViewToGeoRegionMapper::map)

    override fun save(region: GeoRegion) = flow {
        if (region.id == null) {
            localRegionDataSource.insertRegion(
                mappers.geoRegionToGeoRegionEntityMapper.map(region),
                mappers.geoRegionToGeoRegionTlEntityMapper.map(region)
            )
        } else {
            localRegionDataSource.updateRegion(
                mappers.geoRegionToGeoRegionEntityMapper.map(region),
                mappers.geoRegionToGeoRegionTlEntityMapper.map(region)
            )
        }
        emit(region)
    }

    override fun delete(region: GeoRegion) = flow {
        localRegionDataSource.deleteRegion(mappers.geoRegionToGeoRegionEntityMapper.map(region))
        this.emit(region)
    }

    override fun deleteById(regionId: UUID) = flow {
        localRegionDataSource.deleteRegionById(regionId)
        this.emit(regionId)
    }

    override suspend fun deleteAll() = localRegionDataSource.deleteAllRegions()

}