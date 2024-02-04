package com.oborodulin.jwsuite.domain.usecases.entrance

data class EntranceUseCases(
    val getEntrancesUseCase: GetEntrancesUseCase,
    val getEntranceUseCase: GetEntranceUseCase,
    val getNextEntranceUseCase: GetNextEntranceUseCase,
    val getNextEntranceNumUseCase: GetNextEntranceNumUseCase,
    val getEntrancesForTerritoryUseCase: GetEntrancesForTerritoryUseCase,
    val saveEntranceUseCase: SaveEntranceUseCase,
    val saveTerritoryEntrancesUseCase: SaveTerritoryEntrancesUseCase,
    val deleteEntranceUseCase: DeleteEntranceUseCase,
    val deleteTerritoryEntranceUseCase: DeleteTerritoryEntranceUseCase
)