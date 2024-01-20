package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.MicrodistrictWithStreets
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoMicrodistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoMicrodistrictEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoMicrodistrictEntity>>

    @Query("SELECT * FROM ${GeoMicrodistrictTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoMicrodistrictTlEntity>>

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE microdistrictLocCode = :locale ORDER BY mLocalitiesId, mLocalityDistrictsId, microdistrictName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE microdistrictId = :microdistrictId AND microdistrictLocCode = :locale")
    fun findById(microdistrictId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoMicrodistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(microdistrictId: UUID) = findById(microdistrictId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE mLocalitiesId = :localityId AND microdistrictLocCode = :locale ORDER BY microdistrictName")
    fun findByLocalityId(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE mLocalityDistrictsId = :localityDistrictId AND microdistrictLocCode = :locale ORDER BY microdistrictName")
    fun findByLocalityDistrictId(
        localityDistrictId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityDistrictId(localityDistrictId: UUID) =
        findByLocalityDistrictId(localityDistrictId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT mv.* FROM ${GeoMicrodistrictView.VIEW_NAME} mv JOIN ${GeoStreetDistrictEntity.TABLE_NAME} sd 
        ON sd.dsStreetsId = :streetId AND mv.microdistrictId = sd.dsMicrodistrictsId AND mv.microdistrictLocCode = :locale 
    ORDER BY mv.microdistrictName
        """
    )
    fun findByStreetId(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByStreetId(streetId: UUID) = findByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT mv.* FROM ${GeoMicrodistrictView.VIEW_NAME} mv JOIN ${StreetView.VIEW_NAME} sv 
        ON sv.streetId = :streetId AND mv.mLocalitiesId = sv.sLocalitiesId AND mv.microdistrictLocCode = sv.streetLocCode AND sv.streetLocCode = :locale
    WHERE NOT EXISTS (SELECT streetDistrictId FROM ${GeoStreetDistrictEntity.TABLE_NAME} WHERE dsStreetsId = :streetId AND dsMicrodistrictsId = mv.microdistrictId) 
    ORDER BY mv.microdistrictName
        """
    )
    fun findForStreetByStreetId(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findForStreetDistinctByStreetId(streetId: UUID) =
        findForStreetByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
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
    suspend fun insert(microdistrictTls: List<GeoMicrodistrictTlEntity>)

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