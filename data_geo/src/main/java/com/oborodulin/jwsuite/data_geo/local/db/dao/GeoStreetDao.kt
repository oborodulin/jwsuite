package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoStreetDao {
    // READS:
    @Query("SELECT * FROM ${GeoStreetEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoStreetEntity>>

    @Query("SELECT * FROM ${GeoStreetTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoStreetTlEntity>>

    @Query("SELECT * FROM ${GeoStreetView.VIEW_NAME} WHERE streetLocCode = :locale ORDER BY streetName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoStreetView.VIEW_NAME} WHERE streetId = :streetId AND streetLocCode = :locale")
    fun findById(streetId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoStreetView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(streetId: UUID) = findById(streetId).distinctUntilChanged()

    //-----------------------------
    @Query(
        "SELECT * FROM ${GeoStreetView.VIEW_NAME} WHERE sLocalitiesId = :localityId AND isStreetPrivateSector = ifnull(:isPrivateSector, isStreetPrivateSector) AND streetLocCode = :locale ORDER BY streetName"
    )
    fun findByLocalityIdAndPrivateSectorMark(
        localityId: UUID, isPrivateSector: Boolean? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityIdAndPrivateSectorMark(
        localityId: UUID, isPrivateSector: Boolean? = null
    ) = findByLocalityIdAndPrivateSectorMark(localityId, isPrivateSector).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT sv.* FROM ${GeoStreetView.VIEW_NAME} sv JOIN (SELECT dsStreetsId, dsLocalityDistrictsId FROM ${GeoStreetDistrictEntity.TABLE_NAME} GROUP BY dsLocalityDistrictsId, dsStreetsId) sd 
            ON sd.dsStreetsId = sv.streetId AND sd.dsLocalityDistrictsId = :localityDistrictId 
                AND sv.isStreetPrivateSector = ifnull(:isPrivateSector, sv.isStreetPrivateSector) AND sv.streetLocCode = :locale
        ORDER BY sv.streetName                
        """
    )
    fun findByLocalityDistrictIdAndPrivateSectorMark(
        localityDistrictId: UUID, isPrivateSector: Boolean? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityDistrictIdAndPrivateSectorMark(
        localityDistrictId: UUID, isPrivateSector: Boolean? = null
    ) = findByLocalityDistrictIdAndPrivateSectorMark(
        localityDistrictId, isPrivateSector
    ).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT sv.* FROM ${GeoStreetView.VIEW_NAME} sv JOIN ${GeoStreetDistrictEntity.TABLE_NAME} ds 
            ON ds.dsStreetsId = sv.streetId AND ds.dsMicrodistrictsId = :microdistrictId 
                AND sv.isStreetPrivateSector = ifnull(:isPrivateSector, sv.isStreetPrivateSector) AND sv.streetLocCode = :locale
        ORDER BY sv.streetName                
        """
    )
    fun findByMicrodistrictIdAndPrivateSectorMark(
        microdistrictId: UUID, isPrivateSector: Boolean? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByMicrodistrictIdAndPrivateSectorMark(
        microdistrictId: UUID, isPrivateSector: Boolean? = null
    ) = findByMicrodistrictIdAndPrivateSectorMark(microdistrictId, isPrivateSector)
        .distinctUntilChanged()

    //-----------------------------
    //LEFT JOIN ${GeoLocalityDistrictView.VIEW_NAME} ldv ON ldv.${PX_LOCALITY_DISTRICT}localityDistrictId = ds.dsLocalityDistrictsId AND ldv.${PX_LOCALITY_DISTRICT}locDistrictLocCode = sv.streetLocCode
    //LEFT JOIN ${GeoMicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = ds.dsMicrodistrictsId AND mdv.microdistrictLocCode = sv.streetLocCode
    @Query(
        """
        SELECT DISTINCT sv.* FROM ${GeoStreetView.VIEW_NAME} sv LEFT JOIN ${GeoStreetDistrictEntity.TABLE_NAME} ds ON ds.dsStreetsId = sv.streetId
        WHERE sv.streetLocCode = :locale
            AND ifnull(ds.dsMicrodistrictsId, '') = ifnull(:microdistrictId, ifnull(ds.dsMicrodistrictsId, '')) 
            AND ifnull(ds.dsLocalityDistrictsId , '') = ifnull(:localityDistrictId, ifnull(ds.dsLocalityDistrictsId , ''))
            AND sv.${PX_LOCALITY}localityId = :localityId
            AND sv.isStreetPrivateSector = $DB_TRUE
            AND sv.streetId NOT IN (:excludes)
        ORDER BY sv.streetName
        """
    )
    fun findByLocalityIdAndLocalityDistrictIdAndMicrodistrictIdWithExcludes(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        excludes: List<UUID> = emptyList(), locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByLocalityIdAndLocalityDistrictIdAndMicrodistrictIdWithExcludes(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        excludes: List<UUID> = emptyList()
    ) = findByLocalityIdAndLocalityDistrictIdAndMicrodistrictIdWithExcludes(
        localityId, localityDistrictId, microdistrictId, excludes
    ).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT sv.* FROM ${GeoStreetView.VIEW_NAME} sv JOIN ${GeoStreetDistrictEntity.TABLE_NAME} ds ON ds.dsStreetsId = sv.streetId 
        WHERE sv.sLocalitiesId = :localityId
            AND sv.isStreetPrivateSector = ifnull(:isPrivateSector, sv.isStreetPrivateSector) 
            AND ifnull(ds.dsLocalityDistrictsId, '') = ifnull(:localityDistrictId, ifnull(ds.dsLocalityDistrictsId, '')) 
            AND ifnull(ds.dsMicrodistrictsId, '') = ifnull(:microdistrictId, ifnull(ds.dsMicrodistrictsId, '')) 
            AND sv.streetName LIKE '%' || :streetName || '%'
    """
    )
    fun findByStreetName(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        isPrivateSector: Boolean? = null, streetName: String
    ): Flow<List<GeoStreetView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(street: GeoStreetEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg streets: GeoStreetEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(streets: List<GeoStreetEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTls(streetTls: List<GeoStreetTlEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg textContent: GeoStreetTlEntity)

    @Transaction
    suspend fun insert(street: GeoStreetEntity, textContent: GeoStreetTlEntity) {
        insert(street)
        insert(textContent)
    }

    //-----------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg streetDistrict: GeoStreetDistrictEntity)

    suspend fun insert(street: GeoStreetEntity, localityDistrict: GeoLocalityDistrictEntity) =
        insert(
            GeoStreetDistrictEntity(
                dsLocalityDistrictsId = localityDistrict.localityDistrictId,
                dsStreetsId = street.streetId
            )
        )

    suspend fun insertStreetLocalityDistrict(streetId: UUID, localityDistrictId: UUID) =
        insert(
            GeoStreetDistrictEntity(
                dsStreetsId = streetId,
                dsLocalityDistrictsId = localityDistrictId
            )
        )

    //-----------------------------
    suspend fun insert(street: GeoStreetEntity, microdistrict: GeoMicrodistrictEntity) =
        insert(
            GeoStreetDistrictEntity(
                dsStreetsId = street.streetId,
                dsLocalityDistrictsId = microdistrict.mLocalityDistrictsId,
                dsMicrodistrictsId = microdistrict.microdistrictId
            )
        )

    suspend fun insertStreetMicrodistrict(
        streetId: UUID, localityDistrictId: UUID, microdistrictId: UUID
    ) = insert(
        GeoStreetDistrictEntity(
            dsStreetsId = streetId,
            dsLocalityDistrictsId = localityDistrictId,
            dsMicrodistrictsId = microdistrictId
        )
    )

    // UPDATES:
    @Update
    suspend fun update(street: GeoStreetEntity)

    @Update
    suspend fun update(vararg streets: GeoStreetEntity)

    @Update
    suspend fun update(vararg textContent: GeoStreetTlEntity)

    @Transaction
    suspend fun update(street: GeoStreetEntity, textContent: GeoStreetTlEntity) {
        update(street)
        update(textContent)
    }

    @Update
    suspend fun update(vararg streetDistrict: GeoStreetDistrictEntity)

    // DELETES:
    @Delete
    suspend fun delete(street: GeoStreetEntity)

    @Delete
    suspend fun delete(vararg streets: GeoStreetEntity)

    @Delete
    suspend fun delete(streets: List<GeoStreetEntity>)

    @Query("DELETE FROM ${GeoStreetEntity.TABLE_NAME} WHERE streetId = :streetId")
    suspend fun deleteById(streetId: UUID)

    @Query("DELETE FROM ${GeoStreetEntity.TABLE_NAME}")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteStreetDistrict(vararg streetDistrict: GeoStreetDistrictEntity)

    @Query("DELETE FROM ${GeoStreetDistrictEntity.TABLE_NAME} WHERE streetDistrictId = :streetDistrictId")
    suspend fun deleteStreetDistrictById(streetDistrictId: UUID)

    @Query("DELETE FROM ${GeoStreetDistrictEntity.TABLE_NAME} WHERE dsStreetsId = :streetId AND dsLocalityDistrictsId = :localityDistrictId")
    suspend fun deleteStreetDistrictByLocalityDistrictId(streetId: UUID, localityDistrictId: UUID)

    @Query("DELETE FROM ${GeoStreetDistrictEntity.TABLE_NAME} WHERE dsStreetsId = :streetId AND dsMicrodistrictsId = :microdistrictId")
    suspend fun deleteStreetDistrictByMicrodistrictId(streetId: UUID, microdistrictId: UUID)
}