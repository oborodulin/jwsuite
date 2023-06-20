package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.model.TerritoryDistrict
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.util.TerritoryDistrictType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoriesRepository {
    fun getCongregationTerritories(congregationId: UUID? = null): Flow<List<Territory>>
    fun getCongregationTerritoryDistricts(isPrivateSector: Boolean, congregationId: UUID):
            Flow<List<TerritoryDistrict>>

    fun getDistrictTerritories(
        territoryDistrictType: TerritoryDistrictType, districtId: UUID, isPrivateSector: Boolean,
        congregationId: UUID? = null
    ): Flow<List<Territory>>

    fun getStreets(territoryId: UUID): Flow<List<TerritoryStreet>>
    fun getHouses(territoryId: UUID): Flow<List<House>>

    fun get(territoryId: UUID): Flow<Territory>
    fun save(territory: Territory): Flow<Territory>
    fun delete(territory: Territory): Flow<Territory>
    fun deleteById(territoryId: UUID): Flow<UUID>
    suspend fun deleteAll()
}