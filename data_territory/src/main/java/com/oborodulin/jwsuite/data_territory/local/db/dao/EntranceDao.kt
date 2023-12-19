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
import com.oborodulin.jwsuite.data_territory.local.db.views.EntranceView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface EntranceDao {
    // READS:
    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} ORDER BY streetName, houseNum, houseLetter, entranceNum")
    fun findAll(): Flow<List<EntranceView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} WHERE entranceId = :entranceId")
    fun findById(entranceId: UUID): Flow<EntranceView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} WHERE eHousesId = :houseId ORDER BY entranceNum")
    fun findByHouseId(houseId: UUID): Flow<List<EntranceView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByHouseId(houseId: UUID) = findByHouseId(houseId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${EntranceView.VIEW_NAME} WHERE eTerritoriesId = :territoryId ORDER BY streetName, houseNum, houseLetter, entranceNum")
    fun findByTerritoryId(territoryId: UUID): Flow<List<EntranceView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryId(territoryId: UUID) =
        findByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
        SELECT ev.* FROM ${EntranceView.VIEW_NAME} ev JOIN ${TerritoryEntity.TABLE_NAME} t 
                ON t.territoryId = :territoryId AND ev.hTerritoriesId IS NULL 
                    AND ev.${PX_LOCALITY}localityId = t.tLocalitiesId 
                    AND ifnull(ev.hMicrodistrictsId, '') = ifnull(t.tMicrodistrictsId, ifnull(ev.hMicrodistrictsId, '')) 
                    AND ifnull(ev.hLocalityDistrictsId , '') = ifnull(t.tLocalityDistrictsId, ifnull(ev.hLocalityDistrictsId , ''))
                    AND ev.streetLocCode = :locale
        WHERE NOT EXISTS (SELECT h.houseId FROM ${HouseEntity.TABLE_NAME} h WHERE h.houseId = ev.eHousesId AND h.hTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT f.floorId FROM ${FloorEntity.TABLE_NAME} f WHERE f.fHousesId = ev.houseId AND f.fTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT r.roomId FROM ${RoomEntity.TABLE_NAME} r WHERE r.rHousesId = ev.houseId AND r.rTerritoriesId IS NOT NULL)
        ORDER BY ev.entranceNum, ev.houseNum, ev.houseLetter, ev.buildingNum, ev.streetName
        """
    )
    fun findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<EntranceView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId: UUID
    ) = findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
        territoryId
    ).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entrance: EntranceEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg entrances: EntranceEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entrances: List<EntranceEntity>)

    // UPDATES:
    @Update
    suspend fun update(entrance: EntranceEntity)

    @Update
    suspend fun update(vararg entrances: EntranceEntity)

    // DELETES:
    @Delete
    suspend fun delete(entrance: EntranceEntity)

    @Delete
    suspend fun delete(vararg entrances: EntranceEntity)

    @Delete
    suspend fun delete(entrances: List<EntranceEntity>)

    @Query("DELETE FROM ${EntranceEntity.TABLE_NAME} WHERE entranceId = :entranceId")
    suspend fun deleteById(entranceId: UUID)

    @Query("DELETE FROM ${EntranceEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // API:
    @Query("SELECT ifnull(MAX(entranceNum), 0) + 1 FROM ${EntranceEntity.TABLE_NAME} WHERE eHousesId = :houseId")
    fun getNextHouseNum(houseId: UUID): Int

    @Query("UPDATE ${EntranceEntity.TABLE_NAME} SET eTerritoriesId = NULL WHERE entranceId = :entranceId")
    suspend fun clearTerritoryById(entranceId: UUID)

    @Query("UPDATE ${EntranceEntity.TABLE_NAME} SET eTerritoriesId = :territoryId WHERE entranceId = :entranceId")
    suspend fun updateTerritoryIdById(entranceId: UUID, territoryId: UUID)
}