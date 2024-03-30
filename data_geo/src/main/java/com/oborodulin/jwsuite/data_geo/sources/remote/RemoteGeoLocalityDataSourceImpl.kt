package com.oborodulin.jwsuite.data_geo.sources.remote

import android.content.Context
import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.locality.LocalityApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.model.locality.LocalityService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoLocalityDataSource
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoLocalityDataSourceImpl @Inject constructor(
    private val localityService: LocalityService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoLocalityDataSource {
    override fun getRegionDistrictLocalities(
        regionId: UUID,
        regionDistrictId: UUID?,
        geocodeArea: String
    ) = flow {
        try {
            localityService.getLocalities(
                data = LocalityApiModel.data(
                    regionId = regionId,
                    regionDistrictId = regionDistrictId,
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