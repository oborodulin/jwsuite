package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteFloorUseCase(
    configuration: Configuration, private val floorsRepository: FloorsRepository
) : UseCase<DeleteFloorUseCase.Request, DeleteFloorUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return floorsRepository.delete(request.floorId).map { Response }
    }

    data class Request(val floorId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
