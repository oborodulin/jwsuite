package com.oborodulin.jwsuite.data_geo.sources.remote

import com.oborodulin.home.common.data.network.ApiResponse
import com.oborodulin.jwsuite.data_geo.remote.osm.model.CountryApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.service.CountryService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoCountryDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoCountryDataSourceImpl @Inject constructor(
    private val countryService: CountryService//,
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoCountryDataSource {
    override fun getCountries() = flow {
        try {
            countryService.getCountries(data = CountryApiModel.data())?.let {
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
    }.map { countries ->
        mappers.countryElementsListToGeoCountriesListMapper.map(countries.elements)
    }.catch {
        throw UseCaseException.GeoCountryApiException(it)
    }
 */