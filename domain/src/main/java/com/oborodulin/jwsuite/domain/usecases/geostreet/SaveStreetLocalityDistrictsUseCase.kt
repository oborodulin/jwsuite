package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveStreetLocalityDistrictsUseCase(
    configuration: Configuration, private val streetsRepository: GeoStreetsRepository
) : UseCase<SaveStreetLocalityDistrictsUseCase.Request, SaveStreetLocalityDistrictsUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return streetsRepository.insertStreetLocalityDistricts(
            request.streetId, request.localityDistrictIds
        ).map {
            Response(it)
        }.catch { throw UseCaseException.GeoStreetLocalityDistrictsSaveException(it) }
    }

    data class Request(val streetId: UUID, val localityDistrictIds: List<UUID> = emptyList()) :
        UseCase.Request

    data class Response(val localityDistrictIds: List<UUID>) : UseCase.Response
}
