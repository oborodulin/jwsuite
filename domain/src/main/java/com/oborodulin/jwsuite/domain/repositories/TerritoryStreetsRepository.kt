package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreetNamesAndHouseNums
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoryStreetsRepository {
    fun getTerritoryStreet(territoryStreetId: UUID): Flow<TerritoryStreet>
    fun getTerritoryStreets(territoryId: UUID): Flow<List<TerritoryStreet>>
    fun getStreetsForTerritory(territoryId: UUID): Flow<List<GeoStreet>>
    fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID? = null): Flow<List<TerritoryStreetNamesAndHouseNums>>
    fun saveTerritoryStreet(territoryStreet: TerritoryStreet): Flow<TerritoryStreet>
    fun deleteTerritoryStreetById(territoryStreetId: UUID): Flow<UUID>
}