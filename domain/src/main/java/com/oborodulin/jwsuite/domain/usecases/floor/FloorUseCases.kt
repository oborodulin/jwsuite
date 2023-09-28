package com.oborodulin.jwsuite.domain.usecases.floor

data class FloorUseCases(
    val getFloorsUseCase: GetFloorsUseCase,
    val getFloorUseCase: GetFloorUseCase,
    val getNextFloorNumUseCase: GetNextFloorNumUseCase,
    val getFloorsForTerritoryUseCase: GetFloorsForTerritoryUseCase,
    val saveFloorUseCase: SaveFloorUseCase,
    val saveTerritoryFloorsUseCase: SaveTerritoryFloorsUseCase,
    val deleteFloorUseCase: DeleteFloorUseCase,
    val deleteTerritoryFloorUseCase: DeleteTerritoryFloorUseCase
)