package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoRegionDistrictDataSource {
    fun getRegionDistricts(
        countryId: UUID,
        countryGeocodeArea: String
    ): Flow<ApiResponse<RegionApiModel>>
}