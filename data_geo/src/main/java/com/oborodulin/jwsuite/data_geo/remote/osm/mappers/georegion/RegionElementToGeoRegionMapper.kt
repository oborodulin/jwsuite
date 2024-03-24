package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionElement
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class RegionElementToGeoRegionMapper(private val ctx: Context, private val mapper: GeometryToGeoCoordinatesMapper) :
    Mapper<RegionElement, GeoRegion> {
    override fun map(input: RegionElement) = GeoRegion(
        country = GeoCountry().also { it.id = input.tags.countryId },
        regionCode = input.tags.cadasterCode.ifEmpty { input.tags.shortNameLoc.ifEmpty { input.tags.refLoc.ifEmpty { input.tags.gost.ifEmpty { input.tags.shortName.ifEmpty { input.tags.ref.ifEmpty { input.tags.isoCode } } } } } },
        regionGeocode = input.tags.geocodeArea,
        regionOsmId = input.id,
        coordinates = mapper.map(input.geometry),
        regionName = input.tags.nameLoc.ifEmpty { input.tags.name}.substringBeforeLast(' ')
    )
}