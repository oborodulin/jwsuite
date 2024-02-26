package com.oborodulin.jwsuite.data_geo.sources.remote

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion.GeoRegionApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionApiModel
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoRegionDataSource
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class RemoteGeoRegionDataSourceImpl @Inject constructor(
    private val regionService: RegionService,
    private val mappers: GeoRegionApiMappers,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteGeoRegionDataSource {
    override fun getCountryRegions(countryId: UUID, countryGeocodeArea: String): Flow<List<GeoRegion>> = flow {
        emit(regionService.getRegions(data = RegionApiModel.data(countryId, countryGeocodeArea)))
    }.map { countries ->
        mappers.regionElementsListToGeoRegionsListMapper.map(countries.elements)
    }.catch {
        throw UseCaseException.GeoCountryApiException(it)
    }
}