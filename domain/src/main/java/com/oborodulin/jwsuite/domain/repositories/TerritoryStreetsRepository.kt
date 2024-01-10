package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreetNamesAndHouseNums
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoryStreetsRepository {
    fun get(territoryStreetId: UUID): Flow<TerritoryStreet>
    fun getAllByTerritory(territoryId: UUID): Flow<List<TerritoryStreet>>
    fun getGeoStreetsForTerritory(territoryId: UUID): Flow<List<GeoStreet>>
    fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID? = null): Flow<List<TerritoryStreetNamesAndHouseNums>>
    fun save(territoryStreet: TerritoryStreet): Flow<TerritoryStreet>
    fun deleteById(territoryStreetId: UUID): Flow<UUID>
}