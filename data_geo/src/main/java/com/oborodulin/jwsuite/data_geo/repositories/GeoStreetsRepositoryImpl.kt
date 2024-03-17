package com.oborodulin.jwsuite.data_geo.repositories

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoStreetsRepositoryImpl @Inject constructor(
    private val localStreetDataSource: LocalGeoStreetDataSource,
    private val domainMappers: GeoStreetMappers,
    private val csvMappers: GeoStreetCsvMappers
) : GeoStreetsRepository {
    override fun getAll() = localStreetDataSource.getAllStreets()
        .map(domainMappers.streetViewListToGeoStreetsListMapper::map)

    override fun getAllByLocality(localityId: UUID, isPrivateSector: Boolean?) =
        localStreetDataSource.getLocalityStreets(localityId, isPrivateSector)
            .map(domainMappers.streetViewListToGeoStreetsListMapper::map)

    override fun getAllByLocalityDistrict(localityDistrictId: UUID, isPrivateSector: Boolean?) =
        localStreetDataSource.getLocalityDistrictStreets(localityDistrictId, isPrivateSector)
            .map(domainMappers.streetViewListToGeoStreetsListMapper::map)

    override fun getAllByMicrodistrict(microdistrictId: UUID, isPrivateSector: Boolean?) =
        localStreetDataSource.getMicrodistrictStreets(microdistrictId, isPrivateSector)
            .map(domainMappers.streetViewListToGeoStreetsListMapper::map)

    override fun getAllForTerritory(
        localityId: UUID, localityDistrictId: UUID?, microdistrictId: UUID?, excludes: List<UUID>
    ) = localStreetDataSource.getStreetsForTerritory(
        localityId, localityDistrictId, microdistrictId, excludes
    ).map(domainMappers.streetViewListToGeoStreetsListMapper::map)

    override fun get(streetId: UUID) = localStreetDataSource.getStreet(streetId)
        .map(domainMappers.geoStreetViewToGeoStreetMapper::map)

    override fun save(street: GeoStreet) = flow {
        if (street.id == null) {
            localStreetDataSource.insertStreet(
                domainMappers.geoStreetToGeoStreetEntityMapper.map(street),
                domainMappers.geoStreetToGeoStreetTlEntityMapper.map(street)
            )
        } else {
            localStreetDataSource.updateStreet(
                domainMappers.geoStreetToGeoStreetEntityMapper.map(street),
                domainMappers.geoStreetToGeoStreetTlEntityMapper.map(street)
            )
        }
        emit(street)
    }

    override fun delete(street: GeoStreet) = flow {
        localStreetDataSource.deleteStreet(
            domainMappers.geoStreetToGeoStreetEntityMapper.map(street)
        )
        this.emit(street)
    }

    override fun delete(streetId: UUID) = flow {
        localStreetDataSource.deleteStreetById(streetId)
        this.emit(streetId)
    }

    override suspend fun deleteAll() = localStreetDataSource.deleteAllStreets()

    // Districts:
    override suspend fun deleteStreetDistrict(streetDistrictId: UUID) =
        localStreetDataSource.deleteStreetDistrict(streetDistrictId)

    // Locality Districts:
    override fun insertStreetLocalityDistricts(streetId: UUID, localityDistrictIds: List<UUID>) =
        flow {
            localityDistrictIds.forEach {
                localStreetDataSource.insertStreetLocalityDistrict(streetId, it)
            }
            this.emit(localityDistrictIds)
        }

    override fun deleteLocalityDistrict(streetId: UUID, localityDistrictId: UUID) = flow {
        localStreetDataSource.deleteStreetLocalityDistrict(streetId, localityDistrictId)
        this.emit(localityDistrictId)
    }

    // Microdistricts:
    override fun insertStreetMicrodistricts(streetId: UUID, districtIds: Map<UUID, List<UUID>>) =
        flow {
            districtIds.forEach { (localityDistrictId, microdistrictIds) ->
                microdistrictIds.forEach { microdistrictId ->
                    localStreetDataSource.insertStreetMicrodistrict(
                        streetId, localityDistrictId, microdistrictId
                    )
                }
                this.emit(microdistrictIds)
            }
        }

    override fun deleteMicrodistrict(streetId: UUID, microdistrictId: UUID) = flow {
        localStreetDataSource.deleteStreetMicrodistrict(streetId, microdistrictId)
        this.emit(microdistrictId)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoStreetEntity.TABLE_NAME)
    override fun extractStreets() = localStreetDataSource.getStreetEntities()
        .map(csvMappers.geoStreetEntityListToGeoStreetCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoStreetTlEntity.TABLE_NAME)
    override fun extractStreetTls() =
        localStreetDataSource.getStreetTlEntities()
            .map(csvMappers.geoStreetTlEntityListToGeoStreetTlCsvListMapper::map)

    @CsvLoad<GeoStreetCsv>(
        fileNamePrefix = GeoStreetEntity.TABLE_NAME,
        contentType = GeoStreetCsv::class
    )
    override fun loadStreets(streets: List<GeoStreetCsv>) = flow {
        localStreetDataSource.loadStreetEntities(
            csvMappers.geoStreetCsvListToGeoStreetEntityListMapper.map(streets)
        )
        emit(streets.size)
    }

    @CsvLoad<GeoStreetTlCsv>(
        fileNamePrefix = GeoStreetTlEntity.TABLE_NAME,
        contentType = GeoStreetTlCsv::class
    )
    override fun loadStreetTls(streetTls: List<GeoStreetTlCsv>) = flow {
        localStreetDataSource.loadStreetTlEntities(
            csvMappers.geoStreetTlCsvListToGeoStreetTlEntityListMapper.map(streetTls)
        )
        emit(streetTls.size)
    }
}