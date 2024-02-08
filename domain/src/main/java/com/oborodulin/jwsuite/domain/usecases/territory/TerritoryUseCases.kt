package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.jwsuite.domain.usecases.territory.street.DeleteTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.SaveTerritoryStreetUseCase

data class TerritoryUseCases(
    val getProcessAndLocationTerritoriesUseCase: GetProcessAndLocationTerritoriesUseCase,
    val getCongregationTerritoriesUseCase: GetCongregationTerritoriesUseCase,
    val getTerritoryUseCase: GetTerritoryUseCase,
    val getNextTerritoryNumUseCase:GetNextTerritoryNumUseCase,
    val saveTerritoryUseCase: SaveTerritoryUseCase,
    val deleteTerritoryUseCase: DeleteTerritoryUseCase,
    val getTerritoryDetailsUseCase: GetTerritoryDetailsUseCase,
    val getFavoriteTerritoryTotalsUseCase: GetFavoriteTerritoryTotalsUseCase,

    val handOutTerritoriesUseCase: HandOutTerritoriesUseCase,
    val processTerritoriesUseCase: ProcessTerritoriesUseCase
)