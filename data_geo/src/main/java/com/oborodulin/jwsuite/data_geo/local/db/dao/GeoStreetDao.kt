package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_geo.util.Constants
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface GeoStreetDao {
    // READS:
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
        SELECT sw.* FROM ${GeoStreetView.VIEW_NAME} sw JOIN ${GeoDistrictStreetEntity.TABLE_NAME} sd 
            ON sd.dsStreetsId = sw.streetId AND sd.dsLocalityDistrictsId = :localityDistrictId 
                AND sw.isStreetPrivateSector = ifnull(:isPrivateSector, sw.isStreetPrivateSector) AND sw.streetLocCode = :locale
        ORDER BY sw.streetName                
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
        SELECT sw.* FROM ${GeoStreetView.VIEW_NAME} sw JOIN ${GeoDistrictStreetEntity.TABLE_NAME} sd 
            ON sd.dsStreetsId = sw.streetId AND sd.dsMicrodistrictsId = :microdistrictId 
                AND sw.isStreetPrivateSector = ifnull(:isPrivateSector, sw.isStreetPrivateSector) AND sw.streetLocCode = :locale
        ORDER BY sw.streetName                
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
    @Query(
        """
        SELECT * FROM ${GeoStreetView.VIEW_NAME} 
        WHERE streetLocCode = :locale
            AND ifnull(microdistrictId, '') = ifnull(:microdistrictId, '') 
            AND ifnull(${PX_LOCALITY_DISTRICT}localityDistrictId , '') = ifnull(:localityDistrictId, '')
            AND ${PX_LOCALITY}localityId = :localityId
            AND streetId NOT IN (:excludes)
        ORDER BY streetName
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
        SELECT sw.* FROM ${GeoStreetView.VIEW_NAME} sw JOIN ${GeoDistrictStreetEntity.TABLE_NAME} sd ON sd.dsStreetsId = sw.streetId 
        WHERE sw.sLocalitiesId = :localityId
            AND sw.isStreetPrivateSector = ifnull(:isPrivateSector, sw.isStreetPrivateSector) 
            AND ifnull(sd.dsLocalityDistrictsId, '') = ifnull(:localityDistrictId, ifnull(sd.dsLocalityDistrictsId, '')) 
            AND ifnull(sd.dsMicrodistrictsId, '') = ifnull(:microdistrictId, ifnull(sd.dsMicrodistrictsId, '')) 
            AND sw.streetName LIKE '%' || :streetName || '%'
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
    suspend fun insert(vararg textContent: GeoStreetTlEntity)

    @Transaction
    suspend fun insert(street: GeoStreetEntity, textContent: GeoStreetTlEntity) {
        insert(street)
        insert(textContent)
    }

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
}