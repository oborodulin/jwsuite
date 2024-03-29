package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextEntranceUseCase(
    configuration: Configuration, private val entrancesRepository: EntrancesRepository
) : UseCase<GetNextEntranceUseCase.Request, GetNextEntranceUseCase.Response>(configuration) {
    override fun process(request: Request) = entrancesRepository.getNext(request.entranceId).map {
        Response(it)
    }

    data class Request(val entranceId: UUID) : UseCase.Request
    data class Response(val entrance: Entrance?) : UseCase.Response
}