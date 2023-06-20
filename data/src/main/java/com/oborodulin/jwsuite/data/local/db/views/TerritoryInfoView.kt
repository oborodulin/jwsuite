package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.util.RoadType
import java.util.UUID

@DatabaseView(
    viewName = TerritoryInfoView.VIEW_NAME,
    value = """
SELECT ts.territoriesId AS territoryId, sv.streetId, sv.roadType, sv.streetName, 
    sv.streetLocCode, ts.isEven, ifnull(ts.estimatedHouses, sv.estimatedHouses) AS estimatedHouses,
    ifnull(sv.isPrivateSector, ts.isPrivateSector) AS isPrivateStreet, h.*
FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${GeoStreetView.VIEW_NAME} sv ON sv.streetId = ts.streetsId
    LEFT JOIN ${HouseEntity.TABLE_NAME} h ON h.streetsId = ts.streetsId AND ifnull(h.territoriesId, ts.territoriesId) = ts.territoriesId 
--    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.entranceId = ''
--    LEFT JOIN ${FloorEntity.TABLE_NAME} f ON f.floorId = ''
--    LEFT JOIN ${RoomEntity.TABLE_NAME} r ON r.roomId = ''
UNION ALL
SELECT ifnull(h.territoriesId, ifnull(e.territoriesId, ifnull(f.territoriesId, ifnull(r.territoriesId)))) AS territoryId, sv.streetId, sv.roadType, sv.streetName, 
    sv.streetLocCode, null AS isEven, sv.estimatedHouses, sv.isPrivateSector AS isPrivateStreet, h.*, e.*, f.*, r.*
FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetView.VIEW_NAME} sv ON sv.streetId = h.streetsId
    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.housesId = h.houseId
    LEFT JOIN ${FloorEntity.TABLE_NAME} f ON f.housesId = h.houseId AND ifnull(f.entrancesId, ifnull(e.entranceId, '')) = ifnull(e.entranceId, '')
    LEFT JOIN ${RoomEntity.TABLE_NAME} r ON r.housesId = h.houseId AND ifnull(r.entrancesId, ifnull(e.entranceId, '')) = ifnull(e.entranceId, '')
                                            AND ifnull(r.floorsId, ifnull(f.floorId, '')) = ifnull(f.floorId, '')
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
