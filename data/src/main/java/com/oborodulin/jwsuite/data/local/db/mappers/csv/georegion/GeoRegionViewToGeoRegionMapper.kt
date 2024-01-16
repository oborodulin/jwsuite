package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView

class GeoRegionViewToGeoRegionMapper : Mapper<GeoRegionView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv>,
    NullableMapper<GeoRegionView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv> {
    override fun map(input: GeoRegionView): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv {
        val region = com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv(
            regionCode = input.data.regionCode,
            regionName = input.tl.regionName
        )
        region.id = input.data.regionId
        region.tlId = input.tl.regionTlId
        return region
    }

    override fun nullableMap(input: GeoRegionView?) = input?.let { map(it) }
}