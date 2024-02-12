package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_MEMBER_CONGREGATION
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_MEMBER_LOCALITY
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_MEMBER_REGION
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_MEMBER_REGION_DISTRICT
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView

@DatabaseView(
    viewName = MemberView.VIEW_NAME,
    value = """
SELECT c.congregationId AS ${PX_MEMBER_CONGREGATION}congregationId, c.congregationNum AS ${PX_MEMBER_CONGREGATION}congregationNum, 
            c.congregationName AS ${PX_MEMBER_CONGREGATION}congregationName, c.territoryMark AS ${PX_MEMBER_CONGREGATION}territoryMark, 
            c.isFavorite AS ${PX_MEMBER_CONGREGATION}isFavorite, c.lastVisitDate AS ${PX_MEMBER_CONGREGATION}lastVisitDate, 
            c.cLocalitiesId AS ${PX_MEMBER_CONGREGATION}cLocalitiesId, 
        rv.regionId AS ${PX_MEMBER_REGION}regionId, rv.regionCode AS ${PX_MEMBER_REGION}regionCode, 
            rv.regionTlId AS ${PX_MEMBER_REGION}regionTlId, rv.regionLocCode AS ${PX_MEMBER_REGION}regionLocCode, rv.regionTlCode AS ${PX_MEMBER_REGION}regionTlCode, 
            rv.regionName AS ${PX_MEMBER_REGION}regionName, rv.regionsId AS ${PX_MEMBER_REGION}regionsId,
        rdv.regionDistrictId AS ${PX_MEMBER_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName AS ${PX_MEMBER_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId AS ${PX_MEMBER_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${PX_MEMBER_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode AS ${PX_MEMBER_REGION_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${PX_MEMBER_REGION_DISTRICT}regDistrictTlShortName, 
            rdv.regDistrictName AS ${PX_MEMBER_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId AS ${PX_MEMBER_REGION_DISTRICT}regionDistrictsId,
        lv.localityId AS ${PX_MEMBER_LOCALITY}localityId, lv.localityCode AS ${PX_MEMBER_LOCALITY}localityCode, lv.localityType AS ${PX_MEMBER_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${PX_MEMBER_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${PX_MEMBER_LOCALITY}lRegionsId,
            lv.localityTlId AS ${PX_MEMBER_LOCALITY}localityTlId, lv.localityLocCode AS ${PX_MEMBER_LOCALITY}localityLocCode, lv.localityShortName AS ${PX_MEMBER_LOCALITY}localityShortName, 
            lv.localityName AS ${PX_MEMBER_LOCALITY}localityName, lv.localitiesId AS ${PX_MEMBER_LOCALITY}localitiesId,
        gv.*, m.*, mlcv.*, mlmv.* FROM ${MemberEntity.TABLE_NAME} m 
    LEFT JOIN ${GroupView.VIEW_NAME} gv ON gv.groupId = m.mGroupsId
    JOIN ${MemberLastCongregationView.VIEW_NAME} mlcv ON mlcv.mcMembersId = m.memberId AND mlcv.mcCongregationsId = ifnull(gv.gCongregationsId, mlcv.mcCongregationsId)  
    JOIN ${CongregationEntity.TABLE_NAME} c ON c.congregationId = mlcv.mcCongregationsId
        JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = c.cLocalitiesId 
        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rv.regionLocCode = lv.localityLocCode
    JOIN ${MemberLastMovementView.VIEW_NAME} mlmv ON mlmv.mMembersId = m.memberId
"""
)
class MemberView(
    @Embedded(prefix = PX_MEMBER_CONGREGATION) val memberCongregation: CongregationEntity,
    @Embedded(prefix = PX_MEMBER_REGION) val region: GeoRegionView,
    @Embedded(prefix = PX_MEMBER_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = PX_MEMBER_LOCALITY) val locality: LocalityView,
    @Embedded val group: GroupView? = null,
    @Embedded val member: MemberEntity,
    @Embedded val lastCongregation: MemberLastCongregationView,
    @Embedded val lastMovement: MemberLastMovementView
) {
    companion object {
        const val VIEW_NAME = "members_view"
    }
}