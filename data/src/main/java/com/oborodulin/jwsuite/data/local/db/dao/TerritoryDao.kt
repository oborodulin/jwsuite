package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.entities.pojo.TerritoryWithMembers
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface TerritoryDao {
    // READS:
    @Query("SELECT * FROM ${TerritoryView.VIEW_NAME} ORDER BY congregationsId, territoryNum")
    fun findAll(): Flow<List<TerritoryView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${TerritoryView.VIEW_NAME} WHERE territoryId = :territoryId")
    fun findById(territoryId: UUID): Flow<TerritoryView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(territoryId: UUID) = findById(territoryId).distinctUntilChanged()

    @Query(
        """
    SELECT t.* FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = t.territoryId 
    WHERE ct.congregationsId = :congregationId
        """
    )
    fun findByCongregationId(congregationId: UUID): Flow<List<TerritoryView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByCongregationId(congregationId: UUID) =
        findByCongregationId(congregationId).distinctUntilChanged()

    @Query("SELECT * FROM ${TerritoryEntity.TABLE_NAME} WHERE congregationsId = :congregationId AND territoryCategoriesId = :territoryCategoryId AND territoryNum = :territoryNum LIMIT 1")
    fun findByTerritoryNum(
        congregationId: UUID, territoryCategoryId: UUID, territoryNum: Int
    ): Flow<TerritoryEntity>

    @Transaction
    @Query("SELECT * FROM ${TerritoryEntity.TABLE_NAME} WHERE congregationsId = :congregationId ORDER BY territoryNum")
    fun findTerritoryWithMembers(congregationId: UUID): Flow<List<TerritoryWithMembers>>

    @Query(
        """
    SELECT t.* FROM (
        SELECT ct.territoriesId  
        FROM  ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON ts.territoriesId = ct.territoriesId AND ct.congregationsId = :congregationId
            JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = ts.streetsId AND s.localitiesId = :localityId
        UNION ALL
        SELECT ct.territoriesId  
        FROM  ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            JOIN ${HouseEntity.TABLE_NAME} h ON h.territoriesId = ct.territoriesId AND ct.congregationsId = :congregationId
            JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.streetsId AND s.localitiesId = :localityId
        GROUP BY territoriesId
         ) lt JOIN ${TerritoryView.VIEW_NAME} t ON t.territoryId = lt.territoriesId 
    """
    )
    fun findByCongregationIdAndLocalityId(congregationId: UUID, localityId: UUID):
            Flow<List<TerritoryView>>

    @Query(
        """
    SELECT t.* FROM (
        SELECT ct.territoriesId  
        FROM  ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON ts.territoriesId = ct.territoriesId AND ct.congregationsId = :congregationId
            JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = ts.streetsId
            JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId AND ds.microdistrictsId = :microdistrictId
        UNION ALL
        SELECT ct.territoriesId  
        FROM  ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            JOIN ${HouseEntity.TABLE_NAME} h ON h.territoriesId = ct.territoriesId AND ct.congregationsId = :congregationId
            JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.streetsId
            JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId AND ifnull(h.microdistrictsId, ds.microdistrictsId) = :microdistrictId
        GROUP BY territoriesId
         ) mt JOIN ${TerritoryView.VIEW_NAME} t ON t.territoryId = mt.territoriesId 
    """
    )
    fun findByCongregationIdAndMicrodistrictId(congregationId: UUID, microdistrictId: UUID):
            Flow<List<TerritoryView>>

    @Query(
        """
    SELECT t.* FROM (
        SELECT ct.territoriesId  
        FROM  ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON ts.territoriesId = ct.territoriesId AND ct.congregationsId = :congregationId
            JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = ts.streetsId
            JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId AND ds.localityDistrictsId = :localityDistrictId AND ds.microdistrictsId IS NULL
        UNION ALL
        SELECT ct.territoriesId  
        FROM  ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
            JOIN ${HouseEntity.TABLE_NAME} h ON h.territoriesId = ct.territoriesId AND ct.congregationsId = :congregationId
            JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.streetsId
            JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId AND ifnull(h.localityDistrictsId, ds.localityDistrictsId) = :localityDistrictId 
                                                            AND ds.microdistrictsId IS NULL
        GROUP BY territoriesId
         ) mt JOIN ${TerritoryView.VIEW_NAME} t ON t.territoryId = mt.territoriesId 
    """
    )
    fun findByCongregationIdAndLocalityDistrictId(congregationId: UUID, localityDistrictId: UUID):
            Flow<List<TerritoryView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territory: TerritoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg territories: TerritoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territories: List<TerritoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg territoryMember: TerritoryMemberCrossRefEntity)

    suspend fun insert(territory: TerritoryEntity, member: MemberEntity) =
        insert(
            TerritoryMemberCrossRefEntity(
                territoriesId = territory.territoryId,
                membersId = member.memberId
            )
        )

    // UPDATES:
    @Update
    suspend fun update(territory: TerritoryEntity)

    @Update
    suspend fun update(vararg territories: TerritoryEntity)

    @Update
    suspend fun update(vararg territoryMembers: TerritoryMemberCrossRefEntity)

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

    @Query("DELETE FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} WHERE territoriesId = :territoryId")
    suspend fun deleteMembersByTerritoryId(territoryId: UUID)

    @Query("DELETE FROM ${TerritoryEntity.TABLE_NAME}")
    suspend fun deleteAll()
}