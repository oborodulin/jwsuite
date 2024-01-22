package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoRegionsRepositoryImpl @Inject constructor(
    private val localRegionDataSource: LocalGeoRegionDataSource,
    private val domainMappers: GeoRegionMappers,
    private val csvMappers: GeoRegionCsvMappers
) : GeoRegionsRepository {
    override fun getAll() = localRegionDataSource.getRegions()
        .map(domainMappers.geoRegionViewListToGeoRegionsListMapper::map)

    override fun get(regionId: UUID) =
        localRegionDataSource.getRegion(regionId)
            .map(domainMappers.geoRegionViewToGeoRegionMapper::map)

    override fun save(region: GeoRegion) = flow {
        if (region.id == null) {
            localRegionDataSource.insertRegion(
                domainMappers.geoRegionToGeoRegionEntityMapper.map(region),
                domainMappers.geoRegionToGeoRegionTlEntityMapper.map(region)
            )
        } else {
            localRegionDataSource.updateRegion(
                domainMappers.geoRegionToGeoRegionEntityMapper.map(region),
                domainMappers.geoRegionToGeoRegionTlEntityMapper.map(region)
            )
        }
        emit(region)
    }

    override fun delete(region: GeoRegion) = flow {
        localRegionDataSource.deleteRegion(domainMappers.geoRegionToGeoRegionEntityMapper.map(region))
        this.emit(region)
    }

    override fun deleteById(regionId: UUID) = flow {
        localRegionDataSource.deleteRegionById(regionId)
        this.emit(regionId)
    }

    override suspend fun deleteAll() = localRegionDataSource.deleteAllRegions()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoRegionEntity.TABLE_NAME)
    override fun extractRegions(username: String?) = localRegionDataSource.getRegionEntities()
        .map(csvMappers.geoRegionEntityListToGeoRegionCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoRegionTlEntity.TABLE_NAME)
    override fun extractRegionTls(username: String?) = localRegionDataSource.getRegionTlEntities()
        .map(csvMappers.geoRegionTlEntityListToGeoRegionTlCsvListMapper::map)

    @CsvLoad<GeoRegionCsv>(
        fileNamePrefix = GeoRegionEntity.TABLE_NAME,
        contentType = GeoRegionCsv::class
    )
    override fun loadRegions(regions: List<GeoRegionCsv>) = flow {
        localRegionDataSource.loadRegionEntities(
            csvMappers.geoRegionCsvListToGeoRegionEntityListMapper.map(regions)
        )
        emit(regions.size)
    }

    @CsvLoad<GeoRegionTlCsv>(
        fileNamePrefix = GeoRegionTlEntity.TABLE_NAME,
        contentType = GeoRegionTlCsv::class
    )
    override fun loadRegionTls(regionTls: List<GeoRegionTlCsv>) = flow {
        localRegionDataSource.loadRegionTlEntities(
            csvMappers.geoRegionTlCsvListToGeoRegionTlEntityListMapper.map(regionTls)
        )
        emit(regionTls.size)
    }
}