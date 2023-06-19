package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.entities.pojo.MicrodistrictWithStreets
import com.oborodulin.jwsuite.data.local.db.views.GeoMicrodistrictView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoMicrodistrictDao {
    // READS:
    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME}")
    fun findAll(): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE microdistrictId = :microdistrictId")
    fun findById(microdistrictId: UUID): Flow<GeoMicrodistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(microdistrictId: UUID) = findById(microdistrictId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE localitiesId = :localityId")
    fun findByLocalityId(localityId: UUID): Flow<GeoMicrodistrictView>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityId(localityId: UUID) =
        findByLocalityId(localityId).distinctUntilChanged()

    @Query("SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} WHERE localityDistrictsId = :localityDistrictId")
    fun findByLocalityDistrictId(localityDistrictId: UUID): Flow<List<GeoMicrodistrictView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityDistrictId(localityDistrictId: UUID) =
        findByLocalityDistrictId(localityDistrictId).distinctUntilChanged()

    @Query(
        """
       SELECT * FROM ${GeoMicrodistrictView.VIEW_NAME} 
        WHERE localitiesId = :localityId
            AND ifnull(localityDistrictsId, '') = ifnull(:localityDistrictId, ifnull(localityDistrictsId, '')) 
            AND microdistrictName LIKE '%' || :microdistrictName || '%' 
    """
    )
    fun findByMicrodistrictName(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictName: String
    ): Flow<List<GeoMicrodistrictView>>

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg districtStreet: GeoDistrictStreetEntity)

    suspend fun insert(microdistrict: GeoMicrodistrictEntity, street: GeoStreetEntity) =
        insert(
            GeoDistrictStreetEntity(
                localityDistrictsId = microdistrict.localityDistrictsId,
                microdistrictsId = microdistrict.microdistrictId,
                streetsId = street.streetId
            )
        )

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

    @Update
    suspend fun update(vararg districtStreet: GeoDistrictStreetEntity)

    // DELETES:
    @Delete
    suspend fun delete(microdistrict: GeoMicrodistrictEntity)

    @Delete
    suspend fun delete(vararg microdistricts: GeoMicrodistrictEntity)

    @Delete
    suspend fun delete(microdistricts: List<GeoMicrodistrictEntity>)

    @Query("DELETE FROM ${GeoMicrodistrictEntity.TABLE_NAME} WHERE microdistrictId = :microdistrictId")
    suspend fun deleteById(microdistrictId: UUID)

    @Delete
    suspend fun deleteStreet(vararg districtStreet: GeoDistrictStreetEntity)

    @Query("DELETE FROM ${GeoDistrictStreetEntity.TABLE_NAME} WHERE districtStreetId = :districtStreetId")
    suspend fun deleteStreetById(districtStreetId: UUID)

    @Query("DELETE FROM ${GeoDistrictStreetEntity.TABLE_NAME} WHERE microdistrictsId = :microdistrictId")
    suspend fun deleteStreetsByMicrodistrictId(microdistrictId: UUID)

    @Query("DELETE FROM ${GeoMicrodistrictEntity.TABLE_NAME}")
    suspend fun deleteAll()
}