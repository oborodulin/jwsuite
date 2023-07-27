package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import com.oborodulin.jwsuite.data.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.data.util.Constants.TDT_ALL_VAL
import com.oborodulin.jwsuite.data.util.Constants.TDT_LOCALITY_DISTRICT_VAL
import com.oborodulin.jwsuite.data.util.Constants.TDT_LOCALITY_VAL
import com.oborodulin.jwsuite.data.util.Constants.TDT_MICRO_DISTRICT_VAL
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import java.util.UUID

@DatabaseView(
    viewName = TerritoryLocationView.VIEW_NAME,
    value = """
SELECT $TDT_ALL_VAL AS territoryLocationType, c.congregationId, NULL AS locationId,
        (SELECT paramValue FROM ${AppSettingEntity.TABLE_NAME} WHERE paramName = ${Constants.PRM_ALL_ITEMS_VAL}) AS locationName,
        NULL AS isPrivateSector
FROM ${CongregationEntity.TABLE_NAME} c
UNION ALL
SELECT td.territoryLocationType, td.congregationId, td.locationId, td.locationName, tpsv.isPrivateSector 
FROM (SELECT (CASE
                WHEN md.microdistrictId IS NOT NULL THEN $TDT_MICRO_DISTRICT_VAL
                WHEN ld.localityDistrictId IS NOT NULL THEN $TDT_LOCALITY_DISTRICT_VAL
                ELSE $TDT_LOCALITY_VAL
            END) AS territoryLocationType, 
            t.territoryId, ct.ctCongregationsId AS congregationId, 
            ifnull(md.microdistrictId, ifnull(ld.localityDistrictId, l.localityId))  AS locationId,
            ifnull(md.microdistrictName, ifnull(ld.locDistrictName, l.localityName)) AS locationName  
    FROM ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct JOIN ${TerritoryEntity.TABLE_NAME} t 
            ON t.territoryId = ct.ctTerritoriesId AND t.isActive = $DB_TRUE AND ct.endUsingDate IS NULL
        JOIN ${LocalityView.VIEW_NAME} l ON l.localityId = t.tLocalitiesId
        LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ld ON ld.localityDistrictId = t.tLocalityDistrictsId
        LEFT JOIN ${MicrodistrictView.VIEW_NAME} md ON md.microdistrictId = t.tMicrodistrictsId) td
            JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tpsv ON tpsv.territoryId = td.territoryId
GROUP BY territoryLocationType, congregationId, locationId, locationName, isPrivateSector
"""
)
class TerritoryLocationView(
    val territoryLocationType: TerritoryLocationType,
    val congregationId: UUID,
    val locationId: UUID?,
    val locationName: String,
    val isPrivateSector: Boolean?
) {
    companion object {
        const val VIEW_NAME = "territory_locations_view"
    }
}
/*
    -- Private sector streets
    SELECT $TDT_MICRO_DISTRICT_VAL AS territoryLocationType, ct.congregationsId AS congregationId,
        s.isPrivateSector, md.microdistrictId AS locationId, md.microdistrictName AS locationName
    FROM ${GeoStreetEntity.TABLE_NAME} s JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON s.streetId = ts.streetsId
        JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId
        JOIN ${GeoMicrodistrictView.VIEW_NAME} md ON md.microdistrictId = ds.microdistrictsId
        JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = ts.territoriesId AND ct.endUsingDate IS NULL
    UNION ALL
    SELECT $TDT_LOCALITY_DISTRICT_VAL AS territoryLocationType, ct.congregationsId AS congregationId,
        s.isPrivateSector, ld.localityDistrictId AS locationId, ld.locationName AS locationName
    FROM ${GeoStreetEntity.TABLE_NAME} s JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON s.streetId = ts.streetsId
        JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId AND ds.microdistrictsId IS NULL
        JOIN ${GeoLocalityDistrictView.VIEW_NAME} ld ON ld.localityDistrictId = ds.localityDistrictsId
        JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = ts.territoriesId AND ct.endUsingDate IS NULL
    UNION ALL
    SELECT $TDT_LOCALITY_VAL AS territoryLocationType, ct.congregationsId AS congregationId,
        s.isPrivateSector, l.localityId AS locationId, l.localityName AS locationName
    FROM ${GeoStreetEntity.TABLE_NAME} s JOIN ${TerritoryStreetEntity.TABLE_NAME} ts ON s.streetId = ts.streetsId
        JOIN ${GeoLocalityView.VIEW_NAME} l ON l.localityId = s.localitiesId
        JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = ts.territoriesId AND ct.endUsingDate IS NULL
    WHERE NOT EXISTS (SELECT ds.* FROM ${GeoDistrictStreetEntity.TABLE_NAME} ds WHERE ds.streetsId = s.streetId)
    -- Houses streets
    UNION ALL
    SELECT $TDT_MICRO_DISTRICT_VAL AS territoryLocationType, ct.congregationsId AS congregationId,
        ifnull(h.isPrivateSector, s.isPrivateSector) AS isPrivateSector, md.microdistrictId AS locationId, md.microdistrictName AS locationName
    FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.streetsId
        JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId
        JOIN ${GeoMicrodistrictView.VIEW_NAME} md ON md.microdistrictId = ifnull(h.microdistrictsId, ds.microdistrictsId)
        JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = h.territoriesId AND ct.endUsingDate IS NULL
    UNION ALL
    SELECT $TDT_LOCALITY_DISTRICT_VAL AS territoryLocationType, ct.congregationsId AS congregationId,
        ifnull(h.isPrivateSector, s.isPrivateSector) AS isPrivateSector, ld.localityDistrictsId AS locationId, ld.locationName AS locationName
    FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.streetsId
        JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.streetsId = s.streetId
        JOIN ${GeoLocalityDistrictView.VIEW_NAME} ld ON ld.localityDistrictId = ifnull(h.localityDistrictsId, ds.localityDistrictsId)
        JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = h.territoriesId AND ct.endUsingDate IS NULL
    UNION ALL
    SELECT $TDT_LOCALITY_VAL AS territoryLocationType, ct.congregationsId AS congregationId,
        ifnull(h.isPrivateSector, s.isPrivateSector) AS isPrivateSector, l.localityId AS locationId, l.localityName AS locationName
    FROM ${HouseEntity.TABLE_NAME} h JOIN ${GeoStreetEntity.TABLE_NAME} s ON s.streetId = h.streetsId
        JOIN ${GeoLocalityView.VIEW_NAME} l ON l.localityId = s.localitiesId
        JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct ON ct.territoriesId = h.territoriesId AND ct.endUsingDate IS NULL
    WHERE NOT EXISTS (SELECT ds.* FROM ${GeoDistrictStreetEntity.TABLE_NAME} ds WHERE ds.streetsId = s.streetId)) td
GROUP BY td.territoryLocationType, td.congregationId, td.isPrivateSector, td.locationId, td.locationName
*/