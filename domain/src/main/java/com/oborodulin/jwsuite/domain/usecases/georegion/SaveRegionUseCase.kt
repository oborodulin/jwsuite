package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveRegionUseCase(
    configuration: Configuration,
    private val regionsRepository: GeoRegionsRepository
) : UseCase<SaveRegionUseCase.Request, SaveRegionUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return regionsRepository.save(request.region)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoRegionSaveException(it) }
    }

    data class Request(val region: GeoRegion) : UseCase.Request
    data class Response(val region: GeoRegion) : UseCase.Response
}
