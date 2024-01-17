package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityView

class GeoLocalityViewToGeoLocalityMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper
) : Mapper<GeoLocalityView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv> {
    override fun map(input: GeoLocalityView): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv {
        val region = regionMapper.map(input.region)
        return localityMapper.map(
            input.locality,
            region,
            regionDistrictMapper.nullableMap(input.district, region)
        )
    }
}