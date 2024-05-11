package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionDistrictApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoRegionDistrictDataSource {
    fun getRegionDistricts(
        regionId: UUID, regionGeocodeArea: String
    ): Flow<ApiResponse<RegionDistrictApiModel>>
}