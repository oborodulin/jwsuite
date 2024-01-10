package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface HouseDao {
    // READS:
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} ORDER BY hStreetsId, houseNum, houseLetter, buildingNum")
    fun findAll(): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE houseId = :houseId AND streetLocCode = :locale")
    fun findById(houseId: UUID, locale: String? = Locale.getDefault().language): Flow<HouseView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE hStreetsId = :streetId AND streetLocCode = :locale ORDER BY houseNum, houseLetter, buildingNum")
    fun findByStreetId(
        streetId: UUID,
        locale: String? = Locale.getDefault().language
    ): Flow<List<HouseView>>

    @ExperimentalCoroutinesApi
    fun findDistinctByStreetId(streetId: UUID) = findByStreetId(streetId).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${HouseView.VIEW_NAME} WHERE hTerritoriesId = :territoryId AND streetLocCode = :locale ORDER BY streetName, houseNum, houseLetter, buildingNum")
    fun findByTerritoryId(
        territoryId: UUID,
        locale: String? = Locale.getDefault().language
    ): Flow<List<HouseView>>

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
                AND ifnull(hv.hMicrodistrictsId, '') = ifnull(t.tMicrodistrictsId, ifnull(hv.hMicrodistrictsId, '')) 
                AND ifnull(hv.hLocalityDistrictsId , '') = ifnull(t.tLocalityDistrictsId, ifnull(hv.hLocalityDistrictsId , ''))
                AND hv.streetLocCode = :locale
            LEFT JOIN ${TerritoryStreetView.VIEW_NAME} tsv ON tsv.tsTerritoriesId = t.territoryId 
                                                            AND ifnull(tsv.isTerStreetPrivateSector, tsv.isStreetPrivateSector) = $DB_TRUE
        WHERE NOT EXISTS (SELECT e.entranceId FROM ${EntranceEntity.TABLE_NAME} e WHERE e.eHousesId = hv.houseId AND e.eTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT f.floorId FROM ${FloorEntity.TABLE_NAME} f WHERE f.fHousesId = hv.houseId AND f.fTerritoriesId IS NOT NULL)
            AND NOT EXISTS (SELECT r.roomId FROM ${RoomEntity.TABLE_NAME} r WHERE r.rHousesId = hv.houseId AND r.rTerritoriesId IS NOT NULL)
            AND (tsv.territoryStreetId IS NULL OR tsv.territoryStreetId IS NOT NULL AND hv.hStreetsId = tsv.tsStreetsId AND hv.isHousePrivateSector = $DB_TRUE)
        ORDER BY hv.houseNum, hv.houseLetter, hv.buildingNum, hv.streetName
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

    //-----------------------------
    @Query(
        """
    SELECT EXISTS (SELECT h.houseId FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.hStreetsId
                        JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON ts.tsStreetsId = s.streetId
                    WHERE ts.territoryStreetId = :territoryStreetId
                        AND (ts.isEvenSide IS NULL OR (ts.isEvenSide = 1 AND h.houseNum % 2 = 0 OR ts.isEvenSide = 0 AND h.houseNum % 2 <> 0))
                    LIMIT 1)
    """
    )
    fun existsByTerritoryStreetId(territoryStreetId: UUID): Flow<Boolean>

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