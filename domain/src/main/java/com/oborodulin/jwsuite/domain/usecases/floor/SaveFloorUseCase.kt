package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveFloorUseCase(
    configuration: Configuration, private val floorsRepository: FloorsRepository
) : UseCase<SaveFloorUseCase.Request, SaveFloorUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return floorsRepository.save(request.floor)
            .map {
                Response(it)
            }.catch { throw UseCaseException.FloorSaveException(it) }
    }

    data class Request(val floor: Floor) : UseCase.Request
    data class Response(val floor: Floor) : UseCase.Response
}
