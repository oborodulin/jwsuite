package com.oborodulin.jwsuite.data_territory.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.CongregationTerritoryView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface TerritoryStreetDao {
    // EXTRACTS:
    @Query(
        """
    SELECT ts.* FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${TerritoryEntity.TABLE_NAME} t ON ts.tsTerritoriesId = t.territoryId  
        JOIN ${CongregationTerritoryView.VIEW_NAME} ctv 
            ON t.territoryId = ctv.ctTerritoriesId AND ctv.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE ctv.isFavorite END)
        LEFT JOIN ${TerritoryMemberView.VIEW_NAME} tmv ON t.territoryId = tmv.tmcTerritoriesId AND tmv.pseudonym = :username AND tmv.deliveryDate IS NULL
    WHERE (:username IS NULL OR tmv.tmcTerritoriesId IS NOT NULL) 
    """
    )
    fun selectEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryStreetEntity>>

    // READS:
    @Query(
        "SELECT tsv.* FROM ${TerritoryStreetView.VIEW_NAME} tsv WHERE tsv.territoryStreetId = :territoryStreetId AND tsv.streetLocCode = :locale"
    )
    fun findTerritoryStreetById(
        territoryStreetId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<TerritoryStreetView>

    @ExperimentalCoroutinesApi
    fun findDistinctTerritoryStreetById(territoryStreetId: UUID) =
        findTerritoryStreetById(territoryStreetId).distinctUntilChanged()

    //-----------------------------
    @Query(
        "SELECT tsv.* FROM ${TerritoryStreetView.VIEW_NAME} tsv WHERE tsv.tsTerritoriesId = :territoryId AND tsv.streetLocCode = :locale ORDER BY tsv.streetName"
    )
    fun findStreetsByTerritoryId(
        territoryId: UUID,
        locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctStreetsByTerritoryId(territoryId: UUID) =
        findStreetsByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    @Query(
        """
    SELECT sv.* FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${GeoStreetView.VIEW_NAME} sv 
        ON t.territoryId = :territoryId AND sv.streetLocCode = :locale AND sv.${PX_LOCALITY}localityId = t.tLocalitiesId
        LEFT JOIN ${GeoStreetDistrictEntity.TABLE_NAME} ds ON ds.dsStreetsId = sv.streetId 
                                                            AND ifnull(ds.dsMicrodistrictsId, '') = ifnull(t.tMicrodistrictsId, ifnull(ds.dsMicrodistrictsId, ''))
                                                            AND ifnull(ds.dsLocalityDistrictsId , '') = ifnull(t.tLocalityDistrictsId, ifnull(ds.dsLocalityDistrictsId , '')) 
    WHERE NOT EXISTS (SELECT ts.territoryStreetId FROM ${TerritoryStreetEntity.TABLE_NAME} ts WHERE ts.tsTerritoriesId = :territoryId AND ts.tsStreetsId = sv.streetId)
        AND (t.tMicrodistrictsId IS NULL OR (t.tMicrodistrictsId IS NOT NULL AND ds.streetDistrictId IS NOT NULL))
        AND (t.tLocalityDistrictsId IS NULL OR (t.tLocalityDistrictsId IS NOT NULL AND ds.streetDistrictId IS NOT NULL))
    ORDER BY sv.streetName
    """
    )
    fun findStreetsForTerritoryByTerritoryId(
        territoryId: UUID, locale: String? = Locale.getDefault().language
    ): Flow<List<GeoStreetView>>

    @ExperimentalCoroutinesApi
    fun findDistinctStreetsForTerritoryByTerritoryId(territoryId: UUID) =
        findStreetsForTerritoryByTerritoryId(territoryId).distinctUntilChanged()

    //-----------------------------
    /*
        @Query(
            "SELECT group_concat(DISTINCT tsv.streetName, ', ') AS streetNames FROM ${TerritoryStreetView.VIEW_NAME} tsv WHERE tsv.tsTerritoriesId = :territoryId AND tsv.streetLocCode = :locale"
        )
        fun findNamesByTerritoryId(territoryId: UUID, locale: String? = Locale.getDefault().language):
                Flow<String?>

        @ExperimentalCoroutinesApi
        fun findNamesDistinctByTerritoryId(territoryId: UUID) =
            findNamesByTerritoryId(territoryId).distinctUntilChanged()
    */
    @Query(
        """
    SELECT tsh.congregationId, tsh.territoryId, GROUP_CONCAT(tsh.streetNames, ',') AS streetNames, tsh.streetLocCode, tsh.houseFullNums   
    FROM ${TerritoryStreetNamesAndHouseNumsView.VIEW_NAME} tsh LEFT JOIN ${FavoriteCongregationView.VIEW_NAME} fcv ON fcv.congregationId = tsh.congregationId
    WHERE tsh.congregationId = ifnull(:congregationId, fcv.congregationId) AND tsh.streetLocCode = :locale
    GROUP BY tsh.congregationId, tsh.territoryId, tsh.houseFullNums
    """
    )
    fun findStreetNamesAndHouseNumsByCongregationId(
        congregationId: UUID? = null, locale: String? = Locale.getDefault().language
    ): Flow<List<TerritoryStreetNamesAndHouseNumsView>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg territoryStreet: TerritoryStreetEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(territoryStreets: List<TerritoryStreetEntity>)

    suspend fun insert(
        territory: TerritoryEntity,
        street: GeoStreetEntity,
        isEvenSide: Boolean? = null,
        isPrivateSector: Boolean? = null,
        estimatedHouses: Int? = null
    ) = insert(
        TerritoryStreetEntity(
            tsTerritoriesId = territory.territoryId,
            tsStreetsId = street.streetId,
            isEvenSide = isEvenSide,
            isTerStreetPrivateSector = isPrivateSector,
            estTerStreetHouses = estimatedHouses
        )
    )

    suspend fun insert(
        territoryId: UUID,
        streetId: UUID,
        isEvenSide: Boolean? = null,
        isPrivateSector: Boolean? = null,
        estimatedHouses: Int? = null
    ) = insert(
        TerritoryStreetEntity(
            tsTerritoriesId = territoryId,
            tsStreetsId = streetId,
            isEvenSide = isEvenSide,
            isTerStreetPrivateSector = isPrivateSector,
            estTerStreetHouses = estimatedHouses
        )
    )

    // UPDATES:
    @Update
    suspend fun update(vararg territoryStreet: TerritoryStreetEntity)

    // DELETES:
    @Delete
    suspend fun deleteStreet(vararg territoryStreet: TerritoryStreetEntity)

    @Query("DELETE FROM ${TerritoryStreetEntity.TABLE_NAME} WHERE territoryStreetId = :territoryStreetId")
    suspend fun deleteStreetById(territoryStreetId: UUID)

    @Query("DELETE FROM ${TerritoryStreetEntity.TABLE_NAME} WHERE tsTerritoriesId = :territoryId")
    suspend fun deleteStreetsByTerritoryId(territoryId: UUID)

    @Query("DELETE FROM ${TerritoryStreetEntity.TABLE_NAME}")
    suspend fun deleteAll()
}