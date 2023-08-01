package com.oborodulin.jwsuite.domain.usecases.territory

data class TerritoryUseCases(
    val getTerritoriesUseCase: GetTerritoriesUseCase,
    val getTerritoryUseCase: GetTerritoryUseCase,
    val saveTerritoryUseCase: SaveTerritoryUseCase,
    val deleteTerritoryUseCase: DeleteTerritoryUseCase,
    val handOutTerritoriesUseCase: HandOutTerritoriesUseCase
)