package com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveLocalityDistrictUseCase(
    configuration: Configuration,
    private val localityDistrictsRepository: GeoLocalityDistrictsRepository
) : UseCase<SaveLocalityDistrictUseCase.Request, SaveLocalityDistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return localityDistrictsRepository.save(request.localityDistrict)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoLocalityDistrictSaveException(it) }
    }

    data class Request(val localityDistrict: GeoLocalityDistrict) : UseCase.Request
    data class Response(val localityDistrict: GeoLocalityDistrict) : UseCase.Response
}
