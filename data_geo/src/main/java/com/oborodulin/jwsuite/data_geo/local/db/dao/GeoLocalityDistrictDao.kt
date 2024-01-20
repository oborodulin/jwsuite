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
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoLocalityDistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoLocalityDistrictEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoLocalityDistrictEntity>>

    @Query("SELECT * FROM ${GeoLocalityDistrictTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoLocalityDistrictTlEntity>>

    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale ORDER BY ${PX_LOCALITY_DISTRICT}locDistrictName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale AND ${PX_LOCALITY_DISTRICT}localityDistrictId = :localityDistrictId")
    fun findById(localityDistrictId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoLocalityDistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityDistrictId: UUID) =
        findById(localityDistrictId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale AND ${PX_LOCALITY_DISTRICT}ldLocalitiesId = :localityId ORDER BY ${PX_LOCALITY_DISTRICT}locDistrictName")
    fun findByLocalityId(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT ldv.* FROM ${GeoLocalityDistrictView.VIEW_NAME} ldv JOIN (SELECT dsStreetsId, dsLocalityDistrictsId FROM ${GeoStreetDistrictEntity.TABLE_NAME} GROUP BY dsLocalityDistrictsId, dsStreetsId) sd 
        ON sd.dsStreetsId = :streetId AND ldv.${PX_LOCALITY_DISTRICT}localityDistrictId = sd.dsLocalityDistrictsId 
            AND ldv.${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale 
    ORDER BY ldv.${PX_LOCALITY_DISTRICT}locDistrictName
        """
    )
    fun findByStreetId(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByStreetId(streetId: UUID) = findByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT ldv.* FROM ${GeoLocalityDistrictView.VIEW_NAME} ldv JOIN ${StreetView.VIEW_NAME} sv 
        ON sv.streetId = :streetId AND ldv.${PX_LOCALITY_DISTRICT}ldLocalitiesId = sv.sLocalitiesId 
            AND ldv.${PX_LOCALITY_DISTRICT}locDistrictLocCode = sv.streetLocCode AND sv.streetLocCode = :locale
    WHERE NOT EXISTS (SELECT streetDistrictId FROM ${GeoStreetDistrictEntity.TABLE_NAME} WHERE dsStreetsId = :streetId AND dsLocalityDistrictsId = ldv.${PX_LOCALITY_DISTRICT}localityDistrictId) 
    ORDER BY ldv.${PX_LOCALITY_DISTRICT}locDistrictName
        """
    )
    fun findForStreetByStreetId(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findForStreetDistinctByStreetId(streetId: UUID) =
        findForStreetByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale AND ${PX_LOCALITY_DISTRICT}ldLocalitiesId = :localityId AND ${PX_LOCALITY_DISTRICT}locDistrictName LIKE '%' || :districtName || '%' ORDER BY ${PX_LOCALITY_DISTRICT}locDistrictName")
    fun findByDistrictName(
        localityId: UUID, districtName: String, locale: String? = Locale.getDefault().language
    ): Flow<List<GeoLocalityDistrictView>>

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
    suspend fun insert(localityDistrictTls: List<GeoLocalityDistrictTlEntity>)

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