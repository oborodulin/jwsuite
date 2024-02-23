package com.oborodulin.jwsuite.data_geo.local.db.repositories.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoCountryView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoCountryDataSource {
    fun getCountries(): Flow<List<GeoCountryView>>
    fun getCountry(countryId: UUID): Flow<GeoCountryView>
    suspend fun insertCountry(country: GeoCountryEntity, textContent: GeoCountryTlEntity)
    suspend fun updateCountry(country: GeoCountryEntity, textContent: GeoCountryTlEntity)
    suspend fun deleteCountry(country: GeoCountryEntity)
    suspend fun deleteCountryById(countryId: UUID)
    suspend fun deleteCountries(countries: List<GeoCountryEntity>)
    suspend fun deleteAllCountries()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getCountryEntities(): Flow<List<GeoCountryEntity>>
    fun getCountryTlEntities(): Flow<List<GeoCountryTlEntity>>
    suspend fun loadCountryEntities(countries: List<GeoCountryEntity>)
    suspend fun loadCountryTlEntities(countryTls: List<GeoCountryTlEntity>)
}