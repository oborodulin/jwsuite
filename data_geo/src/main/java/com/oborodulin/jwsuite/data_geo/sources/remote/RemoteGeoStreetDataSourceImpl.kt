package com.oborodulin.jwsuite.data_geo.sources.remote

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.StreetApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.service.StreetService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoStreetDataSource
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoStreetDataSourceImpl @Inject constructor(
    private val streetService: StreetService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoStreetDataSource {
    override fun getStreets(
        localityId: UUID,
        localityDistrictId: UUID?,
        geocodeArea: String
    ) = flow {
        try {
            streetService.getStreets(
                data = StreetApiModel.data(
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