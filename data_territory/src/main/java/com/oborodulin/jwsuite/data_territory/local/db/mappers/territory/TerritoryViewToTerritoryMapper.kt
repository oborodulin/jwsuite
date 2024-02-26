package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryEntityToTerritoryCategoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.model.territory.Territory

class TerritoryViewToTerritoryMapper(
    private val ctx: Context,
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
) : Mapper<TerritoryView, Territory>, NullableMapper<TerritoryView, Territory> {
    override fun map(input: TerritoryView) = Territory(
        ctx = ctx,
        congregation = congregationMapper.map(input.congregation),
        territoryCategory = territoryCategoryMapper.map(input.territoryCategory),
        locality = localityMapper.map(input.locality),// region, regionDistrict),
        localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),// ldLocality),
        microdistrict = microdistrictMapper.nullableMap(input.microdistrict),//, mLocality, mLocalityDistrict),
        territoryNum = input.territory.territoryNum,
        isBusiness = input.territory.isBusinessTerritory,
        isGroupMinistry = input.territory.isGroupMinistry,
        //isInPerimeter = input.territory.isInPerimeter,
        isProcessed = input.territory.isProcessed,
        isActive = input.territory.isActive,
        territoryDesc = input.territory.territoryDesc,
        territoryBusinessMark = input.territoryBusinessMark
    ).also { it.id = input.territory.territoryId }

    override fun nullableMap(input: TerritoryView?) = input?.let { map(it) }
}
/*
        val region = regionMapper.map(input.tRegion)
        val regionDistrict = regionDistrictMapper.nullableMap(input.tDistrict, region)

        val ldRegion = regionMapper.nullableMap(input.tldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.tldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.tldLocality, ldRegion, ldRegionDistrict)

        val mRegion = regionMapper.nullableMap(input.tmRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.tmDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.tmLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.tmLocalityDistrict, mLocality
        )
 */