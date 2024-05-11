package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionElement
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class RegionElementsListToGeoRegionsListMapper(mapper: RegionElementToGeoRegionMapper) :
    ListMapperImpl<RegionElement, GeoRegion>(mapper)