package com.oborodulin.jwsuite.domain.usecases.territory.report

data class HouseUseCases(
    val getHousesUseCase: GetHousesUseCase,
    val getHouseUseCase: GetHouseUseCase,
    val getNextHouseNumUseCase: GetNextHouseNumUseCase,
    val getHousesForTerritoryUseCase: GetHousesForTerritoryUseCase,
    val saveHouseUseCase: SaveHouseUseCase,
    val saveTerritoryHousesUseCase: SaveTerritoryHousesUseCase,
    val deleteHouseUseCase: DeleteHouseUseCase,
    val deleteTerritoryHouseUseCase: DeleteTerritoryHouseUseCase
)