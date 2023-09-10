package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteHouseUseCase(
    configuration: Configuration,
    private val housesRepository: HousesRepository
) : UseCase<DeleteHouseUseCase.Request, DeleteHouseUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return housesRepository.deleteById(request.houseId).map { Response }
    }

    data class Request(val houseId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
