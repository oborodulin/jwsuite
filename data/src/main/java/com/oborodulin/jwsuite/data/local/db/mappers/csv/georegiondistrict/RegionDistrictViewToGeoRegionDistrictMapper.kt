package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView

private const val TAG = "Data.RegionDistrictViewToGeoRegionDistrictMapper"

class RegionDistrictViewToGeoRegionDistrictMapper :
    ConstructedMapper<RegionDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv>,
    NullableConstructedMapper<RegionDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv> {
    override fun map(input: RegionDistrictView, vararg properties: Any?): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv {
        /*
                properties.forEach { property ->
                    property?.let { Timber.tag(TAG).d("property class = %s", it::class) }
                }
         */
        if (properties.isEmpty() || properties[0] !is com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv) throw IllegalArgumentException(
            "RegionDistrictViewToGeoRegionDistrictMapper: properties empty or properties[0] is not GeoRegionCsv class: properties.isEmpty() = %s; input.data.regionDistrictId = %s".format(
                properties.isEmpty(), input.data.regionDistrictId
            )
        )
        val regionDistrict =
            com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv(
                region = properties[0] as com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv,
                districtShortName = input.data.regDistrictShortName,
                districtName = input.tl.regDistrictName
            )
        regionDistrict.id = input.data.regionDistrictId
        regionDistrict.tlId = input.tl.regionDistrictTlId
        return regionDistrict
    }

    override fun nullableMap(input: RegionDistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}