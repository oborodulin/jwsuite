package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.MicrodistrictWithStreets
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoMicrodistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} ORDER BY microdistrictName")
    fun findAll(): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE microdistrictId = :microdistrictId")
    fun findById(microdistrictId: UUID): Flow<GeoMicrodistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(microdistrictId: UUID) = findById(microdistrictId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE mLocalitiesId = :localityId ORDER BY microdistrictName")
    fun findByLocalityId(localityId: UUID): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE mLocalityDistrictsId = :localityDistrictId ORDER BY microdistrictName")
    fun findByLocalityDistrictId(localityDistrictId: UUID): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityDistrictId(localityDistrictId: UUID) =
        findByLocalityDistrictId(localityDistrictId).distinctUntilChanged()

    @Query(
        """
       SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} 
        WHERE mLocalitiesId = :localityId
            AND ifnull(mLocalityDistrictsId, '') = ifnull(:localityDistrictId, ifnull(mLocalityDistrictsId, '')) 
            AND microdistrictName LIKE '%' || :microdistrictName || '%' 
        ORDER BY microdistrictName
    """
    )
    fun findByMicrodistrictName(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictName: String
    ): Flow<List<GeoMicrodistrictView>>

    @Transaction
    @Query("SELECT * FROM ${GeoMicrodistrictEntity.TABLE_NAME} WHERE microdistrictId = :microdistrictId")
    fun findDistrictStreetsById(microdistrictId: UUID): Flow<List<MicrodistrictWithStreets>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(microdistrict: GeoMicrodistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg microdistricts: GeoMicrodistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(microdistricts: List<GeoMicrodistrictEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg textContent: GeoMicrodistrictTlEntity)

    @Transaction
    suspend fun insert(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    ) {
        insert(microdistrict)
        insert(textContent)
    }

    // UPDATES:
    @Update
    suspend fun update(microdistrict: GeoMicrodistrictEntity)

    @Update
    suspend fun update(vararg microdistricts: GeoMicrodistrictEntity)

    @Update
    suspend fun update(vararg textContent: GeoMicrodistrictTlEntity)

    @Transaction
    suspend fun update(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    ) {
        update(microdistrict)
        update(textContent)
    }

    // DELETES:
    @Delete
    suspend fun delete(microdistrict: GeoMicrodistrictEntity)

    @Delete
    suspend fun delete(vararg microdistricts: GeoMicrodistrictEntity)

    @Delete
    suspend fun delete(microdistricts: List<GeoMicrodistrictEntity>)

    @Query("DELETE FROM ${GeoMicrodistrictEntity.TABLE_NAME} WHERE microdistrictId = :microdistrictId")
    suspend fun deleteById(microdistrictId: UUID)

    @Query("DELETE FROM ${GeoMicrodistrictEntity.TABLE_NAME}")
    suspend fun deleteAll()
}