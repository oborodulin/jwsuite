package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.views.*
import com.oborodulin.jwsuite.data.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoRegionDao {
    // READS:
    @Query("SELECT * FROM ${GeoRegionView.VIEW_NAME} WHERE regionLocCode = :locale")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoRegionView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoRegionView.VIEW_NAME} WHERE regionId = :regionId AND regionLocCode = :locale")
    fun findById(regionId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoRegionView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query(
        """
        SELECT r.* FROM ${CongregationEntity.TABLE_NAME} c JOIN ${GeoLocalityEntity.TABLE_NAME} l ON l.localityId = c.localitiesId
            JOIN ${GeoRegionView.VIEW_NAME} r ON r.regionId = l.regionsId AND r.regionLocCode = :locale
        WHERE c.isFavorite = ${Constants.DB_TRUE}
    """
    )
    fun findByFavoriteCongregation(locale: String? = Locale.getDefault().language):
            Flow<GeoRegionView>

    @ExperimentalCoroutinesApi
    fun findDistinctByFavoriteCongregation() = findByFavoriteCongregation().distinctUntilChanged()

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