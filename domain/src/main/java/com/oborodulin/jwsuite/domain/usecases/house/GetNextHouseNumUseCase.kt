package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextHouseNumUseCase(
    configuration: Configuration, private val housesRepository: HousesRepository
) : UseCase<GetNextHouseNumUseCase.Request, GetNextHouseNumUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        housesRepository.getNextNum(request.streetId).map { Response(it) }

    data class Request(val streetId: UUID) : UseCase.Request
    data class Response(val houseNum: Int) : UseCase.Response
}