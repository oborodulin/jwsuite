package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoCountriesRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<GeoCountry>>
    fun get(countryId: UUID): Flow<GeoCountry>
    fun save(country: GeoCountry): Flow<GeoCountry>
    fun delete(country: GeoCountry): Flow<GeoCountry>
    fun delete(countryId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractCountries(): Flow<List<GeoCountryCsv>>
    fun extractCountryTls(): Flow<List<GeoCountryTlCsv>>
    fun loadCountries(countries: List<GeoCountryCsv>): Flow<Int>
    fun loadCountryTls(countryTls: List<GeoCountryTlCsv>): Flow<Int>
}