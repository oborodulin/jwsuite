package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRegionsUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<GetRegionsUseCase.Request, GetRegionsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        congregationsRepository.getAll().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val congregations: List<Congregation>) : UseCase.Response
}