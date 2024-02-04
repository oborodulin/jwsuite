package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextHouseUseCase(
    configuration: Configuration, private val housesRepository: HousesRepository
) : UseCase<GetNextHouseUseCase.Request, GetNextHouseUseCase.Response>(configuration) {
    override fun process(request: Request) = housesRepository.getNext(request.houseId).map {
        Response(it)
    }

    data class Request(val houseId: UUID) : UseCase.Request
    data class Response(val house: House?) : UseCase.Response
}