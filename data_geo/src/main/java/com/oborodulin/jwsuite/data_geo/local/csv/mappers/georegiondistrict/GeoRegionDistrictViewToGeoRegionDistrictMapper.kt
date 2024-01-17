package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView

class GeoRegionDistrictViewToGeoRegionDistrictMapper(private val mapper: GeoRegionViewToGeoRegionMapper) :
    Mapper<GeoRegionDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv>,
    NullableMapper<GeoRegionDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv> {
    override fun map(input: GeoRegionDistrictView): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv {
        val regionDistrict =
            com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv(
                region = mapper.map(input.region),
                districtShortName = input.district.data.regDistrictShortName,
                districtName = input.district.tl.regDistrictName
            )
        regionDistrict.id = input.district.data.regionDistrictId
        regionDistrict.tlId = input.district.tl.regionDistrictTlId
        return regionDistrict
    }

    override fun nullableMap(input: GeoRegionDistrictView?) = input?.let { map(it) }
}