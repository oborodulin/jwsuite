package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface TerritoryReportDao {
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
    @Query(
        """
    SELECT * FROM ${TerritoryReportHouseView.VIEW_NAME}
    WHERE tmcTerritoriesId = :territoryId AND ifnull(territoryStreetId, '') = ifnull(:territoryStreetId, ifnull(territoryStreetId, '')) 
    ORDER BY hStreetsId, houseNum, houseLetter, buildingNum
        """
    )
    fun findHousesByTerritoryIdAndTerritoryStreetId(
        territoryId: UUID, territoryStreetId: UUID? = null
    ): Flow<List<TerritoryReportHouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctHousesByTerritoryIdAndTerritoryStreetId(
        territoryId: UUID, territoryStreetId: UUID? = null
    ) = findHousesByTerritoryIdAndTerritoryStreetId(territoryId, territoryStreetId)
        .distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT * FROM ${TerritoryReportRoomView.VIEW_NAME}
    WHERE tmcTerritoriesId = :territoryId AND ifnull(rHousesId, '') = ifnull(:houseId, ifnull(rHousesId, '')) 
    ORDER BY hStreetsId, houseNum, houseLetter, buildingNum
        """
    )
    fun findRoomsByTerritoryIdAndHouseId(
        territoryId: UUID, houseId: UUID? = null
    ): Flow<List<TerritoryReportRoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctRoomsByTerritoryIdAndHouseId(territoryId: UUID, houseId: UUID? = null) =
        findRoomsByTerritoryIdAndHouseId(territoryId, houseId).distinctUntilChanged()

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