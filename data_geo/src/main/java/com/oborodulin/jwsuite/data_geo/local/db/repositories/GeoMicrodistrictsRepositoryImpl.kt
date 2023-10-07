package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoMicrodistrictsRepositoryImpl @Inject constructor(
    private val localMicrodistrictDataSource: LocalGeoMicrodistrictDataSource,
    private val mappers: GeoMicrodistrictMappers
) : GeoMicrodistrictsRepository {
    override fun getAll() = localMicrodistrictDataSource.getAllMicrodistricts()
        .map(mappers.geoMicrodistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllByLocality(localityId: UUID) =
        localMicrodistrictDataSource.getLocalityMicrodistricts(localityId)
            .map(mappers.geoMicrodistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllByLocalityDistrict(localityDistrictId: UUID) =
        localMicrodistrictDataSource.getLocalityDistrictMicrodistricts(localityDistrictId)
            .map(mappers.geoMicrodistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllByStreet(streetId: UUID) =
        localMicrodistrictDataSource.getStreetMicrodistricts(streetId)
            .map(mappers.geoMicrodistrictViewListToGeoMicrodistrictsListMapper::map)
    override fun getAllForStreet(streetId: UUID) =
        localMicrodistrictDataSource.getMicrodistrictsForStreet(streetId)
            .map(mappers.geoMicrodistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun get(microdistrictId: UUID) =
        localMicrodistrictDataSource.getMicrodistrict(microdistrictId)
            .map(mappers.geoMicrodistrictViewToGeoMicrodistrictMapper::map)

    override fun save(microdistrict: GeoMicrodistrict) = flow {
        if (microdistrict.id == null) {
            localMicrodistrictDataSource.insertMicrodistrict(
                mappers.geoMicrodistrictToGeoMicrodistrictEntityMapper.map(microdistrict),
                mappers.geoMicrodistrictToGeoMicrodistrictTlEntityMapper.map(microdistrict)
            )
        } else {
            localMicrodistrictDataSource.updateMicrodistrict(
                mappers.geoMicrodistrictToGeoMicrodistrictEntityMapper.map(microdistrict),
                mappers.geoMicrodistrictToGeoMicrodistrictTlEntityMapper.map(microdistrict)
            )
        }
        emit(microdistrict)
    }

    override fun delete(microdistrict: GeoMicrodistrict) = flow {
        localMicrodistrictDataSource.deleteMicrodistrict(
            mappers.geoMicrodistrictToGeoMicrodistrictEntityMapper.map(microdistrict)
        )
        this.emit(microdistrict)
    }

    override fun deleteById(microdistrictId: UUID) = flow {
        localMicrodistrictDataSource.deleteMicrodistrictById(microdistrictId)
        this.emit(microdistrictId)
    }

    override suspend fun deleteAll() = localMicrodistrictDataSource.deleteAllMicrodistricts()
}