package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetEntrancesUseCase(
    configuration: Configuration, private val entrancesRepository: EntrancesRepository
) : UseCase<GetEntrancesUseCase.Request, GetEntrancesUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.territoryId) {
        null -> when (request.houseId) {
            null -> entrancesRepository.getAll()
            else -> entrancesRepository.getAllByHouse(request.houseId)
        }

        else -> entrancesRepository.getAllByTerritory(request.territoryId)
    }.map {
        Response(it)
    }

    data class Request(val houseId: UUID? = null, val territoryId: UUID? = null) : UseCase.Request
    data class Response(val entrances: List<Entrance>) : UseCase.Response
}