package com.oborodulin.jwsuite.domain.usecases.geomicrodistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveMicrodistrictUseCase(
    configuration: Configuration,
    private val microdistrictsRepository: GeoMicrodistrictsRepository
) : UseCase<SaveMicrodistrictUseCase.Request, SaveMicrodistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return microdistrictsRepository.save(request.microdistrict)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoMicrodistrictSaveException(it) }
    }

    data class Request(val microdistrict: GeoMicrodistrict) : UseCase.Request
    data class Response(val microdistrict: GeoMicrodistrict) : UseCase.Response
}
