package com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class GeoMicrodistrictViewToGeoMicrodistrictMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
) :
    Mapper<GeoMicrodistrictView, GeoMicrodistrict>,
    NullableMapper<GeoMicrodistrictView, GeoMicrodistrict> {
    override fun map(input: GeoMicrodistrictView): GeoMicrodistrict {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val locality = localityMapper.map(input.locality, region, regionDistrict)
        val microdistrict = GeoMicrodistrict(
            locality = locality,
            localityDistrict = localityDistrictMapper.map(input.localityDistrict, locality),
            microdistrictType = input.microdistrict.data.microdistrictType,
            microdistrictShortName = input.microdistrict.data.microdistrictShortName,
            microdistrictName = input.microdistrict.tl.microdistrictName
        )
        microdistrict.id = input.microdistrict.data.microdistrictId
        microdistrict.tlId = input.microdistrict.tl.microdistrictTlId
        return microdistrict
    }

    override fun nullableMap(input: GeoMicrodistrictView?) = input?.let { map(it) }
}