package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
    private val countryMapper: GeoCountryViewToGeoCountryMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
) : Mapper<GeoLocalityDistrictView, GeoLocalityDistrict>,
    NullableMapper<GeoLocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: GeoLocalityDistrictView): GeoLocalityDistrict {
        val country = countryMapper.map(input.country)
        val region = regionMapper.map(input.region).also { it.country = country }
        return localityDistrictMapper.map(input.localityDistrict).also { localityDistrict ->
            localityDistrict.locality = localityMapper.map(input.locality).also { locality ->
                locality.region = region
                locality.regionDistrict =
                    regionDistrictMapper.nullableMap(input.district).also { district ->
                        district?.region = region
                    }
            }
        }
    }

    override fun nullableMap(input: GeoLocalityDistrictView?) = input?.let { map(it) }
}

/*
    ConstructedMapper<LocalityDistrictView, GeoLocalityDistrict>,
    NullableConstructedMapper<LocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: LocalityDistrictView, vararg properties: Any?): GeoLocalityDistrict {
        if (properties.isEmpty() || properties[0] !is GeoLocality) throw IllegalArgumentException(
            "LocalityDistrictViewToGeoLocalityDistrictMapper: properties is empty or properties[0] is not GeoLocality class: size = %d; input.data.localityDistrictId = %s".format(
                properties.size, input.data.localityDistrictId
            )
        )
        return GeoLocalityDistrict(
            locality = properties[0] as GeoLocality,
            districtShortName = input.data.locDistrictShortName,
            districtGeocode = input.data.locDistrictGeocode,
            districtOsmId = input.data.locDistrictOsmId,
            coordinates = mapper.map(input.data.coordinates),
            districtName = input.tl.locDistrictName
        ).also {
            it.id = input.data.localityDistrictId
            it.tlId = input.tl.localityDistrictTlId
        }
    }

    override fun nullableMap(input: LocalityDistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}*/