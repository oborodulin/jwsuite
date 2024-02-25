package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.LocalityDistrictWithStreets
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoLocalityDistrictDao {
    // EXTRACTS:
    @Query("SELECT * FROM ${GeoLocalityDistrictEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoLocalityDistrictEntity>>

    @Query("SELECT * FROM ${GeoLocalityDistrictTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoLocalityDistrictTlEntity>>

    // READS:
    @Query("SELECT * FROM ${LocalityDistrictView.VIEW_NAME} WHERE locDistrictLocCode = :locale ORDER BY locDistrictName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<LocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${GeoLocalityDistrictEntity.PX}locDistrictLocCode = :locale AND ${GeoLocalityDistrictEntity.PX}localityDistrictId = :localityDistrictId")
    fun findById(localityDistrictId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoLocalityDistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityDistrictId: UUID) =
        findById(localityDistrictId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${LocalityDistrictView.VIEW_NAME} WHERE locDistrictLocCode = :locale AND ldLocalitiesId = :localityId ORDER BY locDistrictName")
    fun findByLocalityId(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<LocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT ldv.* FROM ${LocalityDistrictView.VIEW_NAME} ldv JOIN (SELECT dsStreetsId, dsLocalityDistrictsId FROM ${GeoStreetDistrictEntity.TABLE_NAME} GROUP BY dsLocalityDistrictsId, dsStreetsId) sd 
        ON sd.dsStreetsId = :streetId AND ldv.localityDistrictId = sd.dsLocalityDistrictsId AND ldv.locDistrictLocCode = :locale 
    ORDER BY ldv.locDistrictName
        """
    )
    fun findByStreetId(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<LocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByStreetId(streetId: UUID) = findByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT ldv.* FROM ${LocalityDistrictView.VIEW_NAME} ldv JOIN ${StreetView.VIEW_NAME} sv 
        ON sv.streetId = :streetId AND ldv.ldLocalitiesId = sv.sLocalitiesId AND ldv.locDistrictLocCode = sv.streetLocCode AND sv.streetLocCode = :locale
    WHERE NOT EXISTS (SELECT streetDistrictId FROM ${GeoStreetDistrictEntity.TABLE_NAME} WHERE dsStreetsId = :streetId AND dsLocalityDistrictsId = ldv.localityDistrictId) 
    ORDER BY ldv.locDistrictName
        """
    )
    fun findForStreetByStreetId(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<LocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findForStreetDistinctByStreetId(streetId: UUID) =
        findForStreetByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${LocalityDistrictView.VIEW_NAME} WHERE locDistrictLocCode = :locale AND ldLocalitiesId = :localityId AND locDistrictName LIKE '%' || :districtName || '%' ORDER BY locDistrictName")
    fun findByDistrictName(
        localityId: UUID, districtName: String, locale: String? = Locale.getDefault().language
    ): Flow<List<LocalityDistrictView>>

    @Transaction
    @Query("SELECT * FROM ${GeoLocalityDistrictEntity.TABLE_NAME} WHERE localityDistrictId = :localityDistrictId")
    fun findDistrictStreetsById(localityDistrictId: UUID): Flow<List<LocalityDistrictWithStreets>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(localityDistrict: GeoLocalityDistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg localityDistricts: GeoLocalityDistrictEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(localityDistricts: List<GeoLocalityDistrictEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTls(localityDistrictTls: List<GeoLocalityDistrictTlEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg textContent: GeoLocalityDistrictTlEntity)

    @Transaction
    suspend fun insert(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    ) {
        insert(localityDistrict)
        insert(textContent)
    }

    // UPDATES:
    @Update
    suspend fun update(localityDistrict: GeoLocalityDistrictEntity)

    @Update
    suspend fun update(vararg localityDistricts: GeoLocalityDistrictEntity)

    @Update
    suspend fun update(vararg textContent: GeoLocalityDistrictTlEntity)

    @Transaction
    suspend fun update(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    ) {
        update(localityDistrict)
        update(textContent)
    }

    // DELETES:
    @Delete
    suspend fun delete(localityDistrict: GeoLocalityDistrictEntity)

    @Delete
    suspend fun delete(vararg localityDistricts: GeoLocalityDistrictEntity)

    @Delete
    suspend fun delete(localityDistricts: List<GeoLocalityDistrictEntity>)

    @Query("DELETE FROM ${GeoLocalityDistrictEntity.TABLE_NAME} WHERE localityDistrictId = :localityDistrictId")
    suspend fun deleteById(localityDistrictId: UUID)

    @Query("DELETE FROM ${GeoLocalityDistrictEntity.TABLE_NAME}")
    suspend fun deleteAll()
}