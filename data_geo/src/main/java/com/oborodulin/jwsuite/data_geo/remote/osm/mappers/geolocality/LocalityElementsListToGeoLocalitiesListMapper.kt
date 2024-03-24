package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.locality.LocalityElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality

class LocalityElementsListToGeoLocalitiesListMapper(mapper: LocalityElementToGeoLocalityMapper) :
    ListMapperImpl<LocalityElement, GeoLocality>(mapper)