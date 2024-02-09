package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoLocalityDao {
    // READS:
    @Query("SELECT * FROM ${GeoLocalityEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoLocalityEntity>>

    @Query("SELECT * FROM ${GeoLocalityTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoLocalityTlEntity>>

    @Query("SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE ${PX_LOCALITY}localityLocCode = :locale ORDER BY ${PX_LOCALITY}lRegionsId, ${PX_LOCALITY}lRegionDistrictsId, ${PX_LOCALITY}localityName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoLocalityView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE ${PX_LOCALITY}localityId = :localityId AND ${PX_LOCALITY}localityLocCode = :locale")
    fun findById(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoLocalityView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityId: UUID) = findById(localityId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE ${PX_LOCALITY}lRegionsId = :regionId AND ${PX_LOCALITY}localityLocCode = :locale ORDER BY ${PX_LOCALITY}localityName"
    )
    fun findByRegionId(regionId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoLocalityView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRegionId(payerId: UUID) = findByRegionId(payerId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoLocalityView.VIEW_NAME} WHERE ${PX_LOCALITY}lRegionDistrictsId = :regionDistrictId AND ${PX_LOCALITY}localityLocCode = :locale ORDER BY ${PX_LOCALITY}localityName"
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
        WHERE ${PX_LOCALITY}lRegionsId = :regionId 
            AND ifnull(${PX_LOCALITY}lRegionDistrictsId, '') = ifnull(:regionDistrictId, ifnull(${PX_LOCALITY}lRegionDistrictsId, '')) 
            AND ${PX_LOCALITY}localityName LIKE '%' || :localityName || '%'
        ORDER BY ${PX_LOCALITY}localityName
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
    suspend fun insertTls(localityTls: List<GeoLocalityTlEntity>)

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