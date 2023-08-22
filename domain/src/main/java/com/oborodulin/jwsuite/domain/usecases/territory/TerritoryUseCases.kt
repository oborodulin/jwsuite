package com.oborodulin.jwsuite.domain.usecases.territory

data class TerritoryUseCases(
    val getProcessAndLocationTerritoriesUseCase: GetProcessAndLocationTerritoriesUseCase,
    val getCongregationTerritoriesUseCase: GetCongregationTerritoriesUseCase,
    val getTerritoryUseCase: GetTerritoryUseCase,
    val saveTerritoryUseCase: SaveTerritoryUseCase,
    val deleteTerritoryUseCase: DeleteTerritoryUseCase,
    val getTerritoryDetailsUseCase: GetTerritoryDetailsUseCase,
    val handOutTerritoriesUseCase: HandOutTerritoriesUseCase,
)