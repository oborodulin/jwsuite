package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextEntranceNumUseCase(
    configuration: Configuration, private val entrancesRepository: EntrancesRepository
) : UseCase<GetNextEntranceNumUseCase.Request, GetNextEntranceNumUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        entrancesRepository.getNextNum(request.houseId).map { Response(it) }

    data class Request(val houseId: UUID) : UseCase.Request
    data class Response(val entranceNum: Int) : UseCase.Response
}