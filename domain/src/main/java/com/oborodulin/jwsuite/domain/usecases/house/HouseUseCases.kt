package com.oborodulin.jwsuite.domain.usecases.house

data class HouseUseCases(
    val getHousesUseCase: GetHousesUseCase,
    val getHouseUseCase: GetHouseUseCase,
    val getNextHouseNumUseCase: GetNextHouseNumUseCase,
    val getHousesForTerritoryUseCase: GetHousesForTerritoryUseCase,
    val saveHouseUseCase: SaveHouseUseCase,
    val deleteHouseUseCase: DeleteHouseUseCase,
    val deleteTerritoryHouseUseCase: DeleteTerritoryHouseUseCase
)