package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionDistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

class RegionDistrictElementsListToGeoRegionDistrictsListMapper(mapper: RegionDistrictElementToGeoRegionDistrictMapper) :
    ListMapperImpl<RegionDistrictElement, GeoRegionDistrict>(mapper)