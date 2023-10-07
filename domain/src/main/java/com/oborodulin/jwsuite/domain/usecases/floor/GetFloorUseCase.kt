package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetFloorUseCase(
    configuration: Configuration, private val floorsRepository: FloorsRepository
) : UseCase<GetFloorUseCase.Request, GetFloorUseCase.Response>(configuration) {
    override fun process(request: Request) = floorsRepository.get(request.floorId).map {
        Response(it)
    }

    data class Request(val floorId: UUID) : UseCase.Request
    data class Response(val floor: Floor) : UseCase.Response
}