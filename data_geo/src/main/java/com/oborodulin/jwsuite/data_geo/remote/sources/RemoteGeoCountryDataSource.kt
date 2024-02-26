package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.country.CountryApiModel
import kotlinx.coroutines.flow.Flow

interface RemoteGeoCountryDataSource {
    fun getCountries(): Flow<ApiResponse<CountryApiModel>>
}