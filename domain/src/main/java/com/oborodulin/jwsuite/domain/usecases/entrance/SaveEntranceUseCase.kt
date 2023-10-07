package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveEntranceUseCase(
    configuration: Configuration, private val entrancesRepository: EntrancesRepository
) : UseCase<SaveEntranceUseCase.Request, SaveEntranceUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return entrancesRepository.save(request.entrance)
            .map {
                Response(it)
            }.catch { throw UseCaseException.EntranceSaveException(it) }
    }

    data class Request(val entrance: Entrance) : UseCase.Request
    data class Response(val entrance: Entrance) : UseCase.Response
}
