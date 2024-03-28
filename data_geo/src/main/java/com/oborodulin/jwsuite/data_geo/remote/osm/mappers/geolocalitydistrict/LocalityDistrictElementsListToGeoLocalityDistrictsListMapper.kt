package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.localitydistrict.LocalityDistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class LocalityDistrictElementsListToGeoLocalityDistrictsListMapper(mapper: LocalityDistrictElementToGeoLocalityDistrictMapper) :
    ListMapperImpl<LocalityDistrictElement, GeoLocalityDistrict>(mapper)