package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.entities.pojo.LocalityDistrictWithStreets
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityDistrictView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoLocalityDistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME}")
    fun findAll(): Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE localityDistrictId = :localityDistrictId")
    fun findById(localityDistrictId: UUID): Flow<GeoLocalityDistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(localityDistrictId: UUID) =
        findById(localityDistrictId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ldLocalitiesId = :localityId")
    fun findByLocalityId(localityId: UUID): Flow<List<GeoLocalityDistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoLocalityDistrictView.VIEW_NAME} WHERE ldLocalitiesId = :localityId AND locDistrictName LIKE '%' || :districtName || '%'")
    fun findByDistrictName(localityId: UUID, districtName: String):
            Flow<List<GeoLocalityDistrictView>>

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