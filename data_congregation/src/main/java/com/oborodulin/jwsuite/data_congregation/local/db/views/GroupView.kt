package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_CONGREGATION_LOCALITY
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_CONGREGATION_REGION
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_CONGREGATION_REGION_DISTRICT
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_GROUP_CONGREGATION
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_GROUP_LOCALITY
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_GROUP_REGION
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_GROUP_REGION_DISTRICT

@DatabaseView(
    viewName = GroupView.VIEW_NAME,
    value = """
SELECT g.*, 
    c.congregationId AS ${PX_GROUP_CONGREGATION}congregationId, c.congregationNum AS ${PX_GROUP_CONGREGATION}congregationNum, 
        c.congregationName AS ${PX_GROUP_CONGREGATION}congregationName, c.territoryMark AS ${PX_GROUP_CONGREGATION}territoryMark,
        c.isFavorite AS ${PX_GROUP_CONGREGATION}isFavorite, c.cLocalitiesId AS ${PX_GROUP_CONGREGATION}cLocalitiesId,
    c.${PX_CONGREGATION_REGION}regionId AS ${PX_GROUP_REGION}regionId, c.${PX_CONGREGATION_REGION}regionCode AS ${PX_GROUP_REGION}regionCode, 
        c.${PX_CONGREGATION_REGION}regionTlId AS ${PX_GROUP_REGION}regionTlId, c.${PX_CONGREGATION_REGION}regionLocCode AS ${PX_GROUP_REGION}regionLocCode, 
        c.${PX_CONGREGATION_REGION}regionName AS ${PX_GROUP_REGION}regionName, c.${PX_CONGREGATION_REGION}regionsId AS ${PX_GROUP_REGION}regionsId,
    c.${PX_CONGREGATION_REGION_DISTRICT}regionDistrictId AS ${PX_GROUP_REGION_DISTRICT}regionDistrictId, c.${PX_CONGREGATION_REGION_DISTRICT}regDistrictShortName AS ${PX_GROUP_REGION_DISTRICT}regDistrictShortName, 
        c.${PX_CONGREGATION_REGION_DISTRICT}rRegionsId AS ${PX_GROUP_REGION_DISTRICT}rRegionsId, c.${PX_CONGREGATION_REGION_DISTRICT}regionDistrictTlId AS ${PX_GROUP_REGION_DISTRICT}regionDistrictTlId,
        c.${PX_CONGREGATION_REGION_DISTRICT}regDistrictLocCode AS ${PX_GROUP_REGION_DISTRICT}regDistrictLocCode, c.${PX_CONGREGATION_REGION_DISTRICT}regDistrictName AS ${PX_GROUP_REGION_DISTRICT}regDistrictName,
        c.${PX_CONGREGATION_REGION_DISTRICT}regionDistrictsId AS ${PX_GROUP_REGION_DISTRICT}regionDistrictsId,
    c.${PX_CONGREGATION_LOCALITY}localityId AS ${PX_GROUP_LOCALITY}localityId, c.${PX_CONGREGATION_LOCALITY}localityCode AS ${PX_GROUP_LOCALITY}localityCode,
        c.${PX_CONGREGATION_LOCALITY}localityType AS ${PX_GROUP_LOCALITY}localityType, c.${PX_CONGREGATION_LOCALITY}lRegionDistrictsId AS ${PX_GROUP_LOCALITY}lRegionDistrictsId,
        c.${PX_CONGREGATION_LOCALITY}lRegionsId AS ${PX_GROUP_LOCALITY}lRegionsId, c.${PX_CONGREGATION_LOCALITY}localityTlId AS ${PX_GROUP_LOCALITY}localityTlId,
        c.${PX_CONGREGATION_LOCALITY}localityLocCode AS ${PX_GROUP_LOCALITY}localityLocCode, c.${PX_CONGREGATION_LOCALITY}localityShortName AS ${PX_GROUP_LOCALITY}localityShortName, 
        c.${PX_CONGREGATION_LOCALITY}localityName AS ${PX_GROUP_LOCALITY}localityName, c.${PX_CONGREGATION_LOCALITY}localitiesId AS ${PX_GROUP_LOCALITY}localitiesId
FROM ${GroupEntity.TABLE_NAME} g JOIN ${CongregationView.VIEW_NAME} c ON c.congregationId = g.gCongregationsId
"""
)
class GroupView(
    @Embedded val group: GroupEntity,
    @Embedded(prefix = PX_GROUP_CONGREGATION) val congregation: CongregationView,
) {
    companion object {
        const val VIEW_NAME = "groups_view"
    }
}