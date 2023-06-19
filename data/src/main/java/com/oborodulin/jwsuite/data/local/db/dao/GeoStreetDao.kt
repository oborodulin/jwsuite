package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoStreetDao {
    // READS:
    @Query("SELECT * FROM ${GeoStreetView.VIEW_NAME} WHERE streetLocCode = :locale")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoStreetView.VIEW_NAME} WHERE streetId = :streetId AND streetLocCode = :locale")
    fun findById(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoStreetView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityId: UUID) = findById(localityId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoStreetView.VIEW_NAME} WHERE localitiesId = :localityId AND streetLocCode = :locale"
    )
    fun findByLocalityId(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    @Query(
        "SELECT sw.* FROM ${GeoStreetView.VIEW_NAME} sw JOIN ${GeoDistrictStreetEntity.TABLE_NAME} sd ON sd.streetsId = sw.streetId AND sd.localityDistrictsId = :localityDistrictId AND sw.streetLocCode = :locale"
    )
    fun findByLocalityDistrictId(
        localityDistrictId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityDistrictId(localityDistrictId: UUID) =
        findByLocalityDistrictId(localityDistrictId).distinctUntilChanged()

    @Query(
        "SELECT sw.* FROM ${GeoStreetView.VIEW_NAME} sw JOIN ${GeoDistrictStreetEntity.TABLE_NAME} sd ON sd.streetsId = sw.streetId AND sd.microdistrictsId = :microdistrictId AND sw.streetLocCode = :locale"
    )
    fun findByMicrodistrictId(
        microdistrictId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByMicrodistrictId(microdistrictId: UUID) =
        findByMicrodistrictId(microdistrictId).distinctUntilChanged()

    @Query(
        "SELECT tsv.* FROM ${TerritoryStreetView.VIEW_NAME} tsv WHERE tsv.territoriesId = :territoryId AND tsv.streetLocCode = :locale"
    )
    fun findByTerritoryId(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    @Query(
        """
        SELECT sw.* FROM ${GeoStreetView.VIEW_NAME} sw JOIN ${GeoDistrictStreetEntity.TABLE_NAME} sd ON sd.streetsId = sw.streetId 
        WHERE sw.localitiesId = :localityId
            AND ifnull(sd.localityDistrictsId, '') = ifnull(:localityDistrictId, ifnull(sd.localityDistrictsId, '')) 
            AND ifnull(sd.microdistrictsId, '') = ifnull(:microdistrictId, ifnull(sd.microdistrictsId, '')) 
            AND sw.streetName LIKE '%' || :streetName || '%'
    """
    )
    fun findByStreetName(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        streetName: String
    ): Flow<List<GeoStreetView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(street: GeoStreetEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg streets: GeoStreetEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(streets: List<GeoStreetEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg textContent: GeoStreetTlEntity)

    @Transaction
    suspend fun insert(street: GeoStreetEntity, textContent: GeoStreetTlEntity) {
        insert(street)
        insert(textContent)
    }

    // UPDATES:
    @Update
    suspend fun update(street: GeoStreetEntity)

    @Update
    suspend fun update(vararg streets: GeoStreetEntity)

    @Update
    suspend fun update(vararg textContent: GeoStreetTlEntity)

    @Transaction
    suspend fun update(street: GeoStreetEntity, textContent: GeoStreetTlEntity) {
        update(street)
        update(textContent)
    }

    // DELETES:
    @Delete
    suspend fun delete(street: GeoStreetEntity)

    @Delete
    suspend fun delete(vararg streets: GeoStreetEntity)

    @Delete
    suspend fun delete(streets: List<GeoStreetEntity>)

    @Query("DELETE FROM ${GeoStreetEntity.TABLE_NAME} WHERE streetId = :streetId")
    suspend fun deleteById(streetId: UUID)

    @Query("DELETE FROM ${GeoStreetEntity.TABLE_NAME}")
    suspend fun deleteAll()
}