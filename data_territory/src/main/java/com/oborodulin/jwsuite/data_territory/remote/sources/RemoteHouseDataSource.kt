package com.oborodulin.jwsuite.data_territory.remote.sources

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_territory.remote.osm.model.house.HouseApiModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteHouseDataSource {
    fun getHouses(
        streetId: UUID,
        localityDistrictId: UUID? = null,
        microdistrictId: UUID? = null,
        geocodeArea: String,
        streetName: String
    ): Flow<ApiResponse<HouseApiModel>>
}