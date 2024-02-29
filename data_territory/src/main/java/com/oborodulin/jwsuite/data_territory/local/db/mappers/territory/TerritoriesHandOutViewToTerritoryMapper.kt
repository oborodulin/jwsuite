package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.domain.model.territory.Territory

private const val TAG = "Data.TerritoriesHandOutViewToTerritoryMapper"

class TerritoriesHandOutViewToTerritoryMapper(
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val memberMapper: MemberViewToMemberMapper
) : Mapper<TerritoriesHandOutView, Territory> {
    override fun map(input: TerritoriesHandOutView) = territoryMapper.map(input.territory).copy(
        member = memberMapper.nullableMap(input.member),
        congregationId = input.ctCongregationsId,
        isPrivateSector = input.isPrivateSector,
        handOutTotalDays = input.handOutTotalDays
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
        if (LOG_DB_MAPPER) Timber.tag(TAG).d(
            "TerritoriesHandOutViewToTerritoryMapper(...) called: territoryId = %s",
            input.territory.territory.territoryId
        )
        with(input.territory) {
            val region = regionMapper.map(this.tRegion)
            if (LOG_DB_MAPPER) Timber.tag(TAG)
                .d("TerritoriesHandOutViewToTerritoryMapper: region = %s", region)
            val regionDistrict = regionDistrictMapper.nullableMap(this.tDistrict, region)
            if (LOG_DB_MAPPER) Timber.tag(TAG)
                .d("Territory: Locality -> RegionDistrict -> Region mapped")

            val ldRegion = regionMapper.nullableMap(this.tldRegion)
            val ldRegionDistrict = regionDistrictMapper.nullableMap(this.tldDistrict, ldRegion)
            val ldLocality =
                localityMapper.nullableMap(this.tldLocality, ldRegion, ldRegionDistrict)
            if (LOG_DB_MAPPER) Timber.tag(TAG)
                .d("Territory: LocalityDistrict -> Locality -> RegionDistrict -> Region mapped")

            val mRegion = regionMapper.nullableMap(this.tmRegion)
            val mRegionDistrict = regionDistrictMapper.nullableMap(this.tmDistrict, mRegion)
            val mLocality = localityMapper.nullableMap(this.tmLocality, mRegion, mRegionDistrict)
            val mLocalityDistrict = localityDistrictMapper.nullableMap(
                this.tmLocalityDistrict, mLocality
            )
            if (LOG_DB_MAPPER) Timber.tag(TAG)
                .d("Territory: Microdistrict -> LocalityDistrict -> Locality -> RegionDistrict -> Region mapped")
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
                handOutTotalDays = input.handOutTotalDays,
                territoryBusinessMark = input.handOutTerritoryBusinessMark
            ).also { it.id = this.territory.territoryId }
        }
    }
 */