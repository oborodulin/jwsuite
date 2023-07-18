package com.oborodulin.jwsuite.domain.usecases.territorycategory

data class TerritoryCategoryUseCases(
    val getTerritoryCategoriesUseCase: GetTerritoryCategoriesUseCase,
    val getTerritoryCategoryUseCase: GetTerritoryCategoryUseCase,
    val saveTerritoryCategoryUseCase: SaveTerritoryCategoryUseCase,
    val deleteTerritoryCategoryUseCase: DeleteTerritoryCategoryUseCase
)