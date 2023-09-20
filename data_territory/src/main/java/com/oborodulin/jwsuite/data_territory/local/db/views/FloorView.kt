package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_LD_LOCALITY
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_LD_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_LD_REGION
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_LD_REGION_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_MICRODISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_M_LOCALITY
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_M_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_M_REGION
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_HOUSE_M_REGION_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LOCALITY

@DatabaseView(
    viewName = FloorView.VIEW_NAME,
    value = """
SELECT sv.*,
        rvl.regionId AS ${PX_HOUSE_LD_REGION}regionId, rvl.regionCode AS ${PX_HOUSE_LD_REGION}regionCode, 
            rvl.regionTlId AS ${PX_HOUSE_LD_REGION}regionTlId, rvl.regionLocCode AS ${PX_HOUSE_LD_REGION}regionLocCode, rvl.regionTlCode AS ${PX_HOUSE_LD_REGION}regionTlCode, 
            rvl.regionName AS ${PX_HOUSE_LD_REGION}regionName, rvl.regionsId AS ${PX_HOUSE_LD_REGION}regionsId, 
        rdvl.regionDistrictId AS ${PX_HOUSE_LD_REGION_DISTRICT}regionDistrictId, rdvl.regDistrictShortName  AS ${PX_HOUSE_LD_REGION_DISTRICT}regDistrictShortName, 
            rdvl.rRegionsId  AS ${PX_HOUSE_LD_REGION_DISTRICT}rRegionsId,
            rdvl.regionDistrictTlId  AS ${PX_HOUSE_LD_REGION_DISTRICT}regionDistrictTlId, rdvl.regDistrictLocCode  AS ${PX_HOUSE_LD_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName  AS ${PX_HOUSE_LD_REGION_DISTRICT}regDistrictTlShortName,
            rdvl.regDistrictName  AS ${PX_HOUSE_LD_REGION_DISTRICT}regDistrictName, rdvl.regionDistrictsId  AS ${PX_HOUSE_LD_REGION_DISTRICT}regionDistrictsId, 
        lvl.localityId AS ${PX_HOUSE_LD_LOCALITY}localityId, lvl.localityCode AS ${PX_HOUSE_LD_LOCALITY}localityCode, lvl.localityType AS ${PX_HOUSE_LD_LOCALITY}localityType, 
            lvl.lRegionDistrictsId AS ${PX_HOUSE_LD_LOCALITY}lRegionDistrictsId, lvl.lRegionsId AS ${PX_HOUSE_LD_LOCALITY}lRegionsId,
            lvl.localityTlId AS ${PX_HOUSE_LD_LOCALITY}localityTlId, lvl.localityLocCode AS ${PX_HOUSE_LD_LOCALITY}localityLocCode, lvl.localityShortName AS ${PX_HOUSE_LD_LOCALITY}localityShortName, 
            lvl.localityName AS ${PX_HOUSE_LD_LOCALITY}localityName, lvl.localitiesId AS ${PX_HOUSE_LD_LOCALITY}localitiesId, 
        ldvl.localityDistrictId AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}localityDistrictId, ldvl.locDistrictShortName AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvl.ldLocalitiesId AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}ldLocalitiesId, ldvl.localityDistrictTlId AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvl.locDistrictLocCode AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}locDistrictLocCode, ldvl.locDistrictName AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}locDistrictName, 
            ldvl.localityDistrictsId AS ${PX_HOUSE_LD_LOCALITY_DISTRICT}localityDistrictsId, 

        rvm.regionId AS ${PX_HOUSE_M_REGION}regionId, rvm.regionCode AS ${PX_HOUSE_M_REGION}regionCode, 
            rvm.regionTlId AS ${PX_HOUSE_M_REGION}regionTlId, rvm.regionLocCode AS ${PX_HOUSE_M_REGION}regionLocCode, rvm.regionTlCode AS ${PX_HOUSE_M_REGION}regionTlCode, 
            rvm.regionName AS ${PX_HOUSE_M_REGION}regionName, rvm.regionsId AS ${PX_HOUSE_M_REGION}regionsId, 
        rdvm.regionDistrictId AS ${PX_HOUSE_M_REGION_DISTRICT}regionDistrictId, rdvm.regDistrictShortName  AS ${PX_HOUSE_M_REGION_DISTRICT}regDistrictShortName, 
            rdvm.rRegionsId AS ${PX_HOUSE_M_REGION_DISTRICT}rRegionsId,
            rdvm.regionDistrictTlId AS ${PX_HOUSE_M_REGION_DISTRICT}regionDistrictTlId, rdvm.regDistrictLocCode  AS ${PX_HOUSE_M_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName  AS ${PX_HOUSE_M_REGION_DISTRICT}regDistrictTlShortName,
            rdvm.regDistrictName AS ${PX_HOUSE_M_REGION_DISTRICT}regDistrictName, rdvm.regionDistrictsId  AS ${PX_HOUSE_M_REGION_DISTRICT}regionDistrictsId, 
        lvm.localityId AS ${PX_HOUSE_M_LOCALITY}localityId, lvm.localityCode AS ${PX_HOUSE_M_LOCALITY}localityCode, lvm.localityType AS ${PX_HOUSE_M_LOCALITY}localityType, 
            lvm.lRegionDistrictsId AS ${PX_HOUSE_M_LOCALITY}lRegionDistrictsId, lvm.lRegionsId AS ${PX_HOUSE_M_LOCALITY}lRegionsId,
            lvm.localityTlId AS ${PX_HOUSE_M_LOCALITY}localityTlId, lvm.localityLocCode AS ${PX_HOUSE_M_LOCALITY}localityLocCode, lvm.localityShortName AS ${PX_HOUSE_M_LOCALITY}localityShortName, 
            lvm.localityName AS ${PX_HOUSE_M_LOCALITY}localityName, lvm.localitiesId AS ${PX_HOUSE_M_LOCALITY}localitiesId, 
        ldvm.localityDistrictId AS ${PX_HOUSE_M_LOCALITY_DISTRICT}localityDistrictId, ldvm.locDistrictShortName AS ${PX_HOUSE_M_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvm.ldLocalitiesId AS ${PX_HOUSE_M_LOCALITY_DISTRICT}ldLocalitiesId, ldvm.localityDistrictTlId AS ${PX_HOUSE_M_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvm.locDistrictLocCode AS ${PX_HOUSE_M_LOCALITY_DISTRICT}locDistrictLocCode, ldvm.locDistrictName AS ${PX_HOUSE_M_LOCALITY_DISTRICT}locDistrictName, 
            ldvm.localityDistrictsId AS ${PX_HOUSE_M_LOCALITY_DISTRICT}localityDistrictsId, 
        mdv.microdistrictId AS ${PX_HOUSE_MICRODISTRICT}microdistrictId, mdv.microdistrictType AS ${PX_HOUSE_MICRODISTRICT}microdistrictType, 
            mdv.microdistrictShortName AS ${PX_HOUSE_MICRODISTRICT}microdistrictShortName, mdv.mLocalityDistrictsId AS ${PX_HOUSE_MICRODISTRICT}mLocalityDistrictsId, 
            mdv.mLocalitiesId AS ${PX_HOUSE_MICRODISTRICT}mLocalitiesId, mdv.microdistrictTlId AS ${PX_HOUSE_MICRODISTRICT}microdistrictTlId, 
            mdv.microdistrictLocCode AS ${PX_HOUSE_MICRODISTRICT}microdistrictLocCode, mdv.microdistrictName AS ${PX_HOUSE_MICRODISTRICT}microdistrictName, 
            mdv.microdistrictsId AS ${PX_HOUSE_MICRODISTRICT}microdistrictsId,
        tv.*, h.*, e.*, f.*
FROM ${FloorEntity.TABLE_NAME} f JOIN ${HouseEntity.TABLE_NAME} h ON h.houseId = f.fHousesId 
    JOIN ${GeoStreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.eHousesId = h.houseId AND e.entranceId = f.fEntrancesId
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvl ON ldvl.localityDistrictId = h.hLocalityDistrictsId AND ldvl.locDistrictLocCode = sv.streetLocCode
    LEFT JOIN ${LocalityView.VIEW_NAME} lvl ON lvl.localityId = ldvl.ldLocalitiesId AND lvl.localityLocCode = sv.streetLocCode 
    LEFT JOIN ${GeoRegionView.VIEW_NAME} rvl ON rvl.regionId = lvl.lRegionsId AND rvl.regionLocCode = sv.streetLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvl ON rdvl.regionDistrictId = lvl.lRegionDistrictsId AND rdvl.regDistrictLocCode = sv.streetLocCode
    
    LEFT JOIN ${MicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = h.hMicrodistrictsId AND mdv.microdistrictLocCode = sv.streetLocCode
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvm ON ldvm.localityDistrictId = mdv.mLocalityDistrictsId AND ldvm.locDistrictLocCode = sv.streetLocCode 
    LEFT JOIN ${LocalityView.VIEW_NAME} lvm ON lvm.localityId = ldvm.ldLocalitiesId AND lvm.localityLocCode = sv.streetLocCode
    LEFT JOIN ${GeoRegionView.VIEW_NAME} rvm ON rvm.regionId = lvm.lRegionsId AND rvm.regionLocCode = sv.streetLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvm ON rdvm.regionDistrictId = lvm.lRegionDistrictsId AND rdvm.regDistrictLocCode = sv.streetLocCode

    LEFT JOIN ${TerritoryView.VIEW_NAME} tv ON tv.territoryId = f.fTerritoriesId AND tv.${PX_TERRITORY_LOCALITY}localityLocCode  = sv.streetLocCode
"""
)
class FloorView(
    @Embedded val street: GeoStreetView,

    @Embedded(prefix = PX_HOUSE_LD_REGION) val fldRegion: GeoRegionView?,
    @Embedded(prefix = PX_HOUSE_LD_REGION_DISTRICT) val fldDistrict: RegionDistrictView?,
    @Embedded(prefix = PX_HOUSE_LD_LOCALITY) val fldLocality: LocalityView?,
    @Embedded(prefix = PX_HOUSE_LD_LOCALITY_DISTRICT) val fLocalityDistrict: LocalityDistrictView?,

    @Embedded(prefix = PX_HOUSE_M_REGION) val fmRegion: GeoRegionView?,
    @Embedded(prefix = PX_HOUSE_M_REGION_DISTRICT) val fmDistrict: RegionDistrictView?,
    @Embedded(prefix = PX_HOUSE_M_LOCALITY) val fmLocality: LocalityView?,
    @Embedded(prefix = PX_HOUSE_M_LOCALITY_DISTRICT) val fmLocalityDistrict: LocalityDistrictView?,
    @Embedded(prefix = PX_HOUSE_MICRODISTRICT) val fMicrodistrict: MicrodistrictView?,

    @Embedded val territory: TerritoryView?,
    @Embedded val house: HouseEntity,
    @Embedded val entrance: EntranceEntity?,
    @Embedded val floor: FloorEntity
) {
    companion object {
        const val VIEW_NAME = "floors_view"
    }
}