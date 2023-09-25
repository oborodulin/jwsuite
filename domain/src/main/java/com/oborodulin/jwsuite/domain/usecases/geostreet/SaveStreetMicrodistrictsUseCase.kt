package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveStreetMicrodistrictsUseCase(
    configuration: Configuration, private val streetsRepository: GeoStreetsRepository
) : UseCase<SaveStreetMicrodistrictsUseCase.Request, SaveStreetMicrodistrictsUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return streetsRepository.insertStreetMicrodistricts(request.streetId, request.districtIds)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoStreetMicrodistrictsSaveException(it) }
    }

    data class Request(val streetId: UUID, val districtIds: Map<UUID, UUID> = mapOf()) :
        UseCase.Request

    data class Response(val microdistrictIds: List<UUID>) : UseCase.Response
}
