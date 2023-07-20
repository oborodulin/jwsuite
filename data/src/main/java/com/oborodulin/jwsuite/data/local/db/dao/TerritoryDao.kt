package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.entities.pojo.TerritoryWithMembers
import com.oborodulin.jwsuite.data.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesIdleView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryDistrictView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryPrivateSectorView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.OffsetDateTime
import java.util.*

@Dao
interface TerritoryDao {
    // READS:
    @Query("SELECT * FROM ${TerritoryView.VIEW_NAME} WHERE ${PX_LOCALITY}localityLocCode = :locale ORDER BY tCongregationsId, territoryNum")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<TerritoryView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TerritoryView.VIEW_NAME} WHERE territoryId = :territoryId")
    fun findById(territoryId: UUID): Flow<TerritoryView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(territoryId: UUID) = findById(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT t.* FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.ctTerritoriesId = t.territoryId 
    WHERE ct.ctCongregationsId = :congregationId AND t.${PX_LOCALITY}localityLocCode = :locale 
        """
    )
    fun findByCongregationId(congregationId: UUID, locale: String? = Locale.getDefault().language):
            Flow<List<TerritoryView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationId(congregationId: UUID) =
        findByCongregationId(congregationId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT t.* FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            ON ct.ctTerritoriesId = t.territoryId  AND t.${PX_LOCALITY}localityLocCode = :locale
        JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = ct.ctCongregationsId    
        """
    )
    fun findByFavoriteCongregation(locale: String? = Locale.getDefault().language): Flow<List<TerritoryView>>

    @Query("SELECT * FROM ${TerritoryView.VIEW_NAME} WHERE tCongregationsId = :congregationId AND tTerritoryCategoriesId = :territoryCategoryId AND territoryNum = :territoryNum LIMIT 1")
    fun findByTerritoryNum(congregationId: UUID, territoryCategoryId: UUID, territoryNum: Int):
            Flow<List<TerritoryView>>

    @Transaction
    @Query("SELECT * FROM ${TerritoryEntity.TABLE_NAME} WHERE tCongregationsId = :congregationId ORDER BY territoryNum")
    fun findTerritoryWithMembers(congregationId: UUID): Flow<List<TerritoryWithMembers>>

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoriesHandOutView.VIEW_NAME} 
    WHERE ctCongregationsId = :congregationId AND isPrivateSector = ifnull(:isPrivateSector, isPrivateSector) AND ${PX_LOCALITY}localityLocCode = :locale
    """
    )
    fun findHandOutTerritories(
        congregationId: UUID, isPrivateSector: Boolean? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoriesHandOutView>>

    @Query(
        """
    SELECT * FROM ${TerritoriesAtWorkView.VIEW_NAME} 
    WHERE ctCongregationsId = :congregationId AND isPrivateSector = ifnull(:isPrivateSector, isPrivateSector) AND ${PX_LOCALITY}localityLocCode = :locale
    """
    )
    fun findAtWorkTerritories(
        congregationId: UUID, isPrivateSector: Boolean? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoriesAtWorkView>>

    @Query(
        """
    SELECT * FROM ${TerritoriesIdleView.VIEW_NAME} 
    WHERE ctCongregationsId = :congregationId AND isPrivateSector = ifnull(:isPrivateSector, isPrivateSector) AND ${PX_LOCALITY}localityLocCode = :locale
    """
    )
    fun findIdleTerritories(
        congregationId: UUID, isPrivateSector: Boolean? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoriesIdleView>>

    //-----------------------------
    @Query(
        """
    SELECT td.* 
    FROM ${TerritoryDistrictView.VIEW_NAME} td LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = td.congregationId
    WHERE td.isPrivateSector = :isPrivateSector AND td.congregationId = ifnull(:congregationId, fcv.congregationId) 
        """
    )
    fun findTerritoryDistrictsByPrivateSectorMarkAndCongregationId(
        isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryDistrictView>>

    @Query(
        """
    SELECT t.* FROM ${TerritoryView.VIEW_NAME} t JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tpsv ON tpsv.territoryId = t.territoryId
        LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = t.tCongregationsId
    WHERE t.tCongregationsId = ifnull(:congregationId, fcv.congregationId) AND t.tLocalitiesId = :localityId 
        AND tpsv.isPrivateSector = :isPrivateSector AND t.isActive = $DB_TRUE AND t.tLocalityDistrictsId IS NULL AND t.tMicrodistrictsId IS NULL 
    """
    )
    fun findByLocalityIdAndPrivateSectorMarkAndCongregationId(
        localityId: UUID, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryView>>

    @Query(
        """
    SELECT t.* FROM ${TerritoryView.VIEW_NAME} t JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tpsv ON tpsv.territoryId = t.territoryId
        LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = t.tCongregationsId
    WHERE t.tCongregationsId = ifnull(:congregationId, fcv.congregationId) AND tpsv.isPrivateSector = :isPrivateSector  
        AND t.isActive = $DB_TRUE AND t.tLocalityDistrictsId = :localityDistrictId AND t.tMicrodistrictsId IS NULL 
    """
    )
    fun findByLocalityDistrictIdAndPrivateSectorMarkAndCongregationId(
        localityDistrictId: UUID, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryView>>

    @Query(
        """
    SELECT t.* FROM ${TerritoryView.VIEW_NAME} t JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tpsv ON tpsv.territoryId = t.territoryId
        LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = t.tCongregationsId
    WHERE t.tCongregationsId = ifnull(:congregationId, fcv.congregationId) AND tpsv.isPrivateSector = :isPrivateSector  
        AND t.isActive = $DB_TRUE AND t.tMicrodistrictsId = :microdistrictId 
    """
    )
    fun findByMicrodistrictIdAndPrivateSectorMarkAndCongregationId(
        microdistrictId: UUID, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territory: TerritoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg territories: TerritoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territories: List<TerritoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg territoryMember: TerritoryMemberCrossRefEntity)

    suspend fun insert(
        territory: TerritoryEntity, member: MemberEntity,
        receivingDate: OffsetDateTime = OffsetDateTime.now()
    ) = insert(
        TerritoryMemberCrossRefEntity(
            tmcTerritoriesId = territory.territoryId,
            tmcMembersId = member.memberId,
            receivingDate = receivingDate
        )
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg territoryStreet: TerritoryStreetEntity)

    suspend fun insert(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEven: Boolean? = null, isPrivateSector: Boolean? = null, estimatedHouses: Int? = null
    ) = insert(
        TerritoryStreetEntity(
            tsTerritoriesId = territory.territoryId,
            tsStreetsId = street.streetId,
            isEvenSide = isEven,
            isTerStreetPrivateSector = isPrivateSector,
            estTerStreetHouses = estimatedHouses
        )
    )

    // UPDATES:
    @Update
    suspend fun update(territory: TerritoryEntity)

    @Update
    suspend fun update(vararg territories: TerritoryEntity)

    @Update
    suspend fun update(vararg territoryMembers: TerritoryMemberCrossRefEntity)

    @Update
    suspend fun update(vararg territoryStreet: TerritoryStreetEntity)

    // DELETES:
    @Delete
    suspend fun delete(territory: TerritoryEntity)

    @Delete
    suspend fun delete(vararg territories: TerritoryEntity)

    @Delete
    suspend fun delete(territories: List<TerritoryEntity>)

    @Query("DELETE FROM ${TerritoryEntity.TABLE_NAME} WHERE territoryId = :territoryId")
    suspend fun deleteById(territoryId: UUID)

    @Delete
    suspend fun deleteMember(vararg territoryMember: TerritoryMemberCrossRefEntity)

    @Query("DELETE FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} WHERE territoryMemberId = :territoryMemberId")
    suspend fun deleteMemberById(territoryMemberId: UUID)

    @Query("DELETE FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} WHERE tmcTerritoriesId = :territoryId")
    suspend fun deleteMembersByTerritoryId(territoryId: UUID)

    @Delete
    suspend fun deleteStreet(vararg territoryStreet: TerritoryStreetEntity)

    @Query("DELETE FROM ${TerritoryStreetEntity.TABLE_NAME} WHERE territoryStreetId = :territoryStreetId")
    suspend fun deleteStreetById(territoryStreetId: UUID)

    @Query("DELETE FROM ${TerritoryStreetEntity.TABLE_NAME} WHERE tsTerritoriesId = :territoryId")
    suspend fun deleteStreetsByTerritoryId(territoryId: UUID)

    @Query("DELETE FROM ${TerritoryEntity.TABLE_NAME}")
    suspend fun deleteAll()
}