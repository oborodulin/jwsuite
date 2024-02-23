package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv

class GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper :
    Mapper<GeoLocalityDistrictCsv, GeoLocalityDistrictEntity> {
    override fun map(input: GeoLocalityDistrictCsv) = GeoLocalityDistrictEntity(
        localityDistrictId = input.localityDistrictId,
        locDistrictShortName = input.locDistrictShortName,
        locDistrictOsmId = input.locDistrictOsmId,
        coordinates = Coordinates.fromLatAndLon(lat = input.latitude, lon = input.longitude),
        ldLocalitiesId = input.ldLocalitiesId
    )
}