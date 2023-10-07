package com.oborodulin.jwsuite.domain.usecases.congregation

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.CongregationTotals
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteCongregationTotalsUseCase(
    configuration: Configuration, private val congregationsRepository: CongregationsRepository
) : UseCase<GetFavoriteCongregationTotalsUseCase.Request, GetFavoriteCongregationTotalsUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        congregationsRepository.getFavoriteTotals().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val congregationTotals: CongregationTotals?) : UseCase.Response
}