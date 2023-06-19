package com.oborodulin.jwsuite.domain.usecases

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoritePayerUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<GetFavoritePayerUseCase.Request, GetFavoritePayerUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        congregationsRepository.getFavorite().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val congregation: Congregation) : UseCase.Response
}