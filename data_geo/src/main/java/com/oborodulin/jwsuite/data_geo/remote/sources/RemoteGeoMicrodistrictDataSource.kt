package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.microdistrict.MicrodistrictApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoMicrodistrictDataSource {
    fun getMicrodistricts(
        localityId: UUID,
        localityDistrictId: UUID?,
        geocodeArea: String
    ): Flow<ApiResponse<MicrodistrictApiModel>>
}