package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_geo.util.Constants
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.FloorView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface FloorDao {
    // READS:
    @Query("SELECT * FROM ${FloorView.VIEW_NAME} ORDER BY houseNum, floorNum")
    fun findAll(): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE floorId = :floorId")
    fun findById(floorId: UUID): Flow<FloorView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE fHousesId = :houseId ORDER BY floorNum")
    fun findByHouseId(houseId: UUID): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(entranceId: UUID) = findByHouseId(entranceId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE fEntrancesId = :entranceId ORDER BY entranceNum, floorNum")
    fun findByEntranceId(entranceId: UUID): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByEntranceId(entranceId: UUID) =
        findByEntranceId(entranceId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${FloorView.VIEW_NAME} WHERE fTerritoriesId = :territoryId ORDER BY houseNum, floorNum")
    fun findByTerritoryId(territoryId: UUID): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT fv.* FROM ${FloorView.VIEW_NAME} fv JOIN ${TerritoryEntity.TABLE_NAME} t 
                ON t.territoryId = :territoryId AND fv.hTerritoriesId IS NULL 
                    AND fv.${Constants.PX_LOCALITY}localityId = t.tLocalitiesId 
                    AND ifnull(fv.hMicrodistrictsId, '') = ifnull(t.tMicrodistrictsId, '') 
                    AND ifnull(fv.hLocalityDistrictsId , '') = ifnull(t.tLocalityDistrictsId, '')
                    AND fv.streetLocCode = :locale
        WHERE NOT EXISTS (SELECT h.houseId FROM ${HouseEntity.TABLE_NAME} h WHERE h.houseId = fv.eHousesId AND h.hTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT e.entranceId FROM ${EntranceEntity.TABLE_NAME} e WHERE e.eHousesId = fv.houseId AND e.eTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT r.roomId FROM ${RoomEntity.TABLE_NAME} r WHERE r.rHousesId = fv.houseId AND r.rTerritoriesId IS NOT NULL)
        ORDER BY entranceNum, houseNum, houseLetter, buildingNum, streetName
        """
    )
    fun findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<FloorView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID
    ) = findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId
    ).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(floor: FloorEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg floors: FloorEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(floors: List<FloorEntity>)

    // UPDATES:
    @Update
    suspend fun update(floor: FloorEntity)

    @Update
    suspend fun update(vararg floors: FloorEntity)

    // DELETES:
    @Delete
    suspend fun delete(floor: FloorEntity)

    @Delete
    suspend fun delete(vararg floors: FloorEntity)

    @Delete
    suspend fun delete(floors: List<FloorEntity>)

    @Query("DELETE FROM ${FloorEntity.TABLE_NAME} WHERE floorId = :floorId")
    suspend fun deleteById(floorId: UUID)

    @Query("DELETE FROM ${FloorEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // API:
    @Query("SELECT ifnull(MAX(floorNum), 0) + 1 FROM ${FloorEntity.TABLE_NAME} WHERE fHousesId = :houseId")
    fun getNextHouseNum(houseId: UUID): Int

    @Query("UPDATE ${FloorEntity.TABLE_NAME} SET fTerritoriesId = NULL WHERE floorId = :floorId")
    suspend fun clearTerritoryById(floorId: UUID)

    @Query("UPDATE ${FloorEntity.TABLE_NAME} SET fTerritoriesId = :territoryId WHERE floorId = :floorId")
    suspend fun updateTerritoryIdById(floorId: UUID, territoryId: UUID)
}