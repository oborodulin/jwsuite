package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoLocalitiesRepositoryImpl @Inject constructor(
    private val localLocalityDataSource: LocalGeoLocalityDataSource,
    private val mappers: GeoLocalityMappers
) : GeoLocalitiesRepository {
    override fun getAll() = localLocalityDataSource.getAllLocalities()
        .map(mappers.geoLocalityViewListToGeoLocalitiesListMapper::map)

    override fun getAllByRegion(regionId: UUID) =
        localLocalityDataSource.getRegionLocalities(regionId)
            .map(mappers.geoLocalityViewListToGeoLocalitiesListMapper::map)

    override fun getAllByRegionDistrict(regionDistrictId: UUID) =
        localLocalityDataSource.getRegionDistrictLocalities(regionDistrictId)
            .map(mappers.geoLocalityViewListToGeoLocalitiesListMapper::map)

    override fun get(localityId: UUID) =
        localLocalityDataSource.getLocality(localityId)
            .map(mappers.geoLocalityViewToGeoLocalityMapper::map)

    override fun save(locality: GeoLocality) = flow {
        if (locality.id == null) {
            localLocalityDataSource.insertLocality(
                mappers.geoLocalityToGeoLocalityEntityMapper.map(locality),
                mappers.geoLocalityToGeoLocalityTlEntityMapper.map(locality)
            )
        } else {
            localLocalityDataSource.updateLocality(
                mappers.geoLocalityToGeoLocalityEntityMapper.map(locality),
                mappers.geoLocalityToGeoLocalityTlEntityMapper.map(locality)
            )
        }
        emit(locality)
    }

    override fun delete(locality: GeoLocality) = flow {
        localLocalityDataSource.deleteLocality(
            mappers.geoLocalityToGeoLocalityEntityMapper.map(locality)
        )
        this.emit(locality)
    }

    override fun deleteById(localityId: UUID) = flow {
        localLocalityDataSource.deleteLocalityById(localityId)
        this.emit(localityId)
    }

    override suspend fun deleteAll() = localLocalityDataSource.deleteAllLocalities()
}