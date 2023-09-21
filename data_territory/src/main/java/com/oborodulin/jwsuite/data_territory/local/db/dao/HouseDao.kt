package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetHouseView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

@Dao
interface HouseDao {
    // READS:
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} ORDER BY hStreetsId, houseNum, houseLetter, buildingNum")
    fun findAll(): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE houseId = :houseId")
    fun findById(houseId: UUID): Flow<HouseView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE hStreetsId = :streetId ORDER BY houseNum, houseLetter, buildingNum")
    fun findByStreetId(streetId: UUID): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByStreetId(streetId: UUID) = findByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE hTerritoriesId = :territoryId ORDER BY streetName, houseNum, houseLetter, buildingNum")
    fun findByTerritoryId(territoryId: UUID): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT tshv.* FROM ${TerritoryStreetHouseView.VIEW_NAME} tshv WHERE tshv.tsTerritoriesId = :territoryId AND tshv.streetLocCode = :locale ORDER BY streetName, houseNum, houseLetter, buildingNum")
    fun findOnTerritoryStreetsByTerritoryId(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryStreetHouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctOnTerritoryStreetsByTerritoryId(territoryId: UUID) =
        findOnTerritoryStreetsByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT hv.* FROM ${HouseView.VIEW_NAME} hv JOIN ${TerritoryEntity.TABLE_NAME} t 
            ON t.territoryId = :territoryId AND hv.hTerritoriesId IS NULL 
                AND hv.${PX_LOCALITY}localityId = t.tLocalitiesId 
                AND ifnull(hv.hMicrodistrictsId, '') = ifnull(t.tMicrodistrictsId, '') 
                AND ifnull(hv.hLocalityDistrictsId , '') = ifnull(t.tLocalityDistrictsId, '')
                AND hv.streetLocCode = :locale
        WHERE NOT EXISTS (SELECT e.entranceId FROM ${EntranceEntity.TABLE_NAME} e WHERE e.eHousesId = hv.houseId AND e.eTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT f.floorId FROM ${FloorEntity.TABLE_NAME} f WHERE f.fHousesId = hv.houseId AND f.fTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT r.roomId FROM ${RoomEntity.TABLE_NAME} r WHERE r.rHousesId = hv.houseId AND r.rTerritoriesId IS NOT NULL)
        ORDER BY houseNum, houseLetter, buildingNum, streetName
        """
    )
    fun findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID
    ) = findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId
    ).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT group_concat((CASE WHEN buildingNum IS NOT NULL THEN houseNum || houseLetter || '-' || buildingNum ELSE houseNum || houseLetter END), ', ') AS houseNums FROM ${HouseView.VIEW_NAME} WHERE hTerritoriesId = :territoryId")
    fun findNumsByTerritoryId(territoryId: UUID): Flow<String?>

    @ExperimentalCoroutinesApi
    fun findNumsDistinctByTerritoryId(territoryId: UUID) =
        findNumsByTerritoryId(territoryId).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(house: HouseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg houses: HouseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(houses: List<HouseEntity>)

    // UPDATES:
    @Update
    suspend fun update(house: HouseEntity)

    @Update
    suspend fun update(vararg houses: HouseEntity)

    // DELETES:
    @Delete
    suspend fun delete(house: HouseEntity)

    @Delete
    suspend fun delete(vararg houses: HouseEntity)

    @Delete
    suspend fun delete(houses: List<HouseEntity>)

    @Query("DELETE FROM ${HouseEntity.TABLE_NAME} WHERE houseId = :houseId")
    suspend fun deleteById(houseId: UUID)

    @Query("DELETE FROM ${HouseEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // API:
    @Query("SELECT ifnull(MAX(houseNum), 0) + 1 FROM ${HouseEntity.TABLE_NAME} WHERE hStreetsId = :streetId")
    fun getNextHouseNum(streetId: UUID): Int

    @Query("UPDATE ${HouseEntity.TABLE_NAME} SET hTerritoriesId = NULL WHERE houseId = :houseId")
    suspend fun clearTerritoryById(houseId: UUID)

    @Query("UPDATE ${HouseEntity.TABLE_NAME} SET hTerritoriesId = :territoryId WHERE houseId = :houseId")
    suspend fun updateTerritoryIdById(houseId: UUID, territoryId: UUID)
}