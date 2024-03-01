package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView

@DatabaseView(
    viewName = MemberView.VIEW_NAME,
    value = """
SELECT c.congregationId AS ${MemberEntity.PX_CONGREGATION}congregationId, c.congregationNum AS ${MemberEntity.PX_CONGREGATION}congregationNum, 
            c.congregationName AS ${MemberEntity.PX_CONGREGATION}congregationName, c.territoryMark AS ${MemberEntity.PX_CONGREGATION}territoryMark, 
            c.isFavorite AS ${MemberEntity.PX_CONGREGATION}isFavorite, c.lastVisitDate AS ${MemberEntity.PX_CONGREGATION}lastVisitDate, 
            c.cLocalitiesId AS ${MemberEntity.PX_CONGREGATION}cLocalitiesId, 
        lv.localityId AS ${MemberEntity.PX_LOCALITY}localityId, lv.localityCode AS ${MemberEntity.PX_LOCALITY}localityCode, lv.localityType AS ${MemberEntity.PX_LOCALITY}localityType, 
            lv.localityGeocode AS ${MemberEntity.PX_LOCALITY}localityGeocode, lv.localityOsmId AS ${MemberEntity.PX_LOCALITY}localityOsmId, 
            lv.${GeoLocalityEntity.PREFIX}latitude AS ${MemberEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}latitude,
            lv.${GeoLocalityEntity.PREFIX}longitude AS ${MemberEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}longitude, 
            lv.lRegionDistrictsId AS ${MemberEntity.PX_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${MemberEntity.PX_LOCALITY}lRegionsId,
            lv.localityTlId AS ${MemberEntity.PX_LOCALITY}localityTlId, lv.localityLocCode AS ${MemberEntity.PX_LOCALITY}localityLocCode, lv.localityShortName AS ${MemberEntity.PX_LOCALITY}localityShortName, 
            lv.localityName AS ${MemberEntity.PX_LOCALITY}localityName, lv.localitiesId AS ${MemberEntity.PX_LOCALITY}localitiesId,
        gv.*, m.*, mlcv.*, mlmv.*
FROM ${MemberEntity.TABLE_NAME} m LEFT JOIN ${GroupView.VIEW_NAME} gv ON gv.groupId = m.mGroupsId
    LEFT JOIN ${MemberLastCongregationView.VIEW_NAME} mlcv ON mlcv.mcMembersId = m.memberId AND mlcv.mcCongregationsId = ifnull(gv.gCongregationsId, mlcv.mcCongregationsId)  
    LEFT JOIN ${CongregationEntity.TABLE_NAME} c ON c.congregationId = mlcv.mcCongregationsId
        LEFT JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = c.cLocalitiesId 
    JOIN ${MemberLastMovementView.VIEW_NAME} mlmv ON mlmv.mMembersId = m.memberId
"""
)
class MemberView(
    @Embedded(prefix = MemberEntity.PX_CONGREGATION) val memberCongregation: CongregationEntity?,
    //@Embedded(prefix = MemberEntity.PX_REGION) val region: GeoRegionView,
    //@Embedded(prefix = MemberEntity.PX_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = MemberEntity.PX_LOCALITY) val locality: LocalityView?,
    @Embedded val group: GroupView? = null,
    @Embedded val member: MemberEntity,
    @Embedded val lastCongregation: MemberLastCongregationView?,
    @Embedded val lastMovement: MemberLastMovementView
) {
    companion object {
        const val VIEW_NAME = "members_view"
    }
}
/*
        rv.regionId AS ${MemberEntity.PX_REGION}regionId, rv.regionCode AS ${MemberEntity.PX_REGION}regionCode, 
            rv.regionTlId AS ${MemberEntity.PX_REGION}regionTlId, rv.regionLocCode AS ${MemberEntity.PX_REGION}regionLocCode, rv.regionTlCode AS ${MemberEntity.PX_REGION}regionTlCode, 
            rv.regionName AS ${MemberEntity.PX_REGION}regionName, rv.regionsId AS ${MemberEntity.PX_REGION}regionsId,
        rdv.regionDistrictId AS ${MemberEntity.PX_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName AS ${MemberEntity.PX_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId AS ${MemberEntity.PX_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${MemberEntity.PX_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode AS ${MemberEntity.PX_REGION_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${MemberEntity.PX_REGION_DISTRICT}regDistrictTlShortName, 
            rdv.regDistrictName AS ${MemberEntity.PX_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId AS ${MemberEntity.PX_REGION_DISTRICT}regionDistrictsId,

        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rv.regionLocCode = lv.localityLocCode
*/