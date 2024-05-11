package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.StreetApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoStreetDataSource {
    fun getStreets(
        localityId: UUID,
        localityDistrictId: UUID? = null,
        geocodeArea: String
    ): Flow<ApiResponse<StreetApiModel>>
}