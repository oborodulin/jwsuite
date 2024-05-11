package com.oborodulin.jwsuite.data_geo.sources.remote

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.MicrodistrictApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.service.MicrodistrictService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoMicrodistrictDataSource
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoMicrodistrictDataSourceImpl @Inject constructor(
    private val microdistrictService: MicrodistrictService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoMicrodistrictDataSource {
    override fun getMicrodistricts(
        localityId: UUID,
        localityDistrictId: UUID?,
        geocodeArea: String
    ) = flow {
        try {
            microdistrictService.getMicrodistricts(
                data = MicrodistrictApiModel.data(
                    localityId = localityId,
                    localityDistrictId = localityDistrictId,
                    geocodeArea = geocodeArea
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