package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import kotlinx.coroutines.flow.Flow

interface RemoteCountryDataSource {
    fun getCountries(): Flow<List<GeoCountry>>
}