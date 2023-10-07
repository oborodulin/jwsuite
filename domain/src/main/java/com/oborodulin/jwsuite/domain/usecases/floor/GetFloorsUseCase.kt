package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetFloorsUseCase(
    configuration: Configuration, private val floorsRepository: FloorsRepository
) : UseCase<GetFloorsUseCase.Request, GetFloorsUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.territoryId) {
        null -> when (request.houseId) {
            null -> floorsRepository.getAll()
            else -> floorsRepository.getAllByHouse(request.houseId)
        }

        else -> floorsRepository.getAllByTerritory(request.territoryId)
    }.map {
        Response(it)
    }

    data class Request(val houseId: UUID? = null, val territoryId: UUID? = null) : UseCase.Request
    data class Response(val floors: List<Floor>) : UseCase.Response
}