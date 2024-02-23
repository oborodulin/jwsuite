package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoRegionDistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoRegionDistrictEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoRegionDistrictEntity>>

    @Query("SELECT * FROM ${GeoRegionDistrictTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoRegionDistrictTlEntity>>

    @Query("SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${GeoRegionDistrictEntity.PX}regDistrictLocCode = :locale ORDER BY ${GeoRegionDistrictEntity.PX}rRegionsId, ${GeoRegionDistrictEntity.PX}regDistrictName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoRegionDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${GeoRegionDistrictEntity.PX}regionDistrictId = :regionDistrictId AND ${GeoRegionDistrictEntity.PX}regDistrictLocCode = :locale")
    fun findById(
        regionDistrictId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<GeoRegionDistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(regionDistrictId: UUID) = findById(regionDistrictId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${GeoRegionDistrictEntity.PX}rRegionsId = :regionId AND ${GeoRegionDistrictEntity.PX}regDistrictLocCode = :locale ORDER BY ${GeoRegionDistrictEntity.PX}regDistrictName"
    )
    fun findByRegionId(regionId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoRegionDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRegionId(regionId: UUID) = findByRegionId(regionId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${GeoRegionDistrictEntity.PX}rRegionsId = :regionId AND ${GeoRegionDistrictEntity.PX}regDistrictName LIKE '%' || :districtName || '%' ORDER BY ${GeoRegionDistrictEntity.PX}regDistrictName")
    fun findByDistrictName(regionId: UUID, districtName: String): Flow<List<GeoRegionDistrictView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(regionDistrict: GeoRegionDistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg regionDistricts: GeoRegionDistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(regionDistricts: List<GeoRegionDistrictEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTls(regionDistrictTls: List<GeoRegionDistrictTlEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg textContent: GeoRegionDistrictTlEntity)

    @Transaction
    suspend fun insert(
        regionDistrict: GeoRegionDistrictEntity, textContent: GeoRegionDistrictTlEntity
    ) {
        insert(regionDistrict)
        insert(textContent)
    }

    // UPDATES:
    @Update
    suspend fun update(regionDistrict: GeoRegionDistrictEntity)

    @Update
    suspend fun update(vararg regionDistricts: GeoRegionDistrictEntity)

    @Update
    suspend fun update(vararg textContent: GeoRegionDistrictTlEntity)

    @Transaction
    suspend fun update(
        regionDistrict: GeoRegionDistrictEntity, textContent: GeoRegionDistrictTlEntity
    ) {
        update(regionDistrict)
        update(textContent)
    }

    // DELETES:
    @Delete
    suspend fun delete(regionDistrict: GeoRegionDistrictEntity)

    @Delete
    suspend fun delete(vararg regionDistricts: GeoRegionDistrictEntity)

    @Delete
    suspend fun delete(regionDistricts: List<GeoRegionDistrictEntity>)

    @Query("DELETE FROM ${GeoRegionDistrictEntity.TABLE_NAME} WHERE regionDistrictId = :regionDistrictId")
    suspend fun deleteById(regionDistrictId: UUID)

    @Query("DELETE FROM ${GeoRegionDistrictEntity.TABLE_NAME}")
    suspend fun deleteAll()
}