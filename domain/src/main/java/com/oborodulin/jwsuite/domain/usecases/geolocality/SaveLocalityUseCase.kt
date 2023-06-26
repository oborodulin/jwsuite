package com.oborodulin.jwsuite.domain.usecases.geolocality

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveLocalityUseCase(
    configuration: Configuration,
    private val localitiesRepository: GeoLocalitiesRepository
) : UseCase<SaveLocalityUseCase.Request, SaveLocalityUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return localitiesRepository.save(request.locality)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoLocalitySaveException(it) }
    }

    data class Request(val locality: GeoLocality) : UseCase.Request
    data class Response(val locality: GeoLocality) : UseCase.Response
}
