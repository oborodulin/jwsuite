package com.oborodulin.jwsuite.domain.usecases.territorycategory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryCategoryUseCase(
    configuration: Configuration,
    private val territoryCategoriesRepository: TerritoryCategoriesRepository
) : UseCase<GetTerritoryCategoryUseCase.Request, GetTerritoryCategoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        territoryCategoriesRepository.get(request.territoryCategoryId).map {
            Response(it)
        }

    data class Request(val territoryCategoryId: UUID) : UseCase.Request
    data class Response(val territoryCategory: TerritoryCategory) : UseCase.Response
}