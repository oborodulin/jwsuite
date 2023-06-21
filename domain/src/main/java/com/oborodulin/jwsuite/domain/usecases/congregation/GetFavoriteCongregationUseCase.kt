package com.oborodulin.jwsuite.domain.usecases.congregation

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteCongregationUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<GetFavoriteCongregationUseCase.Request, GetFavoriteCongregationUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        congregationsRepository.getFavorite().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val congregation: Congregation) : UseCase.Response
}