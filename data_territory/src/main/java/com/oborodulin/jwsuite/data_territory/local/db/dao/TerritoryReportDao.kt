package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.CongregationTerritoryView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportStreetView
import com.oborodulin.jwsuite.domain.util.Constants
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface TerritoryReportDao {
    // EXTRACTS:
    @Query("""
    SELECT tmr.* FROM ${TerritoryMemberReportEntity.TABLE_NAME} tmr JOIN ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc ON tmr.tmrTerritoryMembersId = tmc.territoryMemberId  
        JOIN ${TerritoryEntity.TABLE_NAME} t ON tmc.tmcTerritoriesId = t.territoryId  
        JOIN ${CongregationTerritoryView.VIEW_NAME} ctv 
            ON t.territoryId = ctv.ctTerritoriesId AND ctv.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE ctv.isFavorite END)
        LEFT JOIN ${TerritoryMemberView.VIEW_NAME} tmv ON tmr.tmrTerritoryMembersId = tmv.tmrTerritoryMembersId AND t.territoryId = tmv.tmcTerritoriesId
                                                        AND tmv.pseudonym = :username AND tmv.deliveryDate IS NULL
    WHERE (:username IS NULL OR tmv.tmcTerritoriesId IS NOT NULL)
    """)
    fun selectEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryMemberReportEntity>>

    // READS:
    @Query("SELECT * FROM ${TerritoryMemberReportEntity.TABLE_NAME}")
    fun findAll(): Flow<List<TerritoryMemberReportEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TerritoryMemberReportEntity.TABLE_NAME} WHERE territoryMemberReportId = :id")
    fun findById(id: UUID): Flow<TerritoryMemberReportEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoryMemberReportView.VIEW_NAME} WHERE tmrTerritoryStreetsId = :territoryStreetId
    ORDER BY ifnull(strftime($DB_FRACT_SEC_TIME, deliveryDate), strftime($DB_FRACT_SEC_TIME, receivingDate)) DESC
        """
    )
    fun findByTerritoryStreetId(territoryStreetId: UUID): Flow<List<TerritoryMemberReportView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryStreetId(territoryStreetId: UUID) =
        findByTerritoryStreetId(territoryStreetId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoryMemberReportView.VIEW_NAME} WHERE tmrHousesId = :houseId
    ORDER BY ifnull(strftime($DB_FRACT_SEC_TIME, deliveryDate), strftime($DB_FRACT_SEC_TIME, receivingDate)) DESC
        """
    )
    fun findByHouseId(houseId: UUID): Flow<List<TerritoryMemberReportView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(houseId: UUID) = findByHouseId(houseId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoryMemberReportView.VIEW_NAME} WHERE tmrRoomsId = :roomId
    ORDER BY ifnull(strftime($DB_FRACT_SEC_TIME, deliveryDate), strftime($DB_FRACT_SEC_TIME, receivingDate)) DESC
        """
    )
    fun findByRoomId(roomId: UUID): Flow<List<TerritoryMemberReportView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByRoomId(roomId: UUID) = findByRoomId(roomId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TerritoryReportStreetView.VIEW_NAME} WHERE territoryStreetId = :territoryStreetId AND streetLocCode = :locale")
    fun findReportStreetByTerritoryStreetId(
        territoryStreetId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<TerritoryReportStreetView>

    @ExperimentalCoroutinesApi
    fun findDistinctReportStreetByTerritoryStreetId(territoryStreetId: UUID) =
        findReportStreetByTerritoryStreetId(territoryStreetId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TerritoryReportStreetView.VIEW_NAME} WHERE tmcTerritoriesId = :territoryId AND streetLocCode = :locale ORDER BY streetName")
    fun findReportStreetsByTerritoryId(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryReportStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctReportStreetsByTerritoryId(territoryId: UUID) =
        findReportStreetsByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TerritoryReportHouseView.VIEW_NAME} WHERE houseId = :houseId AND streetLocCode = :locale")
    fun findReportHouseByHouseId(
        houseId: UUID,
        locale: String? = Locale.getDefault().language
    ): Flow<TerritoryReportHouseView>

    @ExperimentalCoroutinesApi
    fun findDistinctReportHouseByHouseId(houseId: UUID) =
        findReportHouseByHouseId(houseId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoryReportHouseView.VIEW_NAME}
    WHERE tmcTerritoriesId = :territoryId AND ifnull(territoryStreetId, '') = ifnull(:territoryStreetId, ifnull(territoryStreetId, ''))
         AND streetLocCode = :locale
    ORDER BY hStreetsId, houseNum, houseLetter, buildingNum
        """
    )
    fun findReportHousesByTerritoryIdAndTerritoryStreetId(
        territoryId: UUID,
        territoryStreetId: UUID? = null,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryReportHouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctReportHousesByTerritoryIdAndTerritoryStreetId(
        territoryId: UUID, territoryStreetId: UUID? = null
    ) = findReportHousesByTerritoryIdAndTerritoryStreetId(territoryId, territoryStreetId)
        .distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${TerritoryReportRoomView.VIEW_NAME} WHERE roomId = :roomId AND streetLocCode = :locale")
    fun findReportRoomByRoomId(
        roomId: UUID,
        locale: String? = Locale.getDefault().language
    ): Flow<TerritoryReportRoomView>

    @ExperimentalCoroutinesApi
    fun findDistinctReportRoomByRoomId(roomId: UUID) =
        findReportRoomByRoomId(roomId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoryReportRoomView.VIEW_NAME}
    WHERE tmcTerritoriesId = :territoryId AND ifnull(rHousesId, '') = ifnull(:houseId, ifnull(rHousesId, ''))
        AND streetLocCode = :locale
    ORDER BY hStreetsId, houseNum, houseLetter, buildingNum
        """
    )
    fun findReportRoomsByTerritoryIdAndHouseId(
        territoryId: UUID, houseId: UUID? = null, locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryReportRoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctReportRoomsByTerritoryIdAndHouseId(territoryId: UUID, houseId: UUID? = null) =
        findReportRoomsByTerritoryIdAndHouseId(territoryId, houseId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territoryMemberReport: TerritoryMemberReportEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg territoryMemberReports: TerritoryMemberReportEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territoryMemberReports: List<TerritoryMemberReportEntity>)

    // UPDATES:
    @Update
    suspend fun update(territoryMemberReport: TerritoryMemberReportEntity)

    @Update
    suspend fun update(vararg territoryMemberReports: TerritoryMemberReportEntity)

    @Query("UPDATE ${TerritoryMemberReportEntity.TABLE_NAME} SET isReportProcessed = :isProcessed WHERE territoryMemberReportId = :territoryMemberReportId")
    suspend fun updateIsReportProcessed(territoryMemberReportId: UUID, isProcessed: Boolean)

    // DELETES:
    @Delete
    suspend fun delete(territoryMemberReport: TerritoryMemberReportEntity)

    @Delete
    suspend fun delete(vararg territoryMemberReports: TerritoryMemberReportEntity)

    @Delete
    suspend fun delete(territoryMemberReports: List<TerritoryMemberReportEntity>)

    @Query("DELETE FROM ${TerritoryMemberReportEntity.TABLE_NAME} WHERE territoryMemberReportId = :territoryMemberReportId")
    suspend fun deleteById(territoryMemberReportId: UUID)

    @Query("DELETE FROM ${TerritoryMemberReportEntity.TABLE_NAME}")
    suspend fun deleteAll()
}