package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionElement
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class RegionElementToGeoRegionMapper : Mapper<RegionElement, GeoRegion> {
    override fun map(input: RegionElement) = GeoRegion(
        regionCode = input.tags.isoCode.ifEmpty { input.tags.ref },
        regionGeocode = input.tags.geocodeArea,
        regionOsmId = input.id,
        latitude = input.geometry.coordinates.getOrNull(0),
        longitude = input.geometry.coordinates.getOrNull(1),
        regionName = input.tags.name
    )
}