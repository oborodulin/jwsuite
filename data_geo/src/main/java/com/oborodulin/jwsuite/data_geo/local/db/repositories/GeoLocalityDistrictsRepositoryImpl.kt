package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoLocalityDistrictsRepositoryImpl @Inject constructor(
    private val localLocalityDistrictDataSource: LocalGeoLocalityDistrictDataSource,
    private val domainMappers: GeoLocalityDistrictMappers,
    private val csvMappers: GeoLocalityDistrictCsvMappers
) : GeoLocalityDistrictsRepository {
    override fun getAll() = localLocalityDistrictDataSource.getAllLocalityDistricts()
        .map(domainMappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun getAllByLocality(localityId: UUID) =
        localLocalityDistrictDataSource.getLocalityDistricts(localityId)
            .map(domainMappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun getAllByStreet(streetId: UUID) =
        localLocalityDistrictDataSource.getStreetLocalityDistricts(streetId)
            .map(domainMappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun getAllForStreet(streetId: UUID) =
        localLocalityDistrictDataSource.getLocalityDistrictsForStreet(streetId)
            .map(domainMappers.geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper::map)

    override fun get(localityDistrictId: UUID) =
        localLocalityDistrictDataSource.getLocalityDistrict(localityDistrictId)
            .map(domainMappers.geoLocalityDistrictViewToGeoLocalityDistrictMapper::map)

    override fun save(localityDistrict: GeoLocalityDistrict) = flow {
        if (localityDistrict.id == null) {
            localLocalityDistrictDataSource.insertLocalityDistrict(
                domainMappers.geoLocalityDistrictToGeoLocalityDistrictEntityMapper.map(
                    localityDistrict
                ),
                domainMappers.geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper.map(
                    localityDistrict
                )
            )
        } else {
            localLocalityDistrictDataSource.updateLocalityDistrict(
                domainMappers.geoLocalityDistrictToGeoLocalityDistrictEntityMapper.map(
                    localityDistrict
                ),
                domainMappers.geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper.map(
                    localityDistrict
                )
            )
        }
        emit(localityDistrict)
    }

    override fun delete(localityDistrict: GeoLocalityDistrict) = flow {
        localLocalityDistrictDataSource.deleteLocalityDistrict(
            domainMappers.geoLocalityDistrictToGeoLocalityDistrictEntityMapper.map(localityDistrict)
        )
        this.emit(localityDistrict)
    }

    override fun delete(localityDistrictId: UUID) = flow {
        localLocalityDistrictDataSource.deleteLocalityDistrictById(localityDistrictId)
        this.emit(localityDistrictId)
    }

    override suspend fun deleteAll() = localLocalityDistrictDataSource.deleteAllLocalityDistricts()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoLocalityDistrictEntity.TABLE_NAME)
    override fun extractLocalityDistricts() =
        localLocalityDistrictDataSource.getLocalityDistrictEntities()
            .map(csvMappers.geoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoLocalityDistrictTlEntity.TABLE_NAME)
    override fun extractLocalityDistrictTls() =
        localLocalityDistrictDataSource.getLocalityDistrictTlEntities()
            .map(csvMappers.geoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper::map)

    @CsvLoad<GeoLocalityDistrictCsv>(
        fileNamePrefix = GeoLocalityDistrictEntity.TABLE_NAME,
        contentType = GeoLocalityDistrictCsv::class
    )
    override fun loadLocalityDistricts(localityDistricts: List<GeoLocalityDistrictCsv>) = flow {
        localLocalityDistrictDataSource.loadLocalityDistrictEntities(
            csvMappers.geoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper.map(
                localityDistricts
            )
        )
        emit(localityDistricts.size)
    }

    @CsvLoad<GeoLocalityDistrictTlCsv>(
        fileNamePrefix = GeoLocalityDistrictTlEntity.TABLE_NAME,
        contentType = GeoLocalityDistrictTlCsv::class
    )
    override fun loadLocalityDistrictTls(localityDistrictTls: List<GeoLocalityDistrictTlCsv>) =
        flow {
            localLocalityDistrictDataSource.loadLocalityDistrictTlEntities(
                csvMappers.geoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper.map(
                    localityDistrictTls
                )
            )
            emit(localityDistrictTls.size)
        }
}