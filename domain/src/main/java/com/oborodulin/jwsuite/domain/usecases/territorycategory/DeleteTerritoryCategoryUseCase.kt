package com.oborodulin.jwsuite.domain.usecases.territorycategory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteTerritoryCategoryUseCase(
    configuration: Configuration,
    private val territoryCategoriesRepository: TerritoryCategoriesRepository
) : UseCase<DeleteTerritoryCategoryUseCase.Request, DeleteTerritoryCategoryUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> {
        return territoryCategoriesRepository.delete(request.territoryCategoryId)
            .map {
                Response
            }
    }

    data class Request(val territoryCategoryId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
