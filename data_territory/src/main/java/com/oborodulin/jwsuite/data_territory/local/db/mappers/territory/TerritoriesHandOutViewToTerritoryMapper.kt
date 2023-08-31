package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryEntityToTerritoryCategoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.domain.model.Territory
import timber.log.Timber

private const val TAG = "Data.TerritoriesHandOutViewToTerritoryMapper"

class TerritoriesHandOutViewToTerritoryMapper(
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    private val memberMapper: MemberViewToMemberMapper
) : Mapper<TerritoriesHandOutView, Territory> {
    override fun map(input: TerritoriesHandOutView): Territory {
        Timber.tag(TAG).d(
            "TerritoriesHandOutViewToTerritoryMapper(...) called: territoryId = %s",
            input.territory.territory.territoryId
        )
        var territory: Territory
        with(input.territory) {
            val region = regionMapper.map(this.tRegion)
            Timber.tag(TAG).d("TerritoriesHandOutViewToTerritoryMapper: region = %s", region)
            val regionDistrict = regionDistrictMapper.nullableMap(this.tDistrict, region)
            Timber.tag(TAG).d("Territory: Locality -> RegionDistrict -> Region mapped")

            val ldRegion = regionMapper.nullableMap(this.tldRegion)
            val ldRegionDistrict = regionDistrictMapper.nullableMap(this.tldDistrict, ldRegion)
            val ldLocality =
                localityMapper.nullableMap(this.tldLocality, ldRegion, ldRegionDistrict)
            Timber.tag(TAG)
                .d("Territory: LocalityDistrict -> Locality -> RegionDistrict -> Region mapped")

            val mRegion = regionMapper.nullableMap(this.tmRegion)
            val mRegionDistrict = regionDistrictMapper.nullableMap(this.tmDistrict, mRegion)
            val mLocality = localityMapper.nullableMap(this.tmLocality, mRegion, mRegionDistrict)
            val mLocalityDistrict = localityDistrictMapper.nullableMap(
                this.tmLocalityDistrict, mLocality
            )
            Timber.tag(TAG)
                .d("Territory: Microdistrict -> LocalityDistrict -> Locality -> RegionDistrict -> Region mapped")
            territory = Territory(
                congregation = congregationMapper.map(this.congregation),
                territoryCategory = territoryCategoryMapper.map(this.territoryCategory),
                locality = localityMapper.map(this.tLocality, region, regionDistrict),
                localityDistrict = localityDistrictMapper.nullableMap(
                    this.tLocalityDistrict, ldLocality
                ),
                microdistrict = microdistrictMapper.nullableMap(
                    this.tMicrodistrict, mLocality, mLocalityDistrict
                ),
                territoryNum = this.territory.territoryNum,
                isBusiness = this.territory.isBusinessTerritory,
                isGroupMinistry = this.territory.isGroupMinistry,
                isInPerimeter = this.territory.isInPerimeter,
                isProcessed = this.territory.isProcessed,
                isActive = this.territory.isActive,
                territoryDesc = this.territory.territoryDesc,
                member = memberMapper.nullableMap(input.member),
                congregationId = input.ctCongregationsId,
                isPrivateSector = input.isPrivateSector,
                handOutTotalDays = input.handOutTotalDays,
                territoryBusinessMark = input.handOutTerritoryBusinessMark
            )
            territory.id = this.territory.territoryId
        }
        return territory
    }
}