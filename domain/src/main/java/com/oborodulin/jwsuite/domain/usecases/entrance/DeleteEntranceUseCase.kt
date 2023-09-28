package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteEntranceUseCase(
    configuration: Configuration, private val entrancesRepository: EntrancesRepository
) : UseCase<DeleteEntranceUseCase.Request, DeleteEntranceUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return entrancesRepository.deleteById(request.entranceId).map { Response }
    }

    data class Request(val entranceId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
