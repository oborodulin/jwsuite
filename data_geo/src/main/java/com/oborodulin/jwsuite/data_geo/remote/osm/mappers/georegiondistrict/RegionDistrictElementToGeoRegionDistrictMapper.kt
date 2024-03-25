package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict.RegionDistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

class RegionDistrictElementToGeoRegionDistrictMapper(private val mapper: GeometryToGeoCoordinatesMapper) :
    Mapper<RegionDistrictElement, GeoRegionDistrict> {
    override fun map(input: RegionDistrictElement) = GeoRegionDistrict(
        region = GeoRegion().also { it.id = input.tags.regionId },
        districtShortName = input.tags.countyAbbrev.ifEmpty {
            "${input.tags.wikidata}-${input.tags.name.substring(0..3).uppercase()}"
        },
        districtGeocode = input.tags.geocodeArea.ifEmpty { input.tags.name },
        districtOsmId = input.id,
        coordinates = mapper.map(input.geometry),
        districtName = input.tags.nameLoc.ifEmpty { input.tags.name }
    )
}