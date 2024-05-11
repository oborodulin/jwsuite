package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.LocalityDistrictApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoLocalityDistrictDataSource {
    fun getLocalityDistricts(
        localityId: UUID,
        localityGeocodeArea: String
    ): Flow<ApiResponse<LocalityDistrictApiModel>>
}