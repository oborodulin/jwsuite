package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LOCALITY

@DatabaseView(
    viewName = TerritoryStreetHouseView.VIEW_NAME,
    value = """
SELECT tsv.*, tv.*, h.* 
FROM ${TerritoryStreetView.VIEW_NAME} tsv JOIN ${TerritoryView.VIEW_NAME} tv ON tv.territoryId = tsv.tsTerritoriesId AND tv.${PX_TERRITORY_LOCALITY}localityLocCode  = tsv.streetLocCode
    LEFT JOIN ${HouseEntity.TABLE_NAME} h ON h.hStreetsId = tsv.tsStreetsId AND h.hTerritoriesId = tsv.tsTerritoriesId
        AND ifnull(h.hLocalityDistrictsId, "") = ifnull(tv.tLocalityDistrictsId, "") AND ifnull(h.hMicrodistrictsId, "") = ifnull(tv.tMicrodistrictsId, "") 
    """
)
class TerritoryStreetHouseView(
    @Embedded val territoryStreet: TerritoryStreetView,
    @Embedded val territory: TerritoryView,
    @Embedded val house: HouseEntity?
) {
    companion object {
        const val VIEW_NAME = "territory_street_houses_view"
    }
}

data class Key(val territoryStreet: TerritoryStreetView, val territory: TerritoryView)

fun TerritoryStreetHouseView.toKey() = Key(this.territoryStreet, this.territory)
