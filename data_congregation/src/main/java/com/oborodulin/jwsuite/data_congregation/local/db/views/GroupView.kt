package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity

@DatabaseView(
    viewName = GroupView.VIEW_NAME,
    value = """
SELECT g.*, 
    c.congregationId AS ${GroupEntity.PX_CONGREGATION}congregationId, c.congregationNum AS ${GroupEntity.PX_CONGREGATION}congregationNum, 
        c.congregationName AS ${GroupEntity.PX_CONGREGATION}congregationName, c.territoryMark AS ${GroupEntity.PX_CONGREGATION}territoryMark,
        c.isFavorite AS ${GroupEntity.PX_CONGREGATION}isFavorite, c.lastVisitDate AS ${GroupEntity.PX_CONGREGATION}lastVisitDate, 
        c.cLocalitiesId AS ${GroupEntity.PX_CONGREGATION}cLocalitiesId,
    c.${CongregationEntity.PX_LOCALITY}localityId AS ${GroupEntity.PX_LOCALITY}localityId, c.${CongregationEntity.PX_LOCALITY}localityCode AS ${GroupEntity.PX_LOCALITY}localityCode,
        c.${CongregationEntity.PX_LOCALITY}localityType AS ${GroupEntity.PX_LOCALITY}localityType, 
        c.${CongregationEntity.PX_LOCALITY}localityGeocode AS ${GroupEntity.PX_LOCALITY}localityGeocode, c.${CongregationEntity.PX_LOCALITY}localityOsmId AS ${GroupEntity.PX_LOCALITY}localityOsmId, 
        c.${CongregationEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}latitude AS ${GroupEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}latitude,
        c.${CongregationEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}longitude AS ${GroupEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}longitude, 
        c.${CongregationEntity.PX_LOCALITY}lRegionDistrictsId AS ${GroupEntity.PX_LOCALITY}lRegionDistrictsId, c.${CongregationEntity.PX_LOCALITY}lRegionsId AS ${GroupEntity.PX_LOCALITY}lRegionsId,
        c.${CongregationEntity.PX_LOCALITY}localityTlId AS ${GroupEntity.PX_LOCALITY}localityTlId, c.${CongregationEntity.PX_LOCALITY}localityLocCode AS ${GroupEntity.PX_LOCALITY}localityLocCode,
        c.${CongregationEntity.PX_LOCALITY}localityShortName AS ${GroupEntity.PX_LOCALITY}localityShortName, 
        c.${CongregationEntity.PX_LOCALITY}localityName AS ${GroupEntity.PX_LOCALITY}localityName, c.${CongregationEntity.PX_LOCALITY}localitiesId AS ${GroupEntity.PX_LOCALITY}localitiesId
FROM ${GroupEntity.TABLE_NAME} g JOIN ${CongregationView.VIEW_NAME} c ON c.congregationId = g.gCongregationsId
"""
)
class GroupView(
    @Embedded val group: GroupEntity,
    @Embedded(prefix = GroupEntity.PX_CONGREGATION) val congregation: CongregationView,
) {
    companion object {
        const val VIEW_NAME = "groups_view"
    }
}