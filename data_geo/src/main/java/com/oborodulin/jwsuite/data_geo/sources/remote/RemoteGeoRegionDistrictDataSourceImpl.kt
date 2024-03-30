package com.oborodulin.jwsuite.data_geo.sources.remote

import android.content.Context
import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict.RegionDistrictApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict.RegionDistrictService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoRegionDataSource
import com.oborodulin.jwsuite.domain.types.RegionDistrictType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoRegionDistrictDataSourceImpl @Inject constructor(
    private val ctx: Context,
    private val regionDistrictService: RegionDistrictService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoRegionDataSource {
    override fun getRegionDistricts(
        regionId: UUID,
        regionGeocodeArea: String
    ): Flow<ApiResponse<RegionDistrictApiModel>> = flow {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.region_district_full_types)
        try {
            regionDistrictService.getRegionDistricts(
                data = RegionDistrictApiModel.data(
                    regionId = regionId,
                    geocodeArea = regionGeocodeArea,
                    incRegionDistrictType = resArray[RegionDistrictType.BOROUGH.ordinal]
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