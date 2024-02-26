package com.oborodulin.jwsuite.data_geo.remote.sources

import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteGeoRegionDataSource {
    fun getCountryRegions(countryId: UUID, countryGeocodeArea: String): Flow<List<GeoRegion>>
}