package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.views.*
import com.oborodulin.jwsuite.data.util.Constants.PX_REGION_DISTRICT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoRegionDistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${PX_REGION_DISTRICT}regDistrictLocCode = :locale ORDER BY ${PX_REGION_DISTRICT}rRegionsId, ${PX_REGION_DISTRICT}regDistrictName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoRegionDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${PX_REGION_DISTRICT}regionDistrictId = :regionDistrictId AND ${PX_REGION_DISTRICT}regDistrictLocCode = :locale")
    fun findById(
        regionDistrictId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<GeoRegionDistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(regionDistrictId: UUID) = findById(regionDistrictId).distinctUntilChanged()

    @Query(
        "SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${PX_REGION_DISTRICT}rRegionsId = :regionId AND ${PX_REGION_DISTRICT}regDistrictLocCode = :locale ORDER BY ${PX_REGION_DISTRICT}regDistrictName"
    )
    fun findByRegionId(regionId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoRegionDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRegionId(regionId: UUID) = findByRegionId(regionId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoRegionDistrictView.VIEW_NAME} WHERE ${PX_REGION_DISTRICT}rRegionsId = :regionId AND ${PX_REGION_DISTRICT}regDistrictName LIKE '%' || :districtName || '%' ORDER BY ${PX_REGION_DISTRICT}regDistrictName")
    fun findByDistrictName(regionId: UUID, districtName: String): Flow<List<GeoRegionDistrictView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(regionDistrict: GeoRegionDistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg regionDistricts: GeoRegionDistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(regionDistricts: List<GeoRegionDistrictEntity>)

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