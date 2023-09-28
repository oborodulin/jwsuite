package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextFloorNumUseCase(
    configuration: Configuration, private val floorsRepository: FloorsRepository
) : UseCase<GetNextFloorNumUseCase.Request, GetNextFloorNumUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        floorsRepository.getNextNum(request.houseId).map { Response(it) }

    data class Request(val houseId: UUID) : UseCase.Request
    data class Response(val floorNum: Int) : UseCase.Response
}