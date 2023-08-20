package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoRegionDao {
    // READS:
    @Query("SELECT * FROM ${GeoRegionView.VIEW_NAME} WHERE regionLocCode = :locale ORDER BY regionName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoRegionView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoRegionView.VIEW_NAME} WHERE regionId = :regionId AND regionLocCode = :locale")
    fun findById(regionId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoRegionView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    /*
        @Query(
            """
            SELECT r.* FROM ${GeoRegionView.VIEW_NAME} r JOIN ${GeoLocalityEntity.TABLE_NAME} l ON l.lRegionsId = r.regionId AND r.regionLocCode = :locale
                JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.cLocalitiesId = l.localityId
        """
        )
        fun findByFavoriteCongregation(locale: String? = Locale.getDefault().language):
                Flow<GeoRegionView>

        @ExperimentalCoroutinesApi
        fun findDistinctByFavoriteCongregation() = findByFavoriteCongregation().distinctUntilChanged()
    */
    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(region: GeoRegionEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg regions: GeoRegionEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(regions: List<GeoRegionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg textContent: GeoRegionTlEntity)

    @Transaction
    suspend fun insert(region: GeoRegionEntity, textContent: GeoRegionTlEntity) {
        insert(region)
        insert(textContent)
    }

    // UPDATES:
    @Update
    suspend fun update(region: GeoRegionEntity)

    @Update
    suspend fun update(vararg regions: GeoRegionEntity)

    @Update
    suspend fun update(vararg textContent: GeoRegionTlEntity)

    @Transaction
    suspend fun update(region: GeoRegionEntity, textContent: GeoRegionTlEntity) {
        update(region)
        update(textContent)
    }

    // DELETES:
    @Delete
    suspend fun delete(region: GeoRegionEntity)

    @Delete
    suspend fun delete(vararg regions: GeoRegionEntity)

    @Delete
    suspend fun delete(regions: List<GeoRegionEntity>)

    @Query("DELETE FROM ${GeoRegionEntity.TABLE_NAME} WHERE regionId = :regionId")
    suspend fun deleteById(regionId: UUID)

    @Query("DELETE FROM ${GeoRegionEntity.TABLE_NAME}")
    suspend fun deleteAll()
}