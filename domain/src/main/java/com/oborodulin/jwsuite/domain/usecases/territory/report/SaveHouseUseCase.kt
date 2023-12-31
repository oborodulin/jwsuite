package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveHouseUseCase(
    configuration: Configuration,
    private val housesRepository: HousesRepository
) : UseCase<SaveHouseUseCase.Request, SaveHouseUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return housesRepository.save(request.house)
            .map {
                Response(it)
            }.catch { throw UseCaseException.HouseSaveException(it) }
    }

    data class Request(val house: House) : UseCase.Request
    data class Response(val house: House) : UseCase.Response
}
