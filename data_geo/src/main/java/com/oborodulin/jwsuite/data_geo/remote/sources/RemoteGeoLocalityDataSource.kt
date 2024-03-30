package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.locality.LocalityApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoLocalityDataSource {
    fun getRegionDistrictLocalities(
        regionId: UUID,
        regionDistrictId: UUID?,
        geocodeArea: String
    ): Flow<ApiResponse<LocalityApiModel>>
}