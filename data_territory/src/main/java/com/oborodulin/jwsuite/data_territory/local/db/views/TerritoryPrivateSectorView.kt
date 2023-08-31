package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FALSE
import java.util.UUID

@DatabaseView(
    viewName = TerritoryPrivateSectorView.VIEW_NAME,
    value = """
SELECT ts.tsTerritoriesId AS territoryId, ifnull(s.isStreetPrivateSector, ts.isTerStreetPrivateSector) isPrivateSector
FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = ts.tsStreetsId
UNION ALL
SELECT h.hTerritoriesId AS territoryId, ifnull(h.isHousePrivateSector, s.isStreetPrivateSector) isPrivateSector
FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.hStreetsId WHERE h.hTerritoriesId IS NOT NULL 
UNION ALL
SELECT e.eTerritoriesId AS territoryId, $DB_FALSE AS isPrivateSector FROM ${EntranceEntity.TABLE_NAME} e WHERE e.eTerritoriesId IS NOT NULL 
UNION ALL
SELECT f.fTerritoriesId AS territoryId, $DB_FALSE AS isPrivateSector FROM ${FloorEntity.TABLE_NAME} f WHERE f.fTerritoriesId IS NOT NULL 
UNION ALL
SELECT r.rTerritoriesId AS territoryId, $DB_FALSE AS isPrivateSector FROM ${RoomEntity.TABLE_NAME} r WHERE r.rTerritoriesId IS NOT NULL 
GROUP BY territoryId, isPrivateSector
"""
)
class TerritoryPrivateSectorView(
    val territoryId: UUID,
    val isPrivateSector: Boolean? = null
) {
    companion object {
        const val VIEW_NAME = "territory_private_sectors_view"
    }
}
