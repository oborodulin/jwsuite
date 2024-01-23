package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictTlCsv

class GeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper :
    Mapper<GeoLocalityDistrictTlCsv, GeoLocalityDistrictTlEntity> {
    override fun map(input: GeoLocalityDistrictTlCsv) = GeoLocalityDistrictTlEntity(
        localityDistrictTlId = input.localityDistrictTlId,
        locDistrictLocCode = input.locDistrictLocCode,
        locDistrictName = input.locDistrictName,
        localityDistrictsId = input.localityDistrictsId
    )
}