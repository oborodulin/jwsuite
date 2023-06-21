package com.oborodulin.jwsuite.domain.usecases.congregation

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class MakeFavoriteCongregationUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) :
    UseCase<MakeFavoriteCongregationUseCase.Request, MakeFavoriteCongregationUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return congregationsRepository.makeFavoriteById(request.congregationId)
            .map {
                Response
            }
    }

    data class Request(val congregationId: UUID) : UseCase.Request
    object Response : UseCase.Response
}