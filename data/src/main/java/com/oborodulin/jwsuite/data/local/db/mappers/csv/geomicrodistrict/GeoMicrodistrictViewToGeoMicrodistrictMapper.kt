package com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView

class GeoMicrodistrictViewToGeoMicrodistrictMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
) :
    Mapper<GeoMicrodistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv>,
    NullableMapper<GeoMicrodistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv> {
    override fun map(input: GeoMicrodistrictView): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val locality = localityMapper.map(input.locality, region, regionDistrict)
        return microdistrictMapper.map(
            input.microdistrict,
            locality,
            localityDistrictMapper.map(input.localityDistrict, locality)
        )
    }

    override fun nullableMap(input: GeoMicrodistrictView?) = input?.let { map(it) }
}