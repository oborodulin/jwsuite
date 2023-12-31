package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetHousesUseCase(
    configuration: Configuration, private val housesRepository: HousesRepository
) : UseCase<GetHousesUseCase.Request, GetHousesUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.territoryId) {
        null -> when (request.streetId) {
            null -> housesRepository.getAll()
            else -> housesRepository.getAllByStreet(request.streetId)
        }

        else -> housesRepository.getAllByTerritory(request.territoryId)
    }.map {
        Response(it)
    }

    data class Request(val streetId: UUID? = null, val territoryId: UUID? = null) : UseCase.Request
    data class Response(val houses: List<House>) : UseCase.Response
}