package com.oborodulin.jwsuite.domain.usecases.territorycategory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveTerritoryCategoryUseCase(
    configuration: Configuration,
    private val territoryCategoriesRepository: TerritoryCategoriesRepository
) : UseCase<SaveTerritoryCategoryUseCase.Request, SaveTerritoryCategoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return territoryCategoriesRepository.save(request.territoryCategory)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryCategorySaveException(it) }
    }

    data class Request(val territoryCategory: TerritoryCategory) : UseCase.Request
    data class Response(val territoryCategory: TerritoryCategory) : UseCase.Response
}
