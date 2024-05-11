package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.StreetElement
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet

class StreetElementsListToGeoStreetsListMapper(mapper: StreetElementToGeoStreetMapper) :
    ListMapperImpl<StreetElement, GeoStreet>(mapper)