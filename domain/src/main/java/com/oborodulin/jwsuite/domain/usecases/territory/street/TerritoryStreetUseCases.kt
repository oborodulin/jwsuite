package com.oborodulin.jwsuite.domain.usecases.territory.street

data class TerritoryStreetUseCases(
    val getTerritoryStreetsUseCase: GetTerritoryStreetsUseCase,
    val getTerritoryStreetUseCase: GetTerritoryStreetUseCase,
    val saveTerritoryStreetUseCase: SaveTerritoryStreetUseCase,
    val deleteTerritoryStreetUseCase: DeleteTerritoryStreetUseCase,
)