package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import java.util.UUID

@DatabaseView(
    viewName = TerritoryStreetNamesAndHouseNumsView.VIEW_NAME,
    value = """
SELECT tsh.tCongregationsId AS congregationId, tsh.territoryId, tsh.streetName AS streetNames, tsh.streetLocCode, GROUP_CONCAT(tsh.houseFullNum, ',') AS houseFullNums
FROM (SELECT t.tCongregationsId, t.territoryId, tsv.streetName, tsv.streetLocCode, '' AS houseFullNum
    FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${TerritoryStreetView.VIEW_NAME} tsv ON tsv.tsTerritoriesId = t.territoryId
    UNION ALL
    SELECT t.tCongregationsId, t.territoryId, sv.streetName, sv.streetLocCode, 
            (CASE WHEN h.buildingNum IS NOT NULL THEN h.houseNum || h.houseLetter || '-' || h.buildingNum ELSE h.houseNum || h.houseLetter END) AS houseFullNum 
    FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${HouseEntity.TABLE_NAME} h ON h.hTerritoriesId = t.territoryId 
        JOIN ${StreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    UNION ALL
    SELECT t.tCongregationsId, t.territoryId, sv.streetName, sv.streetLocCode, 
            (CASE WHEN h.buildingNum IS NOT NULL THEN h.houseNum || h.houseLetter || '-' || h.buildingNum ELSE h.houseNum || h.houseLetter END) AS houseFullNum 
    FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${EntranceEntity.TABLE_NAME} e ON e.eTerritoriesId = t.territoryId 
        JOIN ${HouseEntity.TABLE_NAME} h ON h.houseId = e.eHousesId 
        JOIN ${StreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    UNION ALL
    SELECT t.tCongregationsId, t.territoryId, sv.streetName, sv.streetLocCode, 
            (CASE WHEN h.buildingNum IS NOT NULL THEN h.houseNum || h.houseLetter || '-' || h.buildingNum ELSE h.houseNum || h.houseLetter END) AS houseFullNum 
    FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${FloorEntity.TABLE_NAME} f ON f.fTerritoriesId = t.territoryId 
        JOIN ${HouseEntity.TABLE_NAME} h ON h.houseId = f.fHousesId 
        JOIN ${StreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    UNION ALL
    SELECT t.tCongregationsId, t.territoryId, sv.streetName, sv.streetLocCode, 
            (CASE WHEN h.buildingNum IS NOT NULL THEN h.houseNum || h.houseLetter || '-' || h.buildingNum ELSE h.houseNum || h.houseLetter END) AS houseFullNum
    FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${RoomEntity.TABLE_NAME} r ON r.rTerritoriesId = t.territoryId 
        JOIN ${HouseEntity.TABLE_NAME} h ON h.houseId = r.rHousesId 
        JOIN ${StreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId) tsh
GROUP BY tsh.tCongregationsId, tsh.territoryId, tsh.streetName, tsh.streetLocCode
    """
)
class TerritoryStreetNamesAndHouseNumsView(
    val congregationId: UUID,
    val territoryId: UUID,
    val streetNames: String,
    val streetLocCode: String,
    val houseFullNums: String?
) {
    companion object {
        const val VIEW_NAME = "territory_street_names_and_house_nums_view"
    }
}