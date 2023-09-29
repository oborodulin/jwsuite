package com.oborodulin.jwsuite.data.local.datastore.repositories


import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class SessionManagerRepositoryImpl @Inject constructor() :
    SessionManagerRepository {
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