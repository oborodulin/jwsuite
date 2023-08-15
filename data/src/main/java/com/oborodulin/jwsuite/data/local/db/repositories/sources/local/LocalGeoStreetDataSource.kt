package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoStreetDataSource {
    fun getAllStreets(): Flow<List<GeoStreetView>>
    fun getLocalityStreets(localityId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreetView>>

    fun getLocalityDistrictStreets(localityDistrictId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreetView>>

    fun getMicrodistrictStreets(microdistrictId: UUID, isPrivateSector: Boolean? = null):
            Flow<List<GeoStreetView>>

    fun getTerritoryStreets(territoryId: UUID): Flow<List<TerritoryStreetView>>
    fun getTerritoryStreetNames(territoryId: UUID): Flow<String?>
    fun getStreet(streetId: UUID): Flow<GeoStreetView>
    suspend fun insertStreet(street: GeoStreetEntity, textContent: GeoStreetTlEntity)
    suspend fun updateStreet(street: GeoStreetEntity, textContent: GeoStreetTlEntity)
    suspend fun deleteStreet(street: GeoStreetEntity)
    suspend fun deleteStreetById(streetId: UUID)
    suspend fun deleteStreets(streets: List<GeoStreetEntity>)
    suspend fun deleteAllStreets()
}