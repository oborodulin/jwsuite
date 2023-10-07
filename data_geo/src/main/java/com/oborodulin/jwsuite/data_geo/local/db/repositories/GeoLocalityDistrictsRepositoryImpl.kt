package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoLocalityDistrictsRepositoryImpl @Inject constructor(
    private val localLocalityDistrictDataSource: LocalGeoLocalityDistrictDataSource,
    private val mappers: GeoLocalityDistrictMappers
) : GeoLocalityDistrictsRepository {
    override fun getAll() = localLocalityDistrictDataSource.getAllLocalityDistricts()
        .map(mappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun getAllByLocality(localityId: UUID) =
        localLocalityDistrictDataSource.getLocalityDistricts(localityId)
            .map(mappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun getAllByStreet(streetId: UUID) =
        localLocalityDistrictDataSource.getStreetLocalityDistricts(streetId)
            .map(mappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun getAllForStreet(streetId: UUID) =
        localLocalityDistrictDataSource.getLocalityDistrictsForStreet(streetId)
            .map(mappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun get(localityDistrictId: UUID) =
        localLocalityDistrictDataSource.getLocalityDistrict(localityDistrictId)
            .map(mappers.geoLocalityDistrictViewToGeoLocalityDistrictMapper::map)

    override fun save(localityDistrict: GeoLocalityDistrict) = flow {
        if (localityDistrict.id == null) {
            localLocalityDistrictDataSource.insertLocalityDistrict(
                mappers.geoLocalityDistrictToGeoLocalityDistrictEntityMapper.map(localityDistrict),
                mappers.geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper.map(localityDistrict)
            )
        } else {
            localLocalityDistrictDataSource.updateLocalityDistrict(
                mappers.geoLocalityDistrictToGeoLocalityDistrictEntityMapper.map(localityDistrict),
                mappers.geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper.map(localityDistrict)
            )
        }
        emit(localityDistrict)
    }

    override fun delete(localityDistrict: GeoLocalityDistrict) = flow {
        localLocalityDistrictDataSource.deleteLocalityDistrict(
            mappers.geoLocalityDistrictToGeoLocalityDistrictEntityMapper.map(localityDistrict)
        )
        this.emit(localityDistrict)
    }

    override fun deleteById(localityDistrictId: UUID) = flow {
        localLocalityDistrictDataSource.deleteLocalityDistrictById(localityDistrictId)
        this.emit(localityDistrictId)
    }

    override suspend fun deleteAll() = localLocalityDistrictDataSource.deleteAllLocalityDistricts()
}