package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.util.RoadType
import java.util.UUID

@DatabaseView(
    viewName = TerritoryInfoView.VIEW_NAME,
    value = """
SELECT ts.tsTerritoriesId AS territoryId, sv.streetId, sv.roadType, sv.streetName, 
    sv.streetLocCode, ts.isEvenSide, ifnull(ts.estTerStreetHouses, sv.estStreetHouses) AS estimatedHouses,
    ifnull(sv.isStreetPrivateSector, ts.isTerStreetPrivateSector) AS isPrivateStreet, h.*
FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${GeoStreetView.VIEW_NAME} sv ON sv.streetId = ts.tsStreetsId
    LEFT JOIN ${HouseEntity.TABLE_NAME} h ON h.hStreetsId = ts.tsStreetsId AND ifnull(h.hTerritoriesId, ts.tsTerritoriesId) = ts.tsTerritoriesId 
--    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.entranceId = ''
--    LEFT JOIN ${FloorEntity.TABLE_NAME} f ON f.floorId = ''
--    LEFT JOIN ${RoomEntity.TABLE_NAME} r ON r.roomId = ''
UNION ALL
SELECT ifnull(h.hTerritoriesId, ifnull(e.eTerritoriesId, ifnull(f.fTerritoriesId, ifnull(r.rTerritoriesId)))) AS territoryId, sv.streetId, sv.roadType, sv.streetName, 
    sv.streetLocCode, null AS isEven, sv.estStreetHouses, sv.isStreetPrivateSector AS isPrivateStreet, h.*, e.*, f.*, r.*
FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.eHousesId = h.houseId
    LEFT JOIN ${FloorEntity.TABLE_NAME} f ON f.fHousesId = h.houseId AND ifnull(f.fEntrancesId, ifnull(e.entranceId, '')) = ifnull(e.entranceId, '')
    LEFT JOIN ${RoomEntity.TABLE_NAME} r ON r.rHousesId = h.houseId AND ifnull(r.rEntrancesId, ifnull(e.entranceId, '')) = ifnull(e.entranceId, '')
                                            AND ifnull(r.rFloorsId, ifnull(f.floorId, '')) = ifnull(f.floorId, '')
"""
)
class TerritoryInfoView(
    val territoryId: UUID,
    val streetId: UUID,
    val roadType: RoadType,
    val streetName: String,
    val streetLocCode: String,
    val isEven: Boolean? = null,
    val estimatedHouses: Int? = null,
    val isPrivateStreet: Boolean,
    @Embedded
    val house: HouseEntity? = null,
    @Embedded
    val entrance: EntranceEntity? = null,
    @Embedded
    val floor: FloorEntity? = null,
    @Embedded
    val room: RoomEntity? = null
) {
    companion object {
        const val VIEW_NAME = "territory_info_view"
    }
}
/*
select group_concat(a, ',')
from yourtable
*/
