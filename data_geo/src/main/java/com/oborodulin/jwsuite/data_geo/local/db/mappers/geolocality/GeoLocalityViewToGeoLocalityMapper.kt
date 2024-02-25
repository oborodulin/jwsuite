package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality

class GeoLocalityViewToGeoLocalityMapper(
    private val countryMapper: GeoCountryViewToGeoCountryMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper
) : Mapper<GeoLocalityView, GeoLocality>, NullableMapper<GeoLocalityView, GeoLocality> {
    override fun map(input: GeoLocalityView): GeoLocality {
        val country = countryMapper.map(input.country)
        val region = regionMapper.map(input.region).also { it.country = country }
        return localityMapper.map(input.locality).also { locality ->
            locality.region = region
            locality.regionDistrict =
                regionDistrictMapper.nullableMap(input.district).also { district ->
                    district?.region = region
                }
        }
    }

    override fun nullableMap(input: GeoLocalityView?) = input?.let { map(it) }
}
/*
    ConstructedMapper<LocalityView, GeoLocality>,
    NullableConstructedMapper<LocalityView, GeoLocality> {
    override fun map(input: LocalityView, vararg properties: Any?): GeoLocality {
        if (properties.size != 2 || properties[0] !is GeoRegion || (properties[1] != null && properties[1] !is GeoRegionDistrict)) throw IllegalArgumentException(
            "LocalityViewToGeoLocalityMapper: properties size not equal 2 or properties[0] is not GeoRegion class or properties[1] is not GeoRegionDistrict class: size = %d; input.data.localityId = %s".format(
                properties.size, input.data.localityId
            )
        )
        return GeoLocality(
            ctx = ctx,
            region = properties[0] as GeoRegion,
            regionDistrict = properties[1] as? GeoRegionDistrict,
            localityCode = input.data.localityCode,
            localityType = input.data.localityType,
            localityShortName = input.tl.localityShortName,
            localityName = input.tl.localityName
        ).also {
            it.id = input.data.localityId
            it.tlId = input.tl.localityTlId
        }
    }

    override fun nullableMap(input: LocalityView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}
 */