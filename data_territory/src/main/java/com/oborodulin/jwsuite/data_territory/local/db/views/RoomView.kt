package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LOCALITY

@DatabaseView(
    viewName = RoomView.VIEW_NAME,
    value = """
SELECT hv.*, tv.*, r.* 
FROM ${RoomEntity.TABLE_NAME} r JOIN ${HouseView.VIEW_NAME} hv ON hv.houseId = r.rHousesId
    LEFT JOIN ${TerritoryView.VIEW_NAME} tv ON tv.territoryId = r.rTerritoriesId AND tv.${PX_TERRITORY_LOCALITY}localityLocCode  = hv.streetLocCode
"""
)
class RoomView(
    @Embedded val house: HouseView,
    @Embedded val territory: TerritoryView?,
    @Embedded val room: RoomEntity
) {
    companion object {
        const val VIEW_NAME = "rooms_view"
    }
}