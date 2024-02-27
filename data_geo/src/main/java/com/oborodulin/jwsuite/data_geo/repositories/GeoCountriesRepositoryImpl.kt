package com.oborodulin.jwsuite.data_geo.repositories

import com.oborodulin.home.common.data.network.NetworkBoundResult
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryMappers
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoCountryDataSource
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry.GeoCountryApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.model.country.CountryApiModel
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoCountryDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryTlCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoCountriesRepositoryImpl @Inject constructor(
    private val localCountryDataSource: LocalGeoCountryDataSource,
    private val remoteCountryDataSource: RemoteGeoCountryDataSource,
    private val domainMappers: GeoCountryMappers,
    private val apiMappers: GeoCountryApiMappers,
    private val csvMappers: GeoCountryCsvMappers
) : GeoCountriesRepository {
    override fun getAll(isRemoteFetch: Boolean) =
        object : NetworkBoundResult<List<GeoCountry>, CountryApiModel>() {
            override fun loadFromDB() = localCountryDataSource.getCountries()
                .map(domainMappers.geoCountryViewListToGeoCountriesListMapper::map)

            override fun shouldFetch(data: List<GeoCountry>?) =
                isRemoteFetch && data.isNullOrEmpty()

            override suspend fun createCall() = remoteCountryDataSource.getCountries()
            override suspend fun saveCallResult(data: CountryApiModel) {
                apiMappers.countryElementsListToGeoCountriesListMapper.map(data.elements)
                    .forEach { save(it) }
            }
        }.asFlow()

    override fun getDefault() = localCountryDataSource.getDefaultCountry()
        .map(domainMappers.geoCountryViewToGeoCountryMapper::nullableMap)

    override fun get(countryId: UUID) = localCountryDataSource.getCountry(countryId)
        .map(domainMappers.geoCountryViewToGeoCountryMapper::map)

    override fun save(country: GeoCountry) = flow {
        if (country.id == null) {
            localCountryDataSource.insertCountry(
                domainMappers.geoCountryToGeoCountryEntityMapper.map(country),
                domainMappers.geoCountryToGeoCountryTlEntityMapper.map(country)
            )
        } else {
            localCountryDataSource.updateCountry(
                domainMappers.geoCountryToGeoCountryEntityMapper.map(country),
                domainMappers.geoCountryToGeoCountryTlEntityMapper.map(country)
            )
        }
        emit(country)
    }

    override fun delete(country: GeoCountry) = flow {
        localCountryDataSource.deleteCountry(
            domainMappers.geoCountryToGeoCountryEntityMapper.map(country)
        )
        this.emit(country)
    }

    override fun delete(countryId: UUID) = flow {
        localCountryDataSource.deleteCountryById(countryId)
        this.emit(countryId)
    }

    override suspend fun deleteAll() = localCountryDataSource.deleteAllCountries()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GeoCountryEntity.TABLE_NAME)
    override fun extractCountries() = localCountryDataSource.getCountryEntities()
        .map(csvMappers.geoCountryEntityListToGeoCountryCsvListMapper::map)

    @CsvExtract(fileNamePrefix = GeoCountryTlEntity.TABLE_NAME)
    override fun extractCountryTls() = localCountryDataSource.getCountryTlEntities()
        .map(csvMappers.geoCountryTlEntityListToGeoCountryTlCsvListMapper::map)

    @CsvLoad<GeoCountryCsv>(
        fileNamePrefix = GeoCountryEntity.TABLE_NAME,
        contentType = GeoCountryCsv::class
    )
    override fun loadCountries(countries: List<GeoCountryCsv>) = flow {
        localCountryDataSource.loadCountryEntities(
            csvMappers.geoCountryCsvListToGeoCountryEntityListMapper.map(countries)
        )
        emit(countries.size)
    }

    @CsvLoad<GeoCountryTlCsv>(
        fileNamePrefix = GeoCountryTlEntity.TABLE_NAME,
        contentType = GeoCountryTlCsv::class
    )
    override fun loadCountryTls(countryTls: List<GeoCountryTlCsv>) = flow {
        localCountryDataSource.loadCountryTlEntities(
            csvMappers.geoCountryTlCsvListToGeoCountryTlEntityListMapper.map(countryTls)
        )
        emit(countryTls.size)
    }
}