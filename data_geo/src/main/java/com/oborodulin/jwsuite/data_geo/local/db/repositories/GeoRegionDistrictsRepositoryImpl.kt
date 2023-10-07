package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoRegionDistrictsRepositoryImpl @Inject constructor(
    private val localRegionDistrictDataSource: LocalGeoRegionDistrictDataSource,
    private val mappers: GeoRegionDistrictMappers
) : GeoRegionDistrictsRepository {
    override fun getAll() = localRegionDistrictDataSource.getAllDistricts()
        .map(mappers.geoRegionDistrictViewListToGeoRegionDistrictsListMapper::map)

    override fun getAllByRegion(regionId: UUID) =
        localRegionDistrictDataSource.getRegionDistricts(regionId)
            .map(mappers.geoRegionDistrictViewListToGeoRegionDistrictsListMapper::map)

    override fun get(regionDistrictId: UUID) =
        localRegionDistrictDataSource.getRegionDistrict(regionDistrictId)
            .map(mappers.geoRegionDistrictViewToGeoRegionDistrictMapper::map)

    override fun save(regionDistrict: GeoRegionDistrict) = flow {
        if (regionDistrict.id == null) {
            localRegionDistrictDataSource.insertRegionDistrict(
                mappers.geoRegionDistrictToGeoRegionDistrictEntityMapper.map(regionDistrict),
                mappers.geoRegionDistrictToGeoRegionDistrictTlEntityMapper.map(regionDistrict)
            )
        } else {
            localRegionDistrictDataSource.updateRegionDistrict(
                mappers.geoRegionDistrictToGeoRegionDistrictEntityMapper.map(regionDistrict),
                mappers.geoRegionDistrictToGeoRegionDistrictTlEntityMapper.map(regionDistrict)
            )
        }
        emit(regionDistrict)
    }

    override fun delete(regionDistrict: GeoRegionDistrict) = flow {
        localRegionDistrictDataSource.deleteRegionDistrict(
            mappers.geoRegionDistrictToGeoRegionDistrictEntityMapper.map(regionDistrict)
        )
        this.emit(regionDistrict)
    }

    override fun deleteById(regionDistrictId: UUID) = flow {
        localRegionDistrictDataSource.deleteRegionDistrictById(regionDistrictId)
        this.emit(regionDistrictId)
    }

    override suspend fun deleteAll() = localRegionDistrictDataSource.deleteAllRegionDistricts()
}