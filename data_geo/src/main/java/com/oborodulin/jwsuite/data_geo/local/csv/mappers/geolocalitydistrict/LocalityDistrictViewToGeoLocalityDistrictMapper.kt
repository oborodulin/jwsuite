package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView

class LocalityDistrictViewToGeoLocalityDistrictMapper :
    ConstructedMapper<LocalityDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv>,
    NullableConstructedMapper<LocalityDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv> {
    override fun map(input: LocalityDistrictView, vararg properties: Any?): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv {
        if (properties.isEmpty() || properties[0] !is com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv) throw IllegalArgumentException(
            "LocalityDistrictViewToGeoLocalityDistrictMapper: properties is empty or properties[0] is not GeoLocality class: size = %d; input.data.localityDistrictId = %s".format(
                properties.size, input.data.localityDistrictId
            )
        )
        val localityDistrict =
            com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv(
                locality = properties[0] as com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv,
                districtShortName = input.data.locDistrictShortName,
                districtName = input.tl.locDistrictName
            )
        localityDistrict.id = input.data.localityDistrictId
        localityDistrict.tlId = input.tl.localityDistrictTlId
        return localityDistrict
    }

    override fun nullableMap(input: LocalityDistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}