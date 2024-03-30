package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict.RegionDistrictApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoRegionDataSource {
    fun getRegionDistricts(
        regionId: UUID, regionGeocodeArea: String
    ): Flow<ApiResponse<RegionDistrictApiModel>>
}