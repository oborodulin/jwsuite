package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoRegionDistrictsRepositoryImpl @Inject constructor(
    private val localRegionDistrictDataSource: LocalGeoRegionDistrictDataSource,
    private val domainMappers: GeoRegionDistrictMappers,
    private val csvMappers: GeoRegionDistrictCsvMappers
) : GeoRegionDistrictsRepository {
    override fun getAll() = localRegionDistrictDataSource.getAllDistricts()
        .map(domainMappers.regionDistrictViewListToGeoRegionDistrictsListMapper::map)

    override fun getAllByRegion(regionId: UUID) =
        localRegionDistrictDataSource.getRegionDistricts(regionId)
            .map(domainMappers.regionDistrictViewListToGeoRegionDistrictsListMapper::map)

    override fun get(regionDistrictId: UUID) =
        localRegionDistrictDataSource.getRegionDistrict(regionDistrictId)
            .map(domainMappers.geoRegionDistrictViewToGeoRegionDistrictMapper::map)

    override fun save(regionDistrict: GeoRegionDistrict) = flow {
        if (regionDistrict.id == null) {
            localRegionDistrictDataSource.insertRegionDistrict(
                domainMappers.geoRegionDistrictToGeoRegionDistrictEntityMapper.map(regionDistrict),
                domainMappers.geoRegionDistrictToGeoRegionDistrictTlEntityMapper.map(regionDistrict)
            )
        } else {
            localRegionDistrictDataSource.updateRegionDistrict(
                domainMappers.geoRegionDistrictToGeoRegionDistrictEntityMapper.map(regionDistrict),
                domainMappers.geoRegionDistrictToGeoRegionDistrictTlEntityMapper.map(regionDistrict)
            )
        }
        emit(regionDistrict)
    }

    override fun delete(regionDistrict: GeoRegionDistrict) = flow {
        localRegionDistrictDataSource.deleteRegionDistrict(
            domainMappers.geoRegionDistrictToGeoRegionDistrictEntityMapper.map(regionDistrict)
        )
        this.emit(regionDistrict)
    }

    override fun delete(regionDistrictId: UUID) = flow {
        localRegionDistrictDataSource.deleteRegionDistrictById(regionDistrictId)
        this.emit(regionDistrictId)
    }

    override suspend fun deleteAll() = localRegionDistrictDataSource.deleteAllRegionDistricts()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoRegionDistrictEntity.TABLE_NAME)
    override fun extractRegionDistricts() =
        localRegionDistrictDataSource.getRegionDistrictEntities()
            .map(csvMappers.geoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoRegionDistrictTlEntity.TABLE_NAME)
    override fun extractRegionDistrictTls() =
        localRegionDistrictDataSource.getRegionDistrictTlEntities()
            .map(csvMappers.geoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper::map)

    @CsvLoad<GeoRegionDistrictCsv>(
        fileNamePrefix = GeoRegionDistrictEntity.TABLE_NAME,
        contentType = GeoRegionDistrictCsv::class
    )
    override fun loadRegionDistricts(regionDistricts: List<GeoRegionDistrictCsv>) = flow {
        localRegionDistrictDataSource.loadRegionDistrictEntities(
            csvMappers.geoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper.map(
                regionDistricts
            )
        )
        emit(regionDistricts.size)
    }

    @CsvLoad<GeoRegionDistrictTlCsv>(
        fileNamePrefix = GeoRegionDistrictTlEntity.TABLE_NAME,
        contentType = GeoRegionDistrictTlCsv::class
    )
    override fun loadRegionDistrictTls(regionDistrictTls: List<GeoRegionDistrictTlCsv>) = flow {
        localRegionDistrictDataSource.loadRegionDistrictTlEntities(
            csvMappers.geoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper.map(
                regionDistrictTls
            )
        )
        emit(regionDistrictTls.size)
    }
}