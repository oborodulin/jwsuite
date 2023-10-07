package com.oborodulin.jwsuite.domain.usecases.territorycategory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTerritoryCategoriesUseCase(
    configuration: Configuration,
    private val territoryCategoriesRepository: TerritoryCategoriesRepository
) : UseCase<GetTerritoryCategoriesUseCase.Request, GetTerritoryCategoriesUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        territoryCategoriesRepository.getAll().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val territoryCategories: List<TerritoryCategory>) : UseCase.Response
}