package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.entities.pojo.TerritoryWithMembers
import com.oborodulin.jwsuite.data.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesIdleView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import com.oborodulin.jwsuite.data.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data.util.Constants.TDT_ALL_VAL
import com.oborodulin.jwsuite.data.util.Constants.TDT_LOCALITY_DISTRICT_VAL
import com.oborodulin.jwsuite.data.util.Constants.TDT_LOCALITY_VAL
import com.oborodulin.jwsuite.data.util.Constants.TDT_MICRO_DISTRICT_VAL
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.OffsetDateTime
import java.util.*

@Dao
interface TerritoryDao {
    // READS:
    @Query("SELECT * FROM ${TerritoryView.VIEW_NAME} WHERE ${PX_LOCALITY}localityLocCode = :locale ORDER BY tCongregationsId, territoryCategoryMark, territoryNum")
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
    ORDER BY t.territoryCategoryMark, t.territoryNum
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
    ORDER BY t.territoryCategoryMark, t.territoryNum            
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
    SELECT td.* 
    FROM ${TerritoryLocationView.VIEW_NAME} td LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = td.congregationId
    WHERE (td.locationId IS NULL OR -- for ALL 
            ifnull(td.isPrivateSector, $DB_FALSE) = (CASE WHEN :isPrivateSector = $DB_TRUE THEN $DB_TRUE ELSE ifnull(td.isPrivateSector, $DB_FALSE) END))
        AND td.congregationId = ifnull(:congregationId, fcv.congregationId)
    ORDER BY td.orderPos, td.locationShortName, ifnull(td.isPrivateSector, $DB_FALSE)
        """
    )
    fun findTerritoryLocationsByPrivateSectorMarkAndCongregationId(
        isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryLocationView>>

    //-----------------------------
    @Query(
        """
    SELECT t.* FROM ${TerritoriesHandOutView.VIEW_NAME} t LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = t.tCongregationsId
    WHERE t.ctCongregationsId = ifnull(:congregationId, fcv.congregationId) 
        AND t.isPrivateSector = (CASE WHEN ifnull(:isPrivateSector, $DB_FALSE) = $DB_TRUE THEN $DB_TRUE ELSE ifnull(t.isPrivateSector, $DB_FALSE) END) 
        AND t.${PX_LOCALITY}localityLocCode = :locale
        AND ((:territoryLocationType = $TDT_ALL_VAL) OR
            (:territoryLocationType = $TDT_LOCALITY_VAL AND t.tLocalitiesId = :locationId AND t.tLocalityDistrictsId IS NULL AND t.tMicrodistrictsId IS NULL) OR
            (:territoryLocationType = $TDT_LOCALITY_DISTRICT_VAL AND t.tLocalityDistrictsId = :locationId AND t.tMicrodistrictsId IS NULL) OR
            (:territoryLocationType = $TDT_MICRO_DISTRICT_VAL AND t.tMicrodistrictsId = :locationId))
    ORDER BY t.territoryCategoryMark, t.territoryNum            
    """
    )
    fun findHandOutTerritories(
        congregationId: UUID? = null, isPrivateSector: Boolean? = null,
        territoryLocationType: TerritoryLocationType, locationId: UUID? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoriesHandOutView>>

    @Query(
        """
    SELECT t.* FROM ${TerritoriesAtWorkView.VIEW_NAME} t LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = t.tCongregationsId
    WHERE t.ctCongregationsId = ifnull(:congregationId, fcv.congregationId) 
        AND t.isPrivateSector = (CASE WHEN ifnull(:isPrivateSector, $DB_FALSE) = $DB_TRUE THEN $DB_TRUE ELSE ifnull(t.isPrivateSector, $DB_FALSE) END) 
        AND t.${PX_LOCALITY}localityLocCode = :locale
        AND ((:territoryLocationType = $TDT_ALL_VAL) OR
            (:territoryLocationType = $TDT_LOCALITY_VAL AND t.tLocalitiesId = :locationId AND t.tLocalityDistrictsId IS NULL AND t.tMicrodistrictsId IS NULL) OR
            (:territoryLocationType = $TDT_LOCALITY_DISTRICT_VAL AND t.tLocalityDistrictsId = :locationId AND t.tMicrodistrictsId IS NULL) OR
            (:territoryLocationType = $TDT_MICRO_DISTRICT_VAL AND t.tMicrodistrictsId = :locationId))
    ORDER BY t.territoryCategoryMark, t.territoryNum            
    """
    )
    fun findAtWorkTerritories(
        congregationId: UUID? = null, isPrivateSector: Boolean? = null,
        territoryLocationType: TerritoryLocationType, locationId: UUID? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoriesAtWorkView>>

    @Query(
        """
    SELECT t.* FROM ${TerritoriesIdleView.VIEW_NAME} t LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = t.tCongregationsId
    WHERE t.ctCongregationsId = ifnull(:congregationId, fcv.congregationId)
        AND t.isPrivateSector = (CASE WHEN ifnull(:isPrivateSector, $DB_FALSE) = $DB_TRUE THEN $DB_TRUE ELSE ifnull(t.isPrivateSector, $DB_FALSE) END) 
        AND t.${PX_LOCALITY}localityLocCode = :locale
        AND ((:territoryLocationType = $TDT_ALL_VAL) OR
            (:territoryLocationType = $TDT_LOCALITY_VAL AND t.tLocalitiesId = :locationId AND t.tLocalityDistrictsId IS NULL AND t.tMicrodistrictsId IS NULL) OR
            (:territoryLocationType = $TDT_LOCALITY_DISTRICT_VAL AND t.tLocalityDistrictsId = :locationId AND t.tMicrodistrictsId IS NULL) OR
            (:territoryLocationType = $TDT_MICRO_DISTRICT_VAL AND t.tMicrodistrictsId = :locationId))
    ORDER BY t.territoryCategoryMark, t.territoryNum            
    """
    )
    fun findIdleTerritories(
        congregationId: UUID? = null, isPrivateSector: Boolean? = null,
        territoryLocationType: TerritoryLocationType, locationId: UUID? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoriesIdleView>>

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

    suspend fun insert(
        territory: TerritoryEntity, memberId: UUID,
        receivingDate: OffsetDateTime = OffsetDateTime.now()
    ) = insert(
        TerritoryMemberCrossRefEntity(
            tmcTerritoriesId = territory.territoryId,
            tmcMembersId = memberId,
            receivingDate = receivingDate
        )
    )

    suspend fun insert(
        territoryId: UUID, memberId: UUID,
        receivingDate: OffsetDateTime = OffsetDateTime.now()
    ) = insert(
        TerritoryMemberCrossRefEntity(
            tmcTerritoriesId = territoryId,
            tmcMembersId = memberId,
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


    // API:
    @Query("SELECT ifnull(MAX(territoryNum), 0) FROM ${TerritoryEntity.TABLE_NAME} WHERE tCongregationsId = :congregationId AND tTerritoryCategoriesId = :territoryCategoryId")
    fun maxTerritoryNum(congregationId: UUID, territoryCategoryId: UUID): Int

    @Query("SELECT ifnull(MAX(territoryNum), 0) + 1 FROM ${TerritoryEntity.TABLE_NAME} WHERE tCongregationsId = :congregationId AND tTerritoryCategoriesId = :territoryCategoryId")
    fun nextTerritoryNum(congregationId: UUID, territoryCategoryId: UUID): Int

    @Query(
        """
        UPDATE ${TerritoryEntity.TABLE_NAME} SET territoryNum = territoryNum + 1 
        WHERE territoryNum >= :territoryNum AND tCongregationsId = :congregationId AND tTerritoryCategoriesId = :territoryCategoryId 
        """
    )
    suspend fun updateTerritoryNum(
        congregationId: UUID, territoryCategoryId: UUID, territoryNum: Int
    )

    suspend fun changeWithTerritoryNum(territory: TerritoryEntity) {
        var territories = emptyList<TerritoryView>()
        findByTerritoryNum(
            territory.tCongregationsId, territory.tTerritoryCategoriesId, territory.territoryNum
        ).collect { territories = it }
        if (territories.isNotEmpty()) {
            updateTerritoryNum(
                territory.tCongregationsId, territory.tTerritoryCategoriesId, territory.territoryNum
            )
        }
    }

    @Transaction
    suspend fun insertWithTerritoryNum(territory: TerritoryEntity) {
        changeWithTerritoryNum(territory)
        insert(territory)
    }

    @Transaction
    suspend fun updateWithTerritoryNum(territory: TerritoryEntity) {
        changeWithTerritoryNum(territory)
        update(territory)
    }

    /*
        // API:
        @Query(
            "UPDATE ${PayerServiceCrossRefEntity.TABLE_NAME} SET isMeterOwner = ${Constants.DB_TRUE} " +
                    "WHERE payerServiceId = :payerServiceId AND isMeterOwner = ${Constants.DB_FALSE}"
        )
        suspend fun setPayerServiceMeterOwnerById(payerServiceId: UUID)

        @Query(
            """
    UPDATE ${PayerServiceCrossRefEntity.TABLE_NAME} SET isMeterOwner = ${Constants.DB_FALSE}
    WHERE payerServiceId <> :payerServiceId
        AND payersId = (SELECT ps.payersId FROM ${PayerServiceCrossRefEntity.TABLE_NAME} ps WHERE ps.payerServiceId = :payerServiceId)
        AND servicesId IN (SELECT serviceId FROM ${ServiceEntity.TABLE_NAME}
                            WHERE serviceMeterType = (
                                SELECT s.serviceMeterType FROM ${ServiceEntity.TABLE_NAME} s
                                    JOIN ${PayerServiceCrossRefEntity.TABLE_NAME} ps ON s.serviceId = ps.servicesId
                                        AND ps.payerServiceId = :payerServiceId)
                            )
        """
        )
        suspend fun clearPayerServiceMeterOwnerById(payerServiceId: UUID)

        @Transaction
        suspend fun payerServiceMeterOwnerById(payerServiceId: UUID) {
            clearPayerServiceMeterOwnerById(payerServiceId)
            setPayerServiceMeterOwnerById(payerServiceId)
        }
     */
}