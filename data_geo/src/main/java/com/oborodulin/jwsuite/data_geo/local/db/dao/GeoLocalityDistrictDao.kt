package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.LocalityDistrictWithStreets
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoLocalityDistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale ORDER BY ${PX_LOCALITY_DISTRICT}locDistrictName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale AND ${PX_LOCALITY_DISTRICT}localityDistrictId = :localityDistrictId")
    fun findById(localityDistrictId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoLocalityDistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityDistrictId: UUID) =
        findById(localityDistrictId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ${PX_LOCALITY_DISTRICT}locDistrictLocCode = :locale AND ${PX_LOCALITY_DISTRICT}ldLocalitiesId = :localityId ORDER BY ${PX_LOCALITY_DISTRICT}locDistrictName")
    fun findByLocalityId(localityId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

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
    suspend fun insert(vararg textContent: GeoLocalityDistrictTlEntity)

    @Transaction
    suspend fun insert(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    ) {
        insert(localityDistrict)
        insert(textContent)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg districtStreet: GeoDistrictStreetEntity)

    suspend fun insert(localityDistrict: GeoLocalityDistrictEntity, street: GeoStreetEntity) =
        insert(
            GeoDistrictStreetEntity(
                dsLocalityDistrictsId = localityDistrict.localityDistrictId,
                dsStreetsId = street.streetId
            )
        )

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

    @Update
    suspend fun update(vararg districtStreet: GeoDistrictStreetEntity)

    // DELETES:
    @Delete
    suspend fun delete(localityDistrict: GeoLocalityDistrictEntity)

    @Delete
    suspend fun delete(vararg localityDistricts: GeoLocalityDistrictEntity)

    @Delete
    suspend fun delete(localityDistricts: List<GeoLocalityDistrictEntity>)

    @Query("DELETE FROM ${GeoLocalityDistrictEntity.TABLE_NAME} WHERE localityDistrictId = :localityDistrictId")
    suspend fun deleteById(localityDistrictId: UUID)

    @Delete
    suspend fun deleteStreet(vararg districtStreet: GeoDistrictStreetEntity)

    @Query("DELETE FROM ${GeoDistrictStreetEntity.TABLE_NAME} WHERE districtStreetId = :districtStreetId")
    suspend fun deleteStreetById(districtStreetId: UUID)

    @Query("DELETE FROM ${GeoDistrictStreetEntity.TABLE_NAME} WHERE dsLocalityDistrictsId = :localityDistrictId")
    suspend fun deleteStreetsByLocalityDistrictId(localityDistrictId: UUID)

    @Query("DELETE FROM ${GeoLocalityDistrictEntity.TABLE_NAME}")
    suspend fun deleteAll()
}