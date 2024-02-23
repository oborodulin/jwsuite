package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import kotlinx.coroutines.flow.Flow

interface RemoteRegionDataSource {
    fun getRegions(): Flow<List<GeoRegion>>
}