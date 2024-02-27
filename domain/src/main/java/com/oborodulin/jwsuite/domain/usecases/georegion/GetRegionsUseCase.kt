package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRegionsUseCase(
    configuration: Configuration, private val regionsRepository: GeoRegionsRepository
) : UseCase<GetRegionsUseCase.Request, GetRegionsUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.countryId) {
        null -> regionsRepository.getAll().map { Response(it) }
        else -> regionsRepository.getAllByCountry(
            request.countryId,
            request.countryGeocodeArea,
            request.isRemoteFetch
        ).map {
            when (it) {
                is Result.Success<List<GeoRegion>> -> Response(it.data)
                else -> Response(emptyList())
            }
        }.catch { throw UseCaseException.GeoRegionApiException(it) }
    }

    data class Request(
        val countryId: UUID? = null,
        val countryGeocodeArea: String = "",
        val isRemoteFetch: Boolean = false
    ) : UseCase.Request

    data class Response(val regions: List<GeoRegion>) : UseCase.Response
}