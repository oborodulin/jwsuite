package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoRegionDataSource {
    fun getCountryRegions(
        countryId: UUID,
        countryGeocodeArea: String,
        countryCode: String
    ): Flow<ApiResponse<RegionApiModel>>
}