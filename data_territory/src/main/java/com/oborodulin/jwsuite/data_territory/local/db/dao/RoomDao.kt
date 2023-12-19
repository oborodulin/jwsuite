package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.RoomView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface RoomDao {
    // READS:
    @Query("SELECT * FROM ${RoomView.VIEW_NAME} ORDER BY rHousesId, roomNum")
    fun findAll(): Flow<List<RoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${RoomView.VIEW_NAME} WHERE roomId = :roomId")
    fun findById(roomId: UUID): Flow<RoomView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${RoomView.VIEW_NAME} WHERE rHousesId = :houseId ORDER BY roomNum")
    fun findByHouseId(houseId: UUID): Flow<List<RoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(houseId: UUID) = findByHouseId(houseId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${RoomView.VIEW_NAME} WHERE rEntrancesId = :entranceId ORDER BY roomNum")
    fun findByEntranceId(entranceId: UUID): Flow<List<RoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByEntranceId(entranceId: UUID) =
        findByEntranceId(entranceId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${RoomView.VIEW_NAME} WHERE rFloorsId = :floorId ORDER BY roomNum")
    fun findByFloorId(floorId: UUID): Flow<List<RoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByFloorId(floorId: UUID) = findByFloorId(floorId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${RoomView.VIEW_NAME} WHERE rTerritoriesId = :territoryId ORDER BY roomNum")
    fun findByTerritoryId(territoryId: UUID): Flow<List<RoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT rv.* FROM ${RoomView.VIEW_NAME} rv JOIN ${TerritoryEntity.TABLE_NAME} t 
            ON t.territoryId = :territoryId 
                AND rv.hTerritoriesId IS NULL AND rv.eTerritoriesId IS NULL AND rv.fTerritoriesId IS NULL AND rv.rTerritoriesId IS NULL
                AND rv.${PX_LOCALITY}localityId = t.tLocalitiesId 
                AND ifnull(rv.hMicrodistrictsId, '') = ifnull(t.tMicrodistrictsId, ifnull(rv.hMicrodistrictsId, '')) 
                AND ifnull(rv.hLocalityDistrictsId , '') = ifnull(t.tLocalityDistrictsId, ifnull(rv.hLocalityDistrictsId , ''))
                AND rv.streetLocCode = :locale
        WHERE NOT EXISTS (SELECT h.houseId FROM ${HouseEntity.TABLE_NAME} h WHERE h.houseId = rv.eHousesId AND h.hTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT e.entranceId FROM ${EntranceEntity.TABLE_NAME} e WHERE e.eHousesId = rv.houseId AND e.eTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT f.floorId FROM ${FloorEntity.TABLE_NAME} f WHERE f.floorId = rv.floorId AND f.fTerritoriesId IS NOT NULL)
        ORDER BY rv.roomNum, rv.houseNum, rv.houseLetter, rv.buildingNum, rv.streetName
        """
    )
    fun findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<RoomView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID
    ) = findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId
    ).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg rooms: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(rooms: List<RoomEntity>)

    // UPDATES:
    @Update
    suspend fun update(room: RoomEntity)

    @Update
    suspend fun update(vararg rooms: RoomEntity)

    // DELETES:
    @Delete
    suspend fun delete(room: RoomEntity)

    @Delete
    suspend fun delete(vararg rooms: RoomEntity)

    @Delete
    suspend fun delete(rooms: List<RoomEntity>)

    @Query("DELETE FROM ${RoomEntity.TABLE_NAME} WHERE roomId = :roomId")
    suspend fun deleteById(roomId: UUID)

    @Query("DELETE FROM ${RoomEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // API:
    @Query("SELECT ifnull(MAX(roomNum), 0) + 1 FROM ${RoomEntity.TABLE_NAME} WHERE rHousesId = :houseId")
    fun getNextRoomNum(houseId: UUID): Int

    @Query("UPDATE ${RoomEntity.TABLE_NAME} SET rTerritoriesId = NULL WHERE roomId = :roomId")
    suspend fun clearTerritoryById(roomId: UUID)

    @Query("UPDATE ${RoomEntity.TABLE_NAME} SET rTerritoriesId = :territoryId WHERE roomId = :roomId")
    suspend fun updateTerritoryIdById(roomId: UUID, territoryId: UUID)
}