package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv

class GeoLocalityDistrictEntityToGeoLocalityDistrictCsvMapper :
    Mapper<GeoLocalityDistrictEntity, GeoLocalityDistrictCsv> {
    override fun map(input: GeoLocalityDistrictEntity) = GeoLocalityDistrictCsv(
        localityDistrictId = input.localityDistrictId,
        locDistrictShortName = input.locDistrictShortName,
        locDistrictOsmId = input.locDistrictOsmId,
        latitude = input.coordinates?.latitude,
        longitude = input.coordinates?.longitude,
        ldLocalitiesId = input.ldLocalitiesId
    )
}