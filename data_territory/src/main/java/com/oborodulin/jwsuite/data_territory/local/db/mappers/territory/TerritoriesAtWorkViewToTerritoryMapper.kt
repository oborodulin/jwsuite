package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberEntityToMemberMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.domain.model.territory.Territory

class TerritoriesAtWorkViewToTerritoryMapper(
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val memberMapper: MemberEntityToMemberMapper
) : Mapper<TerritoriesAtWorkView, Territory> {
    override fun map(input: TerritoriesAtWorkView) = territoryMapper.map(input.territory).copy(
        member = memberMapper.nullableMap(input.member),
        congregationId = input.ctCongregationsId,
        isPrivateSector = input.isPrivateSector,
        expiredTotalDays = input.expiredTotalDays
    )
}
/*
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: GeoLocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper,

: Territory {
        with(input.territory) {
            val region = regionMapper.map(this.tRegion)
            val regionDistrict = regionDistrictMapper.nullableMap(this.tDistrict, region)

            val ldRegion = regionMapper.nullableMap(this.tldRegion)
            val ldRegionDistrict = regionDistrictMapper.nullableMap(this.tldDistrict, ldRegion)
            val ldLocality =
                localityMapper.nullableMap(this.tldLocality, ldRegion, ldRegionDistrict)

            val mRegion = regionMapper.nullableMap(this.tmRegion)
            val mRegionDistrict = regionDistrictMapper.nullableMap(this.tmDistrict, mRegion)
            val mLocality = localityMapper.nullableMap(this.tmLocality, mRegion, mRegionDistrict)
            val mLocalityDistrict = localityDistrictMapper.nullableMap(
                this.tmLocalityDistrict, mLocality
            )
            return Territory(
                congregation = congregationMapper.map(this.congregation),
                territoryCategory = territoryCategoryMapper.map(this.territoryCategory),
                locality = localityMapper.map(this.locality, region, regionDistrict),
                localityDistrict = localityDistrictMapper.nullableMap(
                    this.localityDistrict, ldLocality
                ),
                microdistrict = microdistrictMapper.nullableMap(
                    this.microdistrict, mLocality, mLocalityDistrict
                ),
                territoryNum = this.territory.territoryNum,
                isBusiness = this.territory.isBusinessTerritory,
                isGroupMinistry = this.territory.isGroupMinistry,
                //isInPerimeter = this.territory.isInPerimeter,
                isProcessed = this.territory.isProcessed,
                isActive = this.territory.isActive,
                territoryDesc = this.territory.territoryDesc,
                member = memberMapper.nullableMap(input.member),
                congregationId = input.ctCongregationsId,
                isPrivateSector = input.isPrivateSector,
                expiredTotalDays = input.expiredTotalDays,
                territoryBusinessMark = input.atWorkTerritoryBusinessMark
            ).also { it.id = this.territory.territoryId }
        }
 */