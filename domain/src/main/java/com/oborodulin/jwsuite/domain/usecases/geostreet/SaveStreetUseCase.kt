package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveStreetUseCase(
    configuration: Configuration, private val streetsRepository: GeoStreetsRepository
) : UseCase<SaveStreetUseCase.Request, SaveStreetUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return streetsRepository.save(request.street)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoStreetSaveException(it) }
    }

    data class Request(val street: GeoStreet) : UseCase.Request
    data class Response(val street: GeoStreet) : UseCase.Response
}
