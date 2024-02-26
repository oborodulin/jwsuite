package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity

@DatabaseView(
    viewName = RoomView.VIEW_NAME,
    value = """
SELECT sv.*,
        lv.localityId AS ${HouseEntity.PX_LOCALITY}localityId, lv.localityCode AS ${HouseEntity.PX_LOCALITY}localityCode, lv.localityType AS ${HouseEntity.PX_LOCALITY}localityType, 
            lv.localityGeocode AS ${HouseEntity.PX_LOCALITY}localityGeocode, lv.localityOsmId AS ${HouseEntity.PX_LOCALITY}localityOsmId, 
            lv.${GeoLocalityEntity.PREFIX}latitude AS ${HouseEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}latitude,
            lv.${GeoLocalityEntity.PREFIX}longitude AS ${HouseEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}longitude, 
            lv.lRegionDistrictsId AS ${HouseEntity.PX_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${HouseEntity.PX_LOCALITY}lRegionsId,
            lv.localityTlId AS ${HouseEntity.PX_LOCALITY}localityTlId, lv.localityLocCode AS ${HouseEntity.PX_LOCALITY}localityLocCode, lv.localityShortName AS ${HouseEntity.PX_LOCALITY}localityShortName, 
            lv.localityName AS ${HouseEntity.PX_LOCALITY}localityName, lv.localitiesId AS ${HouseEntity.PX_LOCALITY}localitiesId,
        ldv.localityDistrictId AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}localityDistrictId, ldv.locDistrictShortName AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}locDistrictShortName, 
            ldv.locDistrictGeocode AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}locDistrictGeocode, ldv.locDistrictOsmId AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}locDistrictOsmId, 
            ldv.${GeoLocalityDistrictEntity.PREFIX}latitude AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}${GeoLocalityDistrictEntity.PREFIX}latitude,
            ldv.${GeoLocalityDistrictEntity.PREFIX}longitude AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}${GeoLocalityDistrictEntity.PREFIX}longitude, 
            ldv.ldLocalitiesId AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}ldLocalitiesId, ldv.localityDistrictTlId AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldv.locDistrictLocCode AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}locDistrictLocCode, ldv.locDistrictName AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}locDistrictName, 
            ldv.localityDistrictsId AS ${HouseEntity.PX_LD_LOCALITY_DISTRICT}localityDistrictsId, 
        mdv.microdistrictId AS ${HouseEntity.PX_MICRODISTRICT}microdistrictId, mdv.microdistrictType AS ${HouseEntity.PX_MICRODISTRICT}microdistrictType, 
            mdv.microdistrictShortName AS ${HouseEntity.PX_MICRODISTRICT}microdistrictShortName,
            mdv.microdistrictGeocode AS ${HouseEntity.PX_MICRODISTRICT}microdistrictGeocode, mdv.microdistrictOsmId AS ${HouseEntity.PX_MICRODISTRICT}microdistrictOsmId, 
            mdv.${GeoMicrodistrictEntity.PREFIX}latitude AS ${HouseEntity.PX_MICRODISTRICT}${GeoMicrodistrictEntity.PREFIX}latitude,
            mdv.${GeoMicrodistrictEntity.PREFIX}longitude AS ${HouseEntity.PX_MICRODISTRICT}${GeoMicrodistrictEntity.PREFIX}longitude, 
            mdv.mLocalityDistrictsId AS ${HouseEntity.PX_MICRODISTRICT}mLocalityDistrictsId, 
            mdv.mLocalitiesId AS ${HouseEntity.PX_MICRODISTRICT}mLocalitiesId, mdv.microdistrictTlId AS ${HouseEntity.PX_MICRODISTRICT}microdistrictTlId, 
            mdv.microdistrictLocCode AS ${HouseEntity.PX_MICRODISTRICT}microdistrictLocCode, mdv.microdistrictName AS ${HouseEntity.PX_MICRODISTRICT}microdistrictName, 
            mdv.microdistrictsId AS ${HouseEntity.PX_MICRODISTRICT}microdistrictsId,
        tv.*, h.*, e.*, f.*, r.*
FROM ${RoomEntity.TABLE_NAME} r JOIN ${HouseEntity.TABLE_NAME} h ON h.houseId = r.rHousesId
    JOIN ${StreetView.VIEW_NAME} sv ON sv.streetId = h.hStreetsId
    JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = sv.sLocalitiesId AND lv.localityLocCode = sv.streetLocCode 
    LEFT JOIN ${EntranceEntity.TABLE_NAME} e ON e.eHousesId = h.houseId AND e.entranceId = r.rEntrancesId  
    LEFT JOIN ${FloorEntity.TABLE_NAME} f ON f.fHousesId = h.houseId AND f.fEntrancesId = e.entranceId AND f.floorId = r.rFloorsId  
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldv ON ldv.localityDistrictId = h.hLocalityDistrictsId AND ldv.locDistrictLocCode = sv.streetLocCode
    LEFT JOIN ${MicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = h.hMicrodistrictsId AND mdv.microdistrictLocCode = sv.streetLocCode
    LEFT JOIN ${TerritoryView.VIEW_NAME} tv ON tv.territoryId = r.rTerritoriesId AND tv.${TerritoryEntity.PX_LOCALITY}localityLocCode  = sv.streetLocCode
"""
)
class RoomView(
    @Embedded val street: StreetView,
    @Embedded(prefix = HouseEntity.PX_LOCALITY) val locality: LocalityView,
    @Embedded(prefix = HouseEntity.PX_LD_LOCALITY_DISTRICT) val localityDistrict: LocalityDistrictView?,
    @Embedded(prefix = HouseEntity.PX_MICRODISTRICT) val microdistrict: MicrodistrictView?,
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
/*
        rvl.regionId AS ${HouseEntity.PX_LD_REGION}regionId, rvl.regionCode AS ${HouseEntity.PX_LD_REGION}regionCode, 
            rvl.regionTlId AS ${HouseEntity.PX_LD_REGION}regionTlId, rvl.regionLocCode AS ${HouseEntity.PX_LD_REGION}regionLocCode, rvl.regionTlCode AS ${HouseEntity.PX_LD_REGION}regionTlCode, 
            rvl.regionName AS ${HouseEntity.PX_LD_REGION}regionName, rvl.regionsId AS ${HouseEntity.PX_LD_REGION}regionsId, 
        rdvl.regionDistrictId AS ${HouseEntity.PX_LD_REGION_DISTRICT}regionDistrictId, rdvl.regDistrictShortName  AS ${HouseEntity.PX_LD_REGION_DISTRICT}regDistrictShortName, 
            rdvl.rRegionsId  AS ${HouseEntity.PX_LD_REGION_DISTRICT}rRegionsId,
            rdvl.regionDistrictTlId  AS ${HouseEntity.PX_LD_REGION_DISTRICT}regionDistrictTlId, rdvl.regDistrictLocCode  AS ${HouseEntity.PX_LD_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName  AS ${HouseEntity.PX_LD_REGION_DISTRICT}regDistrictTlShortName,
            rdvl.regDistrictName  AS ${HouseEntity.PX_LD_REGION_DISTRICT}regDistrictName, rdvl.regionDistrictsId  AS ${HouseEntity.PX_LD_REGION_DISTRICT}regionDistrictsId, 
        lvl.localityId AS ${HouseEntity.PX_LD_LOCALITY}localityId, lvl.localityCode AS ${HouseEntity.PX_LD_LOCALITY}localityCode, lvl.localityType AS ${HouseEntity.PX_LD_LOCALITY}localityType, 
            lvl.lRegionDistrictsId AS ${HouseEntity.PX_LD_LOCALITY}lRegionDistrictsId, lvl.lRegionsId AS ${HouseEntity.PX_LD_LOCALITY}lRegionsId,
            lvl.localityTlId AS ${HouseEntity.PX_LD_LOCALITY}localityTlId, lvl.localityLocCode AS ${HouseEntity.PX_LD_LOCALITY}localityLocCode, lvl.localityShortName AS ${HouseEntity.PX_LD_LOCALITY}localityShortName, 
            lvl.localityName AS ${HouseEntity.PX_LD_LOCALITY}localityName, lvl.localitiesId AS ${HouseEntity.PX_LD_LOCALITY}localitiesId, 

        rvm.regionId AS ${HouseEntity.PX_M_REGION}regionId, rvm.regionCode AS ${HouseEntity.PX_M_REGION}regionCode, 
            rvm.regionTlId AS ${HouseEntity.PX_M_REGION}regionTlId, rvm.regionLocCode AS ${HouseEntity.PX_M_REGION}regionLocCode, rvm.regionTlCode AS ${HouseEntity.PX_M_REGION}regionTlCode, 
            rvm.regionName AS ${HouseEntity.PX_M_REGION}regionName, rvm.regionsId AS ${HouseEntity.PX_M_REGION}regionsId, 
        rdvm.regionDistrictId AS ${HouseEntity.PX_M_REGION_DISTRICT}regionDistrictId, rdvm.regDistrictShortName  AS ${HouseEntity.PX_M_REGION_DISTRICT}regDistrictShortName, 
            rdvm.rRegionsId AS ${HouseEntity.PX_M_REGION_DISTRICT}rRegionsId,
            rdvm.regionDistrictTlId AS ${HouseEntity.PX_M_REGION_DISTRICT}regionDistrictTlId, rdvm.regDistrictLocCode  AS ${HouseEntity.PX_M_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName  AS ${HouseEntity.PX_M_REGION_DISTRICT}regDistrictTlShortName,
            rdvm.regDistrictName AS ${HouseEntity.PX_M_REGION_DISTRICT}regDistrictName, rdvm.regionDistrictsId  AS ${HouseEntity.PX_M_REGION_DISTRICT}regionDistrictsId, 
        lvm.localityId AS ${HouseEntity.PX_M_LOCALITY}localityId, lvm.localityCode AS ${HouseEntity.PX_M_LOCALITY}localityCode, lvm.localityType AS ${HouseEntity.PX_M_LOCALITY}localityType, 
            lvm.lRegionDistrictsId AS ${HouseEntity.PX_M_LOCALITY}lRegionDistrictsId, lvm.lRegionsId AS ${HouseEntity.PX_M_LOCALITY}lRegionsId,
            lvm.localityTlId AS ${HouseEntity.PX_M_LOCALITY}localityTlId, lvm.localityLocCode AS ${HouseEntity.PX_M_LOCALITY}localityLocCode, lvm.localityShortName AS ${HouseEntity.PX_M_LOCALITY}localityShortName, 
            lvm.localityName AS ${HouseEntity.PX_M_LOCALITY}localityName, lvm.localitiesId AS ${HouseEntity.PX_M_LOCALITY}localitiesId, 
        ldvm.localityDistrictId AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}localityDistrictId, ldvm.locDistrictShortName AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvm.ldLocalitiesId AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}ldLocalitiesId, ldvm.localityDistrictTlId AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvm.locDistrictLocCode AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}locDistrictLocCode, ldvm.locDistrictName AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}locDistrictName, 
            ldvm.localityDistrictsId AS ${HouseEntity.PX_M_LOCALITY_DISTRICT}localityDistrictsId, 

    LEFT JOIN ${LocalityView.VIEW_NAME} lvl ON lvl.localityId = ldv.ldLocalitiesId AND lvl.localityLocCode = sv.streetLocCode 
    LEFT JOIN ${GeoRegionView.VIEW_NAME} rvl ON rvl.regionId = lvl.lRegionsId AND rvl.regionLocCode = sv.streetLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvl ON rdvl.regionDistrictId = lvl.lRegionDistrictsId AND rdvl.regDistrictLocCode = sv.streetLocCode

    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvm ON ldvm.localityDistrictId = mdv.mLocalityDistrictsId AND ldvm.locDistrictLocCode = sv.streetLocCode 
    LEFT JOIN ${LocalityView.VIEW_NAME} lvm ON lvm.localityId = ldvm.ldLocalitiesId AND lvm.localityLocCode = sv.streetLocCode
    LEFT JOIN ${GeoRegionView.VIEW_NAME} rvm ON rvm.regionId = lvm.lRegionsId AND rvm.regionLocCode = sv.streetLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvm ON rdvm.regionDistrictId = lvm.lRegionDistrictsId AND rdvm.regDistrictLocCode = sv.streetLocCode
 
    @Embedded(prefix = HouseEntity.PX_LD_REGION) val rldRegion: GeoRegionView?,
    @Embedded(prefix = HouseEntity.PX_LD_REGION_DISTRICT) val rldDistrict: RegionDistrictView?,
    @Embedded(prefix = HouseEntity.PX_LD_LOCALITY) val rldLocality: LocalityView?,

    @Embedded(prefix = HouseEntity.PX_M_REGION) val rmRegion: GeoRegionView?,
    @Embedded(prefix = HouseEntity.PX_M_REGION_DISTRICT) val rmDistrict: RegionDistrictView?,
    @Embedded(prefix = HouseEntity.PX_M_LOCALITY) val rmLocality: LocalityView?,
    @Embedded(prefix = HouseEntity.PX_M_LOCALITY_DISTRICT) val rmLocalityDistrict: LocalityDistrictView?,
*/