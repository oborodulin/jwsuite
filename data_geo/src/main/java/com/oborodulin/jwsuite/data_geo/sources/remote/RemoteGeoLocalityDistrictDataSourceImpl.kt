package com.oborodulin.jwsuite.data_geo.sources.remote

import android.content.Context
import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.LocalityDistrictApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.service.LocalityDistrictService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.domain.types.LocalityDistrictType
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoLocalityDistrictDataSourceImpl @Inject constructor(
    private val ctx: Context,
    private val localityDistrictService: LocalityDistrictService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoLocalityDistrictDataSource {
    override fun getLocalityDistricts(
        localityId: UUID,
        localityGeocodeArea: String
    ) = flow {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.locality_district_full_types)
        try {
            localityDistrictService.getLocalityDistricts(
                data = LocalityDistrictApiModel.data(
                    localityId = localityId,
                    geocodeArea = localityGeocodeArea,
                    incLocalityDistrictType = resArray[LocalityDistrictType.BOROUGH.ordinal]
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