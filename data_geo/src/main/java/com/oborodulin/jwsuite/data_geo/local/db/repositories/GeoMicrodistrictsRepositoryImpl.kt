package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoMicrodistrictsRepositoryImpl @Inject constructor(
    private val localMicrodistrictDataSource: LocalGeoMicrodistrictDataSource,
    private val domainMappers: GeoMicrodistrictMappers,
    private val csvMappers: GeoMicrodistrictCsvMappers
) : GeoMicrodistrictsRepository {
    override fun getAll() = localMicrodistrictDataSource.getAllMicrodistricts()
        .map(domainMappers.microdistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllByLocality(localityId: UUID) =
        localMicrodistrictDataSource.getLocalityMicrodistricts(localityId)
            .map(domainMappers.microdistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllByLocalityDistrict(localityDistrictId: UUID) =
        localMicrodistrictDataSource.getLocalityDistrictMicrodistricts(localityDistrictId)
            .map(domainMappers.microdistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllByStreet(streetId: UUID) =
        localMicrodistrictDataSource.getStreetMicrodistricts(streetId)
            .map(domainMappers.microdistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun getAllForStreet(streetId: UUID) =
        localMicrodistrictDataSource.getMicrodistrictsForStreet(streetId)
            .map(domainMappers.microdistrictViewListToGeoMicrodistrictsListMapper::map)

    override fun get(microdistrictId: UUID) =
        localMicrodistrictDataSource.getMicrodistrict(microdistrictId)
            .map(domainMappers.geoMicrodistrictViewToGeoMicrodistrictMapper::map)

    override fun save(microdistrict: GeoMicrodistrict) = flow {
        if (microdistrict.id == null) {
            localMicrodistrictDataSource.insertMicrodistrict(
                domainMappers.geoMicrodistrictToGeoMicrodistrictEntityMapper.map(microdistrict),
                domainMappers.geoMicrodistrictToGeoMicrodistrictTlEntityMapper.map(microdistrict)
            )
        } else {
            localMicrodistrictDataSource.updateMicrodistrict(
                domainMappers.geoMicrodistrictToGeoMicrodistrictEntityMapper.map(microdistrict),
                domainMappers.geoMicrodistrictToGeoMicrodistrictTlEntityMapper.map(microdistrict)
            )
        }
        emit(microdistrict)
    }

    override fun delete(microdistrict: GeoMicrodistrict) = flow {
        localMicrodistrictDataSource.deleteMicrodistrict(
            domainMappers.geoMicrodistrictToGeoMicrodistrictEntityMapper.map(microdistrict)
        )
        this.emit(microdistrict)
    }

    override fun delete(microdistrictId: UUID) = flow {
        localMicrodistrictDataSource.deleteMicrodistrictById(microdistrictId)
        this.emit(microdistrictId)
    }

    override suspend fun deleteAll() = localMicrodistrictDataSource.deleteAllMicrodistricts()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoMicrodistrictEntity.TABLE_NAME)
    override fun extractMicrodistricts() = localMicrodistrictDataSource.getMicrodistrictEntities()
        .map(csvMappers.geoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoMicrodistrictTlEntity.TABLE_NAME)
    override fun extractMicrodistrictTls() =
        localMicrodistrictDataSource.getMicrodistrictTlEntities()
            .map(csvMappers.geoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper::map)

    @CsvLoad<GeoMicrodistrictCsv>(
        fileNamePrefix = GeoMicrodistrictEntity.TABLE_NAME,
        contentType = GeoMicrodistrictCsv::class
    )
    override fun loadMicrodistricts(microdistricts: List<GeoMicrodistrictCsv>) = flow {
        localMicrodistrictDataSource.loadMicrodistrictEntities(
            csvMappers.geoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper.map(microdistricts)
        )
        emit(microdistricts.size)
    }

    @CsvLoad<GeoMicrodistrictTlCsv>(
        fileNamePrefix = GeoMicrodistrictTlEntity.TABLE_NAME,
        contentType = GeoMicrodistrictTlCsv::class
    )
    override fun loadMicrodistrictTls(microdistrictTls: List<GeoMicrodistrictTlCsv>) = flow {
        localMicrodistrictDataSource.loadMicrodistrictTlEntities(
            csvMappers.geoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper.map(
                microdistrictTls
            )
        )
        emit(microdistrictTls.size)
    }
}