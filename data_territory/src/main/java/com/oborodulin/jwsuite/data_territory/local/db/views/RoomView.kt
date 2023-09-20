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
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.util.Constants
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LOCALITY

@DatabaseView(
    viewName = RoomView.VIEW_NAME,
    value = """
SELECT sv.*,
        rvl.regionId AS ${Constants.PX_HOUSE_LD_REGION}regionId, rvl.regionCode AS ${Constants.PX_HOUSE_LD_REGION}regionCode, 
            rvl.regionTlId AS ${Constants.PX_HOUSE_LD_REGION}regionTlId, rvl.regionLocCode AS ${Constants.PX_HOUSE_LD_REGION}regionLocCode, rvl.regionTlCode AS ${Constants.PX_HOUSE_LD_REGION}regionTlCode, 
            rvl.regionName AS ${Constants.PX_HOUSE_LD_REGION}regionName, rvl.regionsId AS ${Constants.PX_HOUSE_LD_REGION}regionsId, 
        rdvl.regionDistrictId AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regionDistrictId, rdvl.regDistrictShortName  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regDistrictShortName, 
            rdvl.rRegionsId  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}rRegionsId,
            rdvl.regionDistrictTlId  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regionDistrictTlId, rdvl.regDistrictLocCode  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regDistrictTlShortName,
            rdvl.regDistrictName  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regDistrictName, rdvl.regionDistrictsId  AS ${Constants.PX_HOUSE_LD_REGION_DISTRICT}regionDistrictsId, 
        lvl.localityId AS ${Constants.PX_HOUSE_LD_LOCALITY}localityId, lvl.localityCode AS ${Constants.PX_HOUSE_LD_LOCALITY}localityCode, lvl.localityType AS ${Constants.PX_HOUSE_LD_LOCALITY}localityType, 
            lvl.lRegionDistrictsId AS ${Constants.PX_HOUSE_LD_LOCALITY}lRegionDistrictsId, lvl.lRegionsId AS ${Constants.PX_HOUSE_LD_LOCALITY}lRegionsId,
            lvl.localityTlId AS ${Constants.PX_HOUSE_LD_LOCALITY}localityTlId, lvl.localityLocCode AS ${Constants.PX_HOUSE_LD_LOCALITY}localityLocCode, lvl.localityShortName AS ${Constants.PX_HOUSE_LD_LOCALITY}localityShortName, 
            lvl.localityName AS ${Constants.PX_HOUSE_LD_LOCALITY}localityName, lvl.localitiesId AS ${Constants.PX_HOUSE_LD_LOCALITY}localitiesId, 
        ldvl.localityDistrictId AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}localityDistrictId, ldvl.locDistrictShortName AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvl.ldLocalitiesId AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}ldLocalitiesId, ldvl.localityDistrictTlId AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvl.locDistrictLocCode AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}locDistrictLocCode, ldvl.locDistrictName AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}locDistrictName, 
            ldvl.localityDistrictsId AS ${Constants.PX_HOUSE_LD_LOCALITY_DISTRICT}localityDistrictsId, 

        rvm.regionId AS ${Constants.PX_HOUSE_M_REGION}regionId, rvm.regionCode AS ${Constants.PX_HOUSE_M_REGION}regionCode, 
            rvm.regionTlId AS ${Constants.PX_HOUSE_M_REGION}regionTlId, rvm.regionLocCode AS ${Constants.PX_HOUSE_M_REGION}regionLocCode, rvm.regionTlCode AS ${Constants.PX_HOUSE_M_REGION}regionTlCode, 
            rvm.regionName AS ${Constants.PX_HOUSE_M_REGION}regionName, rvm.regionsId AS ${Constants.PX_HOUSE_M_REGION}regionsId, 
        rdvm.regionDistrictId AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regionDistrictId, rdvm.regDistrictShortName  AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regDistrictShortName, 
            rdvm.rRegionsId AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}rRegionsId,
            rdvm.regionDistrictTlId AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regionDistrictTlId, rdvm.regDistrictLocCode  AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName  AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regDistrictTlShortName,
            rdvm.regDistrictName AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regDistrictName, rdvm.regionDistrictsId  AS ${Constants.PX_HOUSE_M_REGION_DISTRICT}regionDistrictsId, 
        lvm.localityId AS ${Constants.PX_HOUSE_M_LOCALITY}localityId, lvm.localityCode AS ${Constants.PX_HOUSE_M_LOCALITY}localityCode, lvm.localityType AS ${Constants.PX_HOUSE_M_LOCALITY}localityType, 
            lvm.lRegionDistrictsId AS ${Constants.PX_HOUSE_M_LOCALITY}lRegionDistrictsId, lvm.lRegionsId AS ${Constants.PX_HOUSE_M_LOCALITY}lRegionsId,
            lvm.localityTlId AS ${Constants.PX_HOUSE_M_LOCALITY}localityTlId, lvm.localityLocCode AS ${Constants.PX_HOUSE_M_LOCALITY}localityLocCode, lvm.localityShortName AS ${Constants.PX_HOUSE_M_LOCALITY}localityShortName, 
            lvm.localityName AS ${Constants.PX_HOUSE_M_LOCALITY}localityName, lvm.localitiesId AS ${Constants.PX_HOUSE_M_LOCALITY}localitiesId, 
        ldvm.localityDistrictId AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}localityDistrictId, ldvm.locDistrictShortName AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvm.ldLocalitiesId AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}ldLocalitiesId, ldvm.localityDistrictTlId AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvm.locDistrictLocCode AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}locDistrictLocCode, ldvm.locDistrictName AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}locDistrictName, 
            ldvm.localityDistrictsId AS ${Constants.PX_HOUSE_M_LOCALITY_DISTRICT}localityDistrictsId, 
        mdv.microdistrictId AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictId, mdv.microdistrictType AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictType, 
            mdv.microdistrictShortName AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictShortName, mdv.mLocalityDistrictsId AS ${Constants.PX_HOUSE_MICRODISTRICT}mLocalityDistrictsId, 
            mdv.mLocalitiesId AS ${Constants.PX_HOUSE_MICRODISTRICT}mLocalitiesId, mdv.microdistrictTlId AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictTlId, 
            mdv.microdistrictLocCode AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictLocCode, mdv.microdistrictName AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictName, 
            mdv.microdistrictsId AS ${Constants.PX_HOUSE_MICRODISTRICT}microdistrictsId,
        tv.*, h.*, e.*, f.*, r.*
FROM ${RoomEntity.TABLE_NAME} r JOIN ${HouseView.VIEW_NAME} h ON h.houseId = r.rHousesId
    JOIN ${GeoStreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.eHousesId = h.houseId AND e.entranceId = r.rEntrancesId  
    LEFT JOIN ${FloorEntity.TABLE_NAME} f ON f.fHousesId = h.houseId AND f.fEntrancesId = e.entranceId AND f.floorId = r.rFloorsId  
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvl ON ldvl.localityDistrictId = h.hLocalityDistrictsId AND ldvl.locDistrictLocCode = sv.streetLocCode
    LEFT JOIN ${LocalityView.VIEW_NAME} lvl ON lvl.localityId = ldvl.ldLocalitiesId AND lvl.localityLocCode = sv.streetLocCode 
    LEFT JOIN ${GeoRegionView.VIEW_NAME} rvl ON rvl.regionId = lvl.lRegionsId AND rvl.regionLocCode = sv.streetLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvl ON rdvl.regionDistrictId = lvl.lRegionDistrictsId AND rdvl.regDistrictLocCode = sv.streetLocCode
    
    LEFT JOIN ${MicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = h.hMicrodistrictsId AND mdv.microdistrictLocCode = sv.streetLocCode
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvm ON ldvm.localityDistrictId = mdv.mLocalityDistrictsId AND ldvm.locDistrictLocCode = sv.streetLocCode 
    LEFT JOIN ${LocalityView.VIEW_NAME} lvm ON lvm.localityId = ldvm.ldLocalitiesId AND lvm.localityLocCode = sv.streetLocCode
    LEFT JOIN ${GeoRegionView.VIEW_NAME} rvm ON rvm.regionId = lvm.lRegionsId AND rvm.regionLocCode = sv.streetLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvm ON rdvm.regionDistrictId = lvm.lRegionDistrictsId AND rdvm.regDistrictLocCode = sv.streetLocCode

    LEFT JOIN ${TerritoryView.VIEW_NAME} tv ON tv.territoryId = r.rTerritoriesId AND tv.${PX_TERRITORY_LOCALITY}localityLocCode  = h.streetLocCode
"""
)
class RoomView(
    @Embedded val street: GeoStreetView,

    @Embedded(prefix = Constants.PX_HOUSE_LD_REGION) val rldRegion: GeoRegionView?,
    @Embedded(prefix = Constants.PX_HOUSE_LD_REGION_DISTRICT) val rldDistrict: RegionDistrictView?,
    @Embedded(prefix = Constants.PX_HOUSE_LD_LOCALITY) val rldLocality: LocalityView?,
    @Embedded(prefix = Constants.PX_HOUSE_LD_LOCALITY_DISTRICT) val rLocalityDistrict: LocalityDistrictView?,

    @Embedded(prefix = Constants.PX_HOUSE_M_REGION) val rmRegion: GeoRegionView?,
    @Embedded(prefix = Constants.PX_HOUSE_M_REGION_DISTRICT) val rmDistrict: RegionDistrictView?,
    @Embedded(prefix = Constants.PX_HOUSE_M_LOCALITY) val rmLocality: LocalityView?,
    @Embedded(prefix = Constants.PX_HOUSE_M_LOCALITY_DISTRICT) val rmLocalityDistrict: LocalityDistrictView?,
    @Embedded(prefix = Constants.PX_HOUSE_MICRODISTRICT) val rMicrodistrict: MicrodistrictView?,

    @Embedded val territory: TerritoryView?,
    @Embedded val house: HouseEntity,
    @Embedded val entrance: EntranceEntity?,
    @Embedded val floor: FloorEntity?,
    @Embedded val room: RoomEntity
) {
    companion object {
        const val VIEW_NAME = "rooms_view"
    }
}