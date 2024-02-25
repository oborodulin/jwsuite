package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict

class GeoMicrodistrictViewToGeoMicrodistrictMapper(
    private val countryMapper: GeoCountryViewToGeoCountryMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
) : Mapper<GeoMicrodistrictView, GeoMicrodistrict>,
    NullableMapper<GeoMicrodistrictView, GeoMicrodistrict> {
    override fun map(input: GeoMicrodistrictView): GeoMicrodistrict {
        val country = countryMapper.map(input.country)
        val region = regionMapper.map(input.region).also { it.country = country }
        val locality = localityMapper.map(input.locality).also { locality ->
            locality.region = region
            locality.regionDistrict =
                regionDistrictMapper.nullableMap(input.district).also { district ->
                    district?.region = region
                }
        }
        return microdistrictMapper.map(input.microdistrict).also { microdistrict ->
            microdistrict.locality = locality
            microdistrict.localityDistrict =
                localityDistrictMapper.map(input.localityDistrict).also { localityDistrict ->
                    localityDistrict.locality = locality
                }
        }
    }

    override fun nullableMap(input: GeoMicrodistrictView?) = input?.let { map(it) }
}
/*
    ConstructedMapper<MicrodistrictView, GeoMicrodistrict>,
    NullableConstructedMapper<MicrodistrictView, GeoMicrodistrict> {
    override fun map(input: MicrodistrictView, vararg properties: Any?): GeoMicrodistrict {
        if (properties.size != 2 || properties[0] !is GeoLocality || properties[1] !is GeoLocalityDistrict) throw IllegalArgumentException(
            "MicrodistrictViewToGeoMicrodistrictMapper: properties size not equal 2 or properties[0] is not GeoLocality class or properties[1] is not GeoLocalityDistrict class: size = %d; input.data.microdistrictId = %s".format(
                properties.size, input.data.microdistrictId
            )
        )
        return GeoMicrodistrict(
            ctx = ctx,
            locality = properties[0] as GeoLocality,
            localityDistrict = properties[1] as GeoLocalityDistrict,
            microdistrictType = input.data.microdistrictType,
            microdistrictShortName = input.data.microdistrictShortName,
            microdistrictName = input.tl.microdistrictName
        ).also {
            it.id = input.data.microdistrictId
            it.tlId = input.tl.microdistrictTlId
        }
    }

    override fun nullableMap(input: MicrodistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}*/