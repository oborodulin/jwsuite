package com.oborodulin.jwsuite.data_geo.sources.remote

import android.content.Context
import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoRegionDataSource
import com.oborodulin.jwsuite.domain.types.RegionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoRegionDataSourceImpl @Inject constructor(
    private val ctx: Context,
    private val regionService: RegionService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoRegionDataSource {
    override fun getCountryRegions(
        countryId: UUID,
        countryGeocodeArea: String
    ): Flow<ApiResponse<RegionApiModel>> = flow {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.region_full_types)
        try {
            regionService.getRegions(
                data = RegionApiModel.data(
                    countryId = countryId,
                    geocodeArea = countryGeocodeArea,
                    excRegionType = resArray[RegionType.FEDERAL_CITY.ordinal]
                )
            )?.let {
                if (it.elements.isNotEmpty()) {
                    emit(ApiResponse.Success(it))
                } else {
                    emit(ApiResponse.Empty)
                }
            } ?: emit(ApiResponse.Empty)
        } catch (e: Exception) {
            emit(ApiResponse.Error(e))
        }
    }
}
/*
: Flow<List<GeoRegion>> = flow {
        emit(regionService.getRegions(data = RegionApiModel.data(countryId, countryGeocodeArea)))
    }.map { countries ->
        mappers.regionElementsListToGeoRegionsListMapper.map(countries.elements)
    }.catch {
        throw UseCaseException.GeoCountryApiException(it)
    }
 */