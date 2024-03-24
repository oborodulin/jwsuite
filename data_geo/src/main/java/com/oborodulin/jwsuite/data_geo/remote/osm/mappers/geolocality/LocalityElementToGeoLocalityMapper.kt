package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.locality.LocalityElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.types.LocalityType

class LocalityElementToGeoLocalityMapper(private val mapper: GeometryToGeoCoordinatesMapper) :
    Mapper<LocalityElement, GeoLocality> {
    override fun map(input: LocalityElement) = GeoLocality(
        region = GeoRegion().also { it.id = input.tags.regionId },
        regionDistrict = GeoRegionDistrict(),
        localityType = LocalityType.CITY,
        localityShortName = input.tags,
        localityGeocode = input.tags.geocodeArea,
        localityOsmId = input.id,
        coordinates = mapper.map(input.geometry),
        localityName = input.tags.name
    )
}