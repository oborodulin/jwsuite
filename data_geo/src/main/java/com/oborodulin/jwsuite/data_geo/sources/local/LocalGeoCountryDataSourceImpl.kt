package com.oborodulin.jwsuite.data_geo.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoCountryDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoCountryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Data.geo.LocalGeoCountryDataSourceImpl"

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoCountryDataSourceImpl @Inject constructor(
    private val countryDao: GeoCountryDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoCountryDataSource {
    override fun getCountries() = countryDao.findAll()
    override fun getDefaultCountry() = countryDao.findByDefaultLocale()
    override fun getCountry(countryId: UUID) = countryDao.findDistinctById(countryId)
    override suspend fun insertCountry(country: GeoCountryEntity, textContent: GeoCountryTlEntity) =
        withContext(dispatcher) {
            countryDao.insert(country, textContent)
        }

    override suspend fun updateCountry(country: GeoCountryEntity, textContent: GeoCountryTlEntity) =
        withContext(dispatcher) {
            Timber.tag(TAG).d(
                "updateCountry(...) called: country = %s; textContent = %s",
                country,
                textContent
            )
            countryDao.update(country, textContent)
        }

    override suspend fun deleteCountry(country: GeoCountryEntity) = withContext(dispatcher) {
        countryDao.delete(country)
    }

    override suspend fun deleteCountryById(countryId: UUID) = withContext(dispatcher) {
        countryDao.deleteById(countryId)
    }

    override suspend fun deleteCountries(countries: List<GeoCountryEntity>) =
        withContext(dispatcher) {
            countryDao.delete(countries)
        }

    override suspend fun deleteAllCountries() = withContext(dispatcher) {
        countryDao.deleteAll()
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getCountryEntities() = countryDao.selectEntities()
    override fun getCountryTlEntities() = countryDao.selectTlEntities()
    override suspend fun loadCountryEntities(countries: List<GeoCountryEntity>) =
        withContext(dispatcher) {
            countryDao.insert(countries)
        }

    override suspend fun loadCountryTlEntities(countryTls: List<GeoCountryTlEntity>) =
        withContext(dispatcher) {
            countryDao.insertTls(countryTls)
        }
}