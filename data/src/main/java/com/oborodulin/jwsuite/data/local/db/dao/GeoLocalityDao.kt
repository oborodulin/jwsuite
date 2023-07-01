package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoLocalityDao {
    // READS:
    @Query("SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE localityLocCode = :locale ORDER BY lRegionsId, lRegionDistrictsId, localityName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoLocalityView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE localityId = :localityId AND localityLocCode = :locale")
    fun findById(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoLocalityView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityId: UUID) = findById(localityId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE lRegionsId = :regionId AND localityLocCode = :locale ORDER BY localityName"
    )
    fun findByRegionId(regionId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoLocalityView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRegionId(payerId: UUID) = findByRegionId(payerId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE lRegionDistrictsId = :regionDistrictId AND localityLocCode = :locale ORDER BY localityName"
    )
    fun findByRegionDistrictId(
        regionDistrictId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<GeoLocalityView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRegionDistrictId(regionDistrictId: UUID) =
        findByRegionDistrictId(regionDistrictId).distinctUntilChanged()

    @Query(
        """
       SELECT * FROM ${GeoLocalityView.VIEW_NAME} 
        WHERE lRegionsId = :regionId 
            AND ifnull(lRegionDistrictsId, '') = ifnull(:regionDistrictId, ifnull(lRegionDistrictsId, '')) 
            AND localityName LIKE '%' || :localityName || '%' 
    """
    )
    fun findByLocalityName(
        regionId: UUID, regionDistrictId: UUID? = null, localityName: String
    ): Flow<List<GeoLocalityView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(locality: GeoLocalityEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg localities: GeoLocalityEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(localities: List<GeoLocalityEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg textContent: GeoLocalityTlEntity)

    @Transaction
    suspend fun insert(locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity) {
        insert(locality)
        insert(textContent)
    }

    // UPDATES:
    @Update
    suspend fun update(locality: GeoLocalityEntity)

    @Update
    suspend fun update(vararg localities: GeoLocalityEntity)

    @Update
    suspend fun update(vararg textContent: GeoLocalityTlEntity)

    @Transaction
    suspend fun update(locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity) {
        update(locality)
        update(textContent)
    }

    // DELETES:
    @Delete
    suspend fun delete(locality: GeoLocalityEntity)

    @Delete
    suspend fun delete(vararg localities: GeoLocalityEntity)

    @Delete
    suspend fun delete(localities: List<GeoLocalityEntity>)

    @Query("DELETE FROM ${GeoLocalityEntity.TABLE_NAME} WHERE localityId = :localityId")
    suspend fun deleteById(localityId: UUID)

    @Query("DELETE FROM ${GeoLocalityEntity.TABLE_NAME}")
    suspend fun deleteAll()
}