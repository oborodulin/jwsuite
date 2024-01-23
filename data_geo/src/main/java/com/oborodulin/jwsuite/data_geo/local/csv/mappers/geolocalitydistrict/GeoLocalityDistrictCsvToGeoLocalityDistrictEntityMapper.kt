package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv

class GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper :
    Mapper<GeoLocalityDistrictCsv, GeoLocalityDistrictEntity> {
    override fun map(input: GeoLocalityDistrictCsv) = GeoLocalityDistrictEntity(
        localityDistrictId = input.localityDistrictId,
        locDistrictShortName = input.locDistrictShortName,
        ldLocalitiesId = input.ldLocalitiesId
    )
}