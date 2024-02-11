package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoLocalitiesRepositoryImpl @Inject constructor(
    private val localLocalityDataSource: LocalGeoLocalityDataSource,
    private val domainMappers: GeoLocalityMappers,
    private val csvMappers: GeoLocalityCsvMappers
) : GeoLocalitiesRepository {
    override fun getAll() = localLocalityDataSource.getAllLocalities()
        .map(domainMappers.geoLocalityViewListToGeoLocalitiesListMapper::map)

    override fun getAllByRegion(regionId: UUID) =
        localLocalityDataSource.getRegionLocalities(regionId)
            .map(domainMappers.geoLocalityViewListToGeoLocalitiesListMapper::map)

    override fun getAllByRegionDistrict(regionDistrictId: UUID) =
        localLocalityDataSource.getRegionDistrictLocalities(regionDistrictId)
            .map(domainMappers.geoLocalityViewListToGeoLocalitiesListMapper::map)

    override fun get(localityId: UUID) = localLocalityDataSource.getLocality(localityId)
        .map(domainMappers.geoLocalityViewToGeoLocalityMapper::map)

    override fun save(locality: GeoLocality) = flow {
        if (locality.id == null) {
            localLocalityDataSource.insertLocality(
                domainMappers.geoLocalityToGeoLocalityEntityMapper.map(locality),
                domainMappers.geoLocalityToGeoLocalityTlEntityMapper.map(locality)
            )
        } else {
            localLocalityDataSource.updateLocality(
                domainMappers.geoLocalityToGeoLocalityEntityMapper.map(locality),
                domainMappers.geoLocalityToGeoLocalityTlEntityMapper.map(locality)
            )
        }
        emit(locality)
    }

    override fun delete(locality: GeoLocality) = flow {
        localLocalityDataSource.deleteLocality(
            domainMappers.geoLocalityToGeoLocalityEntityMapper.map(locality)
        )
        this.emit(locality)
    }

    override fun delete(localityId: UUID) = flow {
        localLocalityDataSource.deleteLocalityById(localityId)
        this.emit(localityId)
    }

    override suspend fun deleteAll() = localLocalityDataSource.deleteAllLocalities()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoLocalityEntity.TABLE_NAME)
    override fun extractLocalities() = localLocalityDataSource.getLocalityEntities()
        .map(csvMappers.geoLocalityEntityListToGeoLocalityCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoLocalityTlEntity.TABLE_NAME)
    override fun extractLocalityTls() = localLocalityDataSource.getLocalityTlEntities()
        .map(csvMappers.geoLocalityTlEntityListToGeoLocalityTlCsvListMapper::map)

    @CsvLoad<GeoLocalityCsv>(
        fileNamePrefix = GeoLocalityEntity.TABLE_NAME,
        contentType = GeoLocalityCsv::class
    )
    override fun loadLocalities(localities: List<GeoLocalityCsv>) = flow {
        localLocalityDataSource.loadLocalityEntities(
            csvMappers.geoLocalityCsvListToGeoLocalityEntityListMapper.map(localities)
        )
        emit(localities.size)
    }

    @CsvLoad<GeoLocalityTlCsv>(
        fileNamePrefix = GeoLocalityTlEntity.TABLE_NAME,
        contentType = GeoLocalityTlCsv::class
    )
    override fun loadLocalityTls(localityTls: List<GeoLocalityTlCsv>) = flow {
        localLocalityDataSource.loadLocalityTlEntities(
            csvMappers.geoLocalityTlCsvListToGeoLocalityTlEntityListMapper.map(localityTls)
        )
        emit(localityTls.size)
    }
}