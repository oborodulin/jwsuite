package com.oborodulin.jwsuite.data_territory.sources.remote

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_territory.remote.osm.model.house.HouseApiModel
import com.oborodulin.jwsuite.data_territory.remote.osm.model.house.HouseService
import com.oborodulin.jwsuite.data_territory.remote.sources.RemoteHouseDataSource
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteHouseDataSourceImpl @Inject constructor(
    private val houseService: HouseService
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteHouseDataSource {
    override fun getHouses(
        streetId: UUID,
        localityDistrictId: UUID?,
        microdistrictId: UUID?,
        geocodeArea: String,
        streetName: String
    ) = flow {
        try {
            houseService.getHouses(
                data = HouseApiModel.data(
                    streetId = streetId,
                    localityDistrictId = localityDistrictId,
                    microdistrictId = microdistrictId,
                    geocodeArea = geocodeArea,
                    streetName = streetName
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