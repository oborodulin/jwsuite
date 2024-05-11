package com.oborodulin.jwsuite.data_geo.repositories

import com.oborodulin.home.common.data.network.NetworkBoundResult
import com.oborodulin.home.common.util.LogLevel.LOG_DATABASE
import com.oborodulin.home.common.util.LogLevel.LOG_NETWORK
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion.GeoRegionApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionApiModel
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoRegionDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionTlCsv
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Data_Geo.GeoRegionsRepositoryImpl"

class GeoRegionsRepositoryImpl @Inject constructor(
    private val localRegionDataSource: LocalGeoRegionDataSource,
    private val remoteRegionDataSource: RemoteGeoRegionDataSource,
    private val domainMappers: GeoRegionMappers,
    private val apiMappers: GeoRegionApiMappers,
    private val csvMappers: GeoRegionCsvMappers
) : GeoRegionsRepository {
    override fun getAll() = localRegionDataSource.getRegions()
        .map(domainMappers.regionViewListToGeoRegionsListMapper::map)

    override fun getAllByCountry(
        countryId: UUID, countryGeocodeArea: String, countryCode: String, isRemoteFetch: Boolean
    ) = object : NetworkBoundResult<List<GeoRegion>, RegionApiModel>() {
        override fun loadFromDB() = localRegionDataSource.getCountryRegions(countryId)
            .map(domainMappers.regionViewListToGeoRegionsListMapper::map)

        override fun shouldFetch(data: List<GeoRegion>?) = isRemoteFetch && data.isNullOrEmpty()

        override suspend fun createCall() =
            remoteRegionDataSource.getCountryRegions(countryId, countryGeocodeArea, countryCode)

        override suspend fun saveCallResult(data: RegionApiModel) {
            if (LOG_NETWORK) {
                Timber.tag(TAG).d("saveCallResult(...) called: data = %s", data)
            }
            try {
                val geoRegions =
                    apiMappers.regionElementsListToGeoRegionsListMapper.map(data.elements)
                if (LOG_NETWORK) {
                    Timber.tag(TAG).d("geoRegions = %s", geoRegions)
                }
                geoRegions.forEach {
                    val savedRegion = save(it).first()
                    if (LOG_NETWORK) {
                        Timber.tag(TAG).d("savedRegion = %s", savedRegion)
                    }
                }
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
            }
        }
    }.asFlow()


    override fun get(regionId: UUID) = localRegionDataSource.getRegion(regionId)
        .map(domainMappers.geoRegionViewToGeoRegionMapper::map)

    override fun save(region: GeoRegion) = flow {
        if (LOG_DATABASE) {
            Timber.tag(TAG).d("save(...) called: region = %s", region)
        }
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

    override fun delete(regionId: UUID) = flow {
        localRegionDataSource.deleteRegionById(regionId)
        this.emit(regionId)
    }

    override suspend fun deleteAll() = localRegionDataSource.deleteAllRegions()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoRegionEntity.TABLE_NAME)
    override fun extractRegions() = localRegionDataSource.getRegionEntities()
        .map(csvMappers.geoRegionEntityListToGeoRegionCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoRegionTlEntity.TABLE_NAME)
    override fun extractRegionTls() = localRegionDataSource.getRegionTlEntities()
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